/*
GUI where items are inserted into the compressor edit interface
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

package io.github.studioeb.autocompressors.guis;

import io.github.studioeb.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemInsertGUI extends BaseGUI {

    private final CompressionEditGUI editGUI;
    private ItemStack itemInSlot = null;
    private final String itemType;

    public ItemInsertGUI(CompressionEditGUI editGUI, String itemType) {
        super(45, "Insert Item", GUIUtils.GUIType.ITEM_ENTER);

        this.editGUI = editGUI;
        this.itemType = itemType;

        refreshGUI();
    }

    public void refreshGUI() {
        GUIUtils.fillAllSlots(getInventory(), GUIUtils.getBgItem());
        setItem(40, GUIUtils.createGuiItem("§aBack", Material.ARROW), player -> {
            close(player);
            editGUI.open(player);
        });
        if (itemInSlot == null) {
            setItem(13, null);
            setItem(22, GUIUtils.createGuiItem("§eChange Item", Material.RED_TERRACOTTA, "§7Place an item in the above slot to", "§7change the compression " + itemType + " item", "§7to that item!"));
        } else {
            setItem(13, itemInSlot, player -> {
                itemInSlot = null;
                refreshGUI();
            });
            setItem(22, GUIUtils.createGuiItem("§aConfirm", Material.GREEN_TERRACOTTA, "§7This will change the compression's", "§7" + itemType + " item to this item.", "§7This is not permanent until you", "§7\"Save and Apply\" on the compressor", "§7edit screen."), player -> {
                if (itemType.equals("start")) {
                    editGUI.changeStartItem(itemInSlot);
                } else {
                    editGUI.changeFinalItem(itemInSlot);
                }
                close(player);
                editGUI.open(player);
            });
        }
    }

    public void getNewItem(ItemStack item) {
        itemInSlot = item;
        refreshGUI();
    }
}
