/*
Compression class (a holder of a transfer of one item to another)
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

import org.bukkit.inventory.ItemStack;

public class Compression {
    private final int startItemAmount;
    private final int finalItemAmount;
    private final ItemStack startItem;
    private final ItemStack finalItem;
    private final int index;

    public Compression(ItemStack startItem, ItemStack finalItem, int startItemAmount, int finalItemAmount, int index) {
        this.startItem = startItem;
        this.finalItem = finalItem;
        this.startItemAmount = startItemAmount;
        this.finalItemAmount = finalItemAmount;
        this.index = index;
    }

    /* Getters */
    public ItemStack getStartItem() {
        return startItem;
    }

    public ItemStack getStartItem(int amount) {
        ItemStack item = new ItemStack(startItem);
        item.setAmount(amount);
        return item;
    }

    public ItemStack getFinalItem() {
        return finalItem;
    }

    public ItemStack getFinalItem(int amount) {
        ItemStack item = new ItemStack(finalItem);
        item.setAmount(amount);
        return item;
    }

    public int getStartItemAmount() {
        return startItemAmount;
    }
    public int getFinalItemAmount() {
        return finalItemAmount;
    }

    public int getIndex() {
        return index;
    }
    public int getDisplayIndex() {
        return index + 1;
    }
}
