package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class CompressorViewGUI extends BaseGUI{

    private int pageNumber = 1;
    private int numberOfPages = 0;
    private List<Compressor> currentPageCompressors = new ArrayList<>();

    private boolean deleteMode = false;

    private GUIUtils.ViewSortType sortType = GUIUtils.ViewSortType.INDEX_ASCENDING;
    private final List<GUIUtils.ViewSortType> sortTypeList = Arrays.asList(GUIUtils.ViewSortType.values());
    private ListIterator<GUIUtils.ViewSortType> sortTypeIterator;

    private final AutoCompressors plugin;

    public CompressorViewGUI(AutoCompressors plugin) {
        super(54, "Edit Compressors", GUIUtils.GUIType.NORMAL);
        this.plugin = plugin;
        sortTypeIterator = sortTypeList.listIterator(sortTypeList.indexOf(sortType));

        sortTypeIterator = sortTypeIterator.hasNext() ? sortTypeIterator : sortTypeList.listIterator();
        sortTypeIterator.next();

        refreshGUI();
    }

    /* ITEM ACTION FUNCTIONS */
    private void nextPage() {
        pageNumber += 1;
        refreshGUI();
    }

    private void previousPage() {
        pageNumber -= 1;
        refreshGUI();
    }

    /* DRAWING FUNCTIONS */
    public void refreshGUI() {
        refreshPages();

        getActions().clear();
        getInventory().clear();

        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(49, GUIUtils.createGuiItem("§cClose", Material.BARRIER), this::close);
        if (pageNumber < numberOfPages) {
            setItem(51, GUIUtils.createGuiItem("§aNext Page", Material.ARROW, "§7(" + pageNumber + "/" + numberOfPages + ")"), player -> nextPage());
        }
        if (pageNumber > 1) {
            setItem(47, GUIUtils.createGuiItem("§aPrevious Page", Material.ARROW, "§7(" + pageNumber + "/" + numberOfPages + ")"), player -> previousPage());
        }

        setItem(45, GUIUtils.createGuiItem("§aFilter", Material.HOPPER, filterLore()), player -> {
            if (!sortTypeIterator.hasNext()) {
                sortTypeIterator = sortTypeList.listIterator();
            }
            sortType = sortTypeIterator.next();
            refreshGUI();
        });

        if (deleteMode) {
            setItem(46, GUIUtils.createGuiItem("§cExit Deletion Mode", Material.RED_TERRACOTTA, "§7Exits the mode that allows compressor", "§7deletion.", "", "§cClick to exit!"), player -> {
                deleteMode = false;
                refreshGUI();
            });
        } else {
            setItem(46, GUIUtils.createGuiItem("§cDelete Compressors", Material.RED_TERRACOTTA, "§7Allows you to delete certain compressors.", "", "§cClick to open!"), player -> {
                deleteMode = true;
                refreshGUI();
            });
        }

        setItem(52, GUIUtils.createGuiItem("§aCreate New Compressor", Material.GREEN_TERRACOTTA, "§7Creates a new compressor and opens an", "§7editing GUI.", "", "§aClick to create!"), player -> {
            deleteMode = false;
            CompressorEditGUI editGUI = new CompressorEditGUI(null, CompressorItemManager.getCompressorAmount(), plugin, this, GUIUtils.GUIMode.CREATE);
            close(player);
            editGUI.open(player);
        });

        for (Compressor compressor : currentPageCompressors) {
            if (deleteMode) {
                addItem(GUIUtils.appendLore(GUIUtils.createGuiItem("§cDelete " + compressor.getDisplayName(), Material.RED_TERRACOTTA, compressor.getLore()), "", "§cClick to delete!"), player -> {
                    ConfirmGUI gui = new ConfirmGUI(null, this, () -> deleteCompressor(compressor.getIndex()));
                    close(player);
                    gui.open(player);
                });
            } else {
                ItemStack compressorItem = compressor.getItemStack();
                ItemStack item = GUIUtils.appendLore(compressorItem, "", "§aClick to edit compressor " + compressor.getDisplayIndex() + "!");
                addItem(item, player -> {
                    CompressorEditGUI editGUI = new CompressorEditGUI(compressor, compressor.getIndex(), plugin, this, GUIUtils.GUIMode.EDIT);
                    close(player);
                    editGUI.open(player);
                });
            }
        }
    }

    public void refreshPages() {
        final int openCompressorSlots = 28;

        List<Compressor> compressorList;

        switch (sortType) {
            case A_Z:
                compressorList = GUIUtils.sortedCompressorsAZ(CompressorItemManager.getCompressorList());
                break;
            case Z_A:
                compressorList = GUIUtils.sortedCompressorsZA(CompressorItemManager.getCompressorList());
                break;
            case INDEX_DESCENDING:
                compressorList = GUIUtils.sortedCompressorsDescendingIndex(CompressorItemManager.getCompressorList());
                break;
            default:
                compressorList = CompressorItemManager.getCompressorList();
        }

        numberOfPages = (int) Math.ceil(compressorList.size() / (double) openCompressorSlots);

        if (numberOfPages == 0) {
            numberOfPages = 1;
        }

        if (pageNumber == numberOfPages) {
            currentPageCompressors = compressorList.subList(openCompressorSlots * (pageNumber - 1), compressorList.size());
        } else {
            currentPageCompressors = compressorList.subList(openCompressorSlots * (pageNumber - 1), (openCompressorSlots * (pageNumber - 1)) + openCompressorSlots);
        }
    }

    private List<String> filterLore() {
        List<String> currentLore = new ArrayList<>();

        for (GUIUtils.ViewSortType value : sortTypeList) {
            switch (value) {
                case A_Z:
                    currentLore.add((sortType.equals(value) ? "§a▶ " : "§f  ") + "A-Z");
                    break;
                case Z_A:
                    currentLore.add((sortType.equals(value) ? "§a▶ " : "§f  ") + "Z-A");
                    break;
                default:
                    currentLore.add((sortType.equals(value) ? "§a▶ " : "§f  ") + GUIUtils.readable(value.toString()));
            }
        }

        return currentLore;
    }

    public void deleteCompressor(int index) {
        List<Compressor> compressorList = CompressorItemManager.getCompressorList();
        compressorList.remove(index);

        // Resets indices of compressors
        for (int i = 0; i < compressorList.size(); i++) {
            Compressor comp = compressorList.get(i);
            CompressorItemManager.setCompressor(i, new Compressor(
                    comp.getDisplayName(),
                    comp.getLore(),
                    comp.getMaterial(),
                    comp.hasEnchantGlint(),
                    comp.getLocation(),
                    comp.getCompressions(),
                    i
            ));
        }

        FileConfiguration config = plugin.getCompressorConfig();
        config.set("compressor" + (index + 1), null);
        plugin.saveCompressorConfig(config);

        refreshGUI();
    }
}
