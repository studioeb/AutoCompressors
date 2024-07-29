package io.github.rainpaw.autocompressors.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Compressor {

    private final String displayName;
    private final List<String> itemLore;
    private final Material itemMaterial;
    private final boolean enchantGlint;
    private final CompressorItemManager.CompressorLocations location;
    private final int index;

    public ItemStack getItemStack() {
        ItemStack tempItem = new ItemStack(itemMaterial);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(itemLore);
        if (enchantGlint) {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
        tempItem.setItemMeta(meta);
        return tempItem;
    }

    /* Getters for Values */
    public int getIndex() {
        return index;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return itemLore;
    }

    public Material getMaterial() {
        return itemMaterial;
    }

    public boolean hasEnchantGlint() {
        return enchantGlint;
    }

    public CompressorItemManager.CompressorLocations getLocation() {
        return location;
    }

    /* Constructor */
    public Compressor(String name, List<String> lore, Material material, boolean hasEnchantGlint, CompressorItemManager.CompressorLocations compressorLocation, int index) {
        displayName = name;
        itemLore = lore;
        itemMaterial = material;
        enchantGlint = hasEnchantGlint;
        location = compressorLocation;
        this.index = index;
    }

}
