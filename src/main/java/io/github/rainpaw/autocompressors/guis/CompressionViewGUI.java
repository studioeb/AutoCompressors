package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.items.Compression;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class CompressionViewGUI extends BaseGUI {

    private final AutoCompressors plugin;
    private final CompressorEditGUI editGUI;

    private final List<Compression> compressions;
    private List<Compression> currentPageCompressions;

    private boolean deleteMode = false;

    private int pageNumber = 1;
    private int numberOfPages = 0;

    private GUIUtils.CompressionViewSortType sortType = GUIUtils.CompressionViewSortType.INDEX_ASCENDING;
    private final List<GUIUtils.CompressionViewSortType> sortTypeList = Arrays.asList(GUIUtils.CompressionViewSortType.values());
    private ListIterator<GUIUtils.CompressionViewSortType> sortTypeIterator;

    public CompressionViewGUI(CompressorEditGUI editGUI, List<Compression> compressions, AutoCompressors plugin) {
        super(54, "Edit Compressions", GUIUtils.GUIType.NORMAL);

        this.compressions = compressions;
        this.editGUI = editGUI;
        this.plugin = plugin;

        sortTypeIterator = sortTypeList.listIterator(sortTypeList.indexOf(sortType));
        sortTypeIterator = sortTypeIterator.hasNext() ? sortTypeIterator : sortTypeList.listIterator();
        sortTypeIterator.next();

        refreshGUI();
    }

    private void nextPage() {
        pageNumber += 1;
        refreshGUI();
    }

    private void previousPage() {
        pageNumber -= 1;
        refreshGUI();
    }

    public void refreshGUI() {
        refreshPages();

        getActions().clear();
        getInventory().clear();

        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(49, GUIUtils.createGuiItem("§aBack", Material.ARROW), player -> {
            editGUI.refreshGUI();
            close(player);
            editGUI.open(player);
        });
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
            setItem(46, GUIUtils.createGuiItem("§cExit Deletion Mode", Material.RED_TERRACOTTA, "§7Exits the mode that allows compression", "§7deletion.", "", "§cClick to exit!"), player -> {
                deleteMode = false;
                refreshGUI();
            });
        } else {
            setItem(46, GUIUtils.createGuiItem("§cDelete Compressions", Material.RED_TERRACOTTA, "§7Allows you to delete certain compressions.", "", "§cClick to open!"), player -> {
                deleteMode = true;
                refreshGUI();
            });
        }

        setItem(52, GUIUtils.createGuiItem("§aCreate New Compression", Material.GREEN_TERRACOTTA, "§7Creates a new compression for this item and", "§7opens an editing GUI.", "", "§aClick to create!"), player -> {
            deleteMode = false;
            CompressionEditGUI compressionEditGUI = new CompressionEditGUI(this, plugin, null, compressions.size(), GUIUtils.GUIMode.CREATE);
            close(player);
            compressionEditGUI.open(player);
        });

        for (Compression compression : currentPageCompressions) {
            if (deleteMode) {
                addItem(GUIUtils.createGuiItem("§cDelete Compression " + compression.getDisplayIndex(), Material.RED_TERRACOTTA, createCompressionLore(compression)), player -> {
                    ConfirmGUI gui = new ConfirmGUI("§c§lThis is not permanent until you \"Save and Apply\" the compressor!", this, () -> deleteCompression(compression.getIndex()));
                    close(player);
                    gui.open(player);
                });
            } else {
                ItemStack finalItem = compression.getFinalItem();
                addItem(GUIUtils.createGuiItem("§aCompression " + compression.getDisplayIndex(), finalItem.getType(), createCompressionLore(compression)), player -> {
                    CompressionEditGUI compressionEditGUI = new CompressionEditGUI(this, plugin, compression, compression.getIndex(), GUIUtils.GUIMode.EDIT);
                    close(player);
                    compressionEditGUI.open(player);
                });
            }
        }
    }

    public void refreshPages() {
        final int openCompressionSlots = 28;

        numberOfPages = (int) Math.ceil(compressions.size() / (double) openCompressionSlots);

        List<Compression> compressionsListSorted = new ArrayList<>(compressions);

        if (sortType == GUIUtils.CompressionViewSortType.INDEX_DESCENDING) {
            compressionsListSorted.sort(Comparator.comparing(Compression::getIndex).reversed());
        }

        if (numberOfPages == 0) {
            numberOfPages = 1;
        }

        if (pageNumber == numberOfPages) {
            currentPageCompressions = compressionsListSorted.subList(openCompressionSlots * (pageNumber - 1), compressionsListSorted.size());
        } else {
            currentPageCompressions = compressionsListSorted.subList(openCompressionSlots * (pageNumber - 1), (openCompressionSlots * (pageNumber - 1)) + openCompressionSlots);
        }
    }

    private List<String> createCompressionLore(Compression compression) {
        List<String> lore = new ArrayList<>();

        lore.add("§7Compresses from:");
        lore.add("§f" + GUIUtils.getItemName(compression.getStartItem()) + "§7 x" + compression.getStartItemAmount());
        lore.add("");
        lore.add("§7Into:");
        lore.add("§f" + GUIUtils.getItemName(compression.getFinalItem()) + "§7 x" + compression.getFinalItemAmount());
        lore.add("");
        lore.add(deleteMode ? "§cClick to delete!" : "§aClick to edit!");

        return lore;
    }

    private List<String> filterLore() {
        List<String> currentLore = new ArrayList<>();

        for (GUIUtils.CompressionViewSortType value : sortTypeList) {
            currentLore.add((sortType.equals(value) ? "§a▶ " : "§f  ") + GUIUtils.readable(value.toString()));
        }

        return currentLore;
    }

    public void addCompression(Compression compression) {
        compressions.add(compression);

        refreshGUI();
    }

    public void changeCompression(int index, Compression newCompression) {
        compressions.set(index, newCompression);
        refreshGUI();
    }

    public void deleteCompression(int index) {
        compressions.remove(index);

        // Resets indices of compressions
        for (int i = 0; i < compressions.size(); i++) {
            Compression comp = compressions.get(i);
            compressions.set(i, new Compression(
                    comp.getStartItem(),
                    comp.getFinalItem(),
                    comp.getStartItemAmount(),
                    comp.getFinalItemAmount(),
                    i
            ));
        }

        refreshGUI();
    }
}
