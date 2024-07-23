package io.github.rainpaw.autocompressors.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CompressEvent implements Listener {

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack item = event.getItem().getItemStack();
            Inventory playerInventory = player.getInventory();

            player.sendMessage("Event triggered");

            if (playerInventory.containsAtLeast(item, 64)) {
                player.sendMessage("You have a compressable amount of the item you just picked up!");
            }
        }
    }
}
