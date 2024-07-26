package io.github.rainpaw.autocompressors.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TempCompressor {

    private String displayName;
    private List<String> itemLore;
    private Material itemMaterial;
    private boolean enchantGlint;

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

    /* Getters and Setters */
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return itemLore;
    }

    public void setLore(List<String> itemLore) {
        this.itemLore = itemLore;
    }

    public Material getMaterial() {
        return itemMaterial;
    }

    public void setMaterial(Material itemMaterial) {
        this.itemMaterial = itemMaterial;
    }

    public boolean hasEnchantGlint() {
        return enchantGlint;
    }

    public void setEnchantGlint(boolean enchantGlint) {
        this.enchantGlint = enchantGlint;
    }

    public TempCompressor(Compressor compressor) {
        displayName = compressor.getDisplayName();
        itemLore = compressor.getItemLore();
        itemMaterial = compressor.getMaterial();
        enchantGlint = compressor.hasEnchantGlint();
    }

}
