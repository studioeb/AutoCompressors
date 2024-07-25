package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CompressorViewGUI extends BaseGUI{

    private int pageNumber = 1;
    private int numberOfPages = 0;
    private List<Compressor> currentPageCompressors = new ArrayList<>();

    public CompressorViewGUI() {
        super(54, "Edit Compressors");

        drawInventory();
    }

    // ITEM ACTION FUNCTIONS //
    public void nextPage() {
        pageNumber += 1;
        drawInventory();
    }

    public void previousPage() {
        pageNumber -= 1;
        drawInventory();
    }

    // DRAWING FUNCTIONS //
    public void drawInventory() {
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

        for (Compressor compressor : currentPageCompressors) {
            ItemStack compressorItem = compressor.getItemStack();
            ItemStack item = new ItemStack(compressorItem);
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = new ArrayList<>();
            if (itemMeta.hasLore()) {
                lore = item.getItemMeta().getLore();
            }
            lore.add("");
            lore.add("§aClick to edit compressor " + compressor.getIndex() + "!");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            addItem(item);
        }
    }

    public void refreshPages() {
        final int openCompressorSlots = 28;
        List<Compressor> compressorList = CompressorItemManager.getCompressorList();
        numberOfPages = (int) Math.ceil(compressorList.size() / (double) openCompressorSlots);

        if (pageNumber == numberOfPages) {
            currentPageCompressors = compressorList.subList(openCompressorSlots * (pageNumber - 1), compressorList.size());
        } else {
            currentPageCompressors = compressorList.subList(openCompressorSlots * (pageNumber - 1), (openCompressorSlots * (pageNumber - 1)) + openCompressorSlots);
        }
    }
}
