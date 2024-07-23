package io.github.rainpaw.autocompressors.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Compressor {

    private final String displayName;
    private final List<String> itemLore;
    private final Material itemMaterial;
    private final boolean enchantGlint;
    private final boolean stackable;
    private final NamespacedKey key;

    public ItemStack getItemStack() {
        ItemStack tempItem = new ItemStack(itemMaterial);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(itemLore);
        if (enchantGlint) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (!stackable) {
            meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.random());
        }
        tempItem.setItemMeta(meta);
        return tempItem;
    }

    public ItemStack getItemStack(int amount) {
        ItemStack tempItem = new ItemStack(itemMaterial, amount);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(itemLore);
        if (enchantGlint) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (!stackable) {
            meta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.random());
        }
        tempItem.setItemMeta(meta);
        return tempItem;
    }

    public Compressor(String name, List<String> lore, Material material, boolean hasEnchantGlint, boolean isStackable, NamespacedKey namespacedKey) {
        displayName = name;
        itemLore = lore;
        itemMaterial = material;
        enchantGlint = hasEnchantGlint;
        stackable = isStackable;
        key = namespacedKey;
    }

}
