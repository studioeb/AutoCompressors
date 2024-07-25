package io.github.rainpaw.autocompressors.events;

import io.github.rainpaw.autocompressors.guis.BaseGUI;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GUIEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) { return; }

        final Player player = (Player) event.getWhoClicked();
        final UUID playerUUID = player.getUniqueId();

        final UUID inventoryUUID = GUIUtils.openInventories.get(playerUUID);
        if (inventoryUUID != null) {
            event.setCancelled(true);
            BaseGUI gui = GUIUtils.inventoriesByUUID.get(inventoryUUID);
            BaseGUI.InputAction action = gui.getActions().get(event.getSlot());

            if (action != null) {
                action.click(player);
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
        final Player player = (Player) event.getPlayer();
        final UUID playerUUID = player.getUniqueId();

        GUIUtils.openInventories.remove(playerUUID);
    }
}
