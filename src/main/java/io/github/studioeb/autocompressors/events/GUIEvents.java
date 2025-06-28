/*
Events that happen in a GUI interface
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

package io.github.studioeb.autocompressors.events;

import io.github.studioeb.autocompressors.guis.BaseGUI;
import io.github.studioeb.autocompressors.guis.ItemInsertGUI;
import io.github.studioeb.autocompressors.guis.MaterialEnterGUI;
import io.github.studioeb.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GUIEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) { return; }
        if (event.getClickedInventory() == null) { return; }

        final Player player = (Player) event.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        final UUID inventoryUUID = GUIUtils.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            event.setCancelled(true);
            BaseGUI gui = GUIUtils.inventoriesByUUID.get(inventoryUUID);
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER) && gui.getGuiType().equals(GUIUtils.GUIType.MATERIAL_ENTER)) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                    MaterialEnterGUI matGUI = (MaterialEnterGUI) gui;
                    matGUI.getNewMaterial(event.getCurrentItem().getType());
                }
            } else if (event.getClickedInventory().getType().equals(InventoryType.PLAYER) && gui.getGuiType().equals(GUIUtils.GUIType.ITEM_ENTER)) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                    ItemInsertGUI itemGUI = (ItemInsertGUI) gui;
                    ItemStack item = new ItemStack(event.getCurrentItem());
                    item.setAmount(1);
                    itemGUI.getNewItem(item);
                }
            }

            if (!event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                BaseGUI.InputAction action = gui.getActions().get(event.getSlot());
                if (action != null) {
                    action.click(player);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final UUID playerUUID = player.getUniqueId();

        GUIUtils.openInventories.remove(playerUUID);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID playerUUID = player.getUniqueId();

        GUIUtils.openInventories.remove(playerUUID);
    }
}
