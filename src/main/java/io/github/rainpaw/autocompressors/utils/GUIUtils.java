package io.github.rainpaw.autocompressors.utils;

import io.github.rainpaw.autocompressors.guis.BaseGUI;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIUtils {

    private static final ItemStack backgroundItem = createGuiItem("§r",Material.GRAY_STAINED_GLASS_PANE, 1);

    public final static Map<UUID, BaseGUI> inventoriesByUUID = new HashMap<>(); // Inv uuid + instance
    public final static Map<UUID, UUID> openInventories = new HashMap<>(); // Player UUID + inv UUID

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

    public static ItemStack getBgItem() {
        return backgroundItem;
    }
}
