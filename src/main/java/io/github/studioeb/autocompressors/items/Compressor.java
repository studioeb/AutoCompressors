/*
Compressor class
Copyright (C) 2025 Ethan Bayer

This file is part of AutoCompressors.

AutoCompressors is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

AutoCompressors is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package io.github.studioeb.autocompressors.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Compressor {

    private final String displayName;
    private final List<String> itemLore;
    private final Material itemMaterial;
    private final boolean enchantGlint;
    private final CompressorItemManager.CompressorLocations location;
    private final List<Compression> compressions;
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

    public int getDisplayIndex() {
        return index + 1;
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

    public List<Compression> getCompressions() {
        return compressions;
    }

    /* Constructor */
    public Compressor(String name, List<String> lore, Material material, boolean hasEnchantGlint, CompressorItemManager.CompressorLocations compressorLocation, List<Compression> compressions, int index) {
        displayName = name;
        itemLore = lore;
        itemMaterial = material;
        enchantGlint = hasEnchantGlint;
        location = compressorLocation;
        this.compressions = compressions;
        this.index = index;
    }

}
