package io.github.rainpaw.autocompressors.events;

import org.bukkit.Material;
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

            if (item.getType() == Material.DIAMOND) {
                if (playerInventory.containsAtLeast(item, 9)) {
                    playerInventory.removeItem(new ItemStack(Material.DIAMOND, 9));
                    playerInventory.addItem(new ItemStack(Material.DIAMOND_BLOCK, 1));
                }
            }
        }
    }
}
