package io.github.rainpaw.autocompressors.utils;

import io.github.rainpaw.autocompressors.guis.BaseGUI;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GUIUtils {

    private static final ItemStack backgroundItem = createGuiItem("§r",Material.GRAY_STAINED_GLASS_PANE, 1);

    public static final Map<UUID, BaseGUI> inventoriesByUUID = new HashMap<>(); // Inv uuid + instance
    public static final Map<UUID, UUID> openInventories = new HashMap<>(); // Player UUID + inv UUID


    public enum GUIType {
        NORMAL,
        MATERIAL_ENTER
    }

    public static void drawGrayGlassBorder(Inventory inv) {
        final int invRows = inv.getSize() / 9;

        for (int row = 0; row < invRows; row++) {
            if (row == 0 || row == invRows - 1) { // Is first or last row
                for (int slot = 0; slot < 9; slot++) {
                    inv.setItem(slot + (row * 9), backgroundItem);
                }
            } else { // Is one of the middle rows
                inv.setItem(row * 9, backgroundItem);
                inv.setItem((row * 9) + 8, backgroundItem);
            }
        }
    }

    public static void fillAllSlots(Inventory inv, ItemStack item) {
        for (int slot = 0; slot < inv.getSize(); slot++) {
            inv.setItem(slot, item);
        }
    }

    public static String readable(String string) {
        String lowerString = string.toLowerCase();
        String[] words = lowerString.split("_");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static ItemStack createGuiItem(String name, Material mat, int amount, String... lore) {
        final ItemStack item = new ItemStack(mat, amount);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(String name, Material mat, String... lore) {
        final ItemStack item = new ItemStack(mat, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createGuiItem(String name, Material mat, List<String> lore) {
        final ItemStack item = new ItemStack(mat, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack appendLore(ItemStack itemStack, String... addedLore) {
        ItemStack item = new ItemStack(itemStack);

        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (meta.hasLore()) {
            lore = meta.getLore();
        }

        lore.addAll(Arrays.asList(addedLore));

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getBgItem() {
        return backgroundItem;
    }
}
