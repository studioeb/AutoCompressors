package io.github.rainpaw.autocompressors.items;

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
