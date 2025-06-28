/*
Superclass all other GUIs are based off of
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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseGUI {
    private final Inventory inventory;
    private final UUID uuid;
    private final GUIUtils.GUIType guiType;
    private final Map<Integer, InputAction> actions = new HashMap<>();

    // CONSTRUCTOR //
    public BaseGUI(int invSize, String invName, GUIUtils.GUIType guiType) {
        uuid = UUID.randomUUID();
        this.guiType = guiType;
        inventory = Bukkit.createInventory(null, invSize, invName);
        GUIUtils.inventoriesByUUID.put(uuid, this);
    }

    public interface InputAction {
        void click(Player player);
    }

    // ADDING ITEMS //
    public void setItem(int slot, ItemStack itemStack, InputAction action) {
        inventory.setItem(slot, itemStack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack itemStack) {
        setItem(slot, itemStack, null);
    }

    public Map<Integer, ItemStack> addItem(ItemStack itemStack, InputAction action) {
        if (action != null && inventory.firstEmpty() > -1) {
            actions.put(inventory.firstEmpty(), action);
        }
        return inventory.addItem(itemStack);
    }

    public Map<Integer, ItemStack> addItem(ItemStack itemStack) {
        return addItem(itemStack, null);
    }

    // OPENING AND CLOSING INVENTORY //
    public void open(Player player) {
        player.openInventory(inventory);
        GUIUtils.openInventories.put(player.getUniqueId(), uuid);
    }

    public void close(Player player) {
        player.closeInventory();
        GUIUtils.openInventories.remove(player.getUniqueId());
    }



    // GETTERS //
    public Inventory getInventory() {
        return inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<Integer, InputAction> getActions() {
        return actions;
    }

    public GUIUtils.GUIType getGuiType() {
        return guiType;
    }
}
