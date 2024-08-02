package io.github.rainpaw.autocompressors.events;

import io.github.rainpaw.autocompressors.items.Compression;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompressEvent implements Listener {

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack item = event.getItem().getItemStack();
            PlayerInventory playerInventory = player.getInventory();

            for (Compressor compressor : CompressorItemManager.getCompressorList()) {
                if (compressor.getLocation() == CompressorItemManager.CompressorLocations.OFFHAND) {
                    if (playerInventory.getItemInOffHand().isSimilar(compressor.getItemStack())) {
                        for (Compression compression : compressor.getCompressions()) {
                            int startItemAmountInInv = 0;
                            for (Map.Entry<Integer, ? extends ItemStack> entry : playerInventory.all(compression.getStartItem().getType()).entrySet()) {
                                if (entry.getValue().isSimilar(compression.getStartItem())) {
                                    startItemAmountInInv += entry.getValue().getAmount();
                                }
                            }
                            playerInventory.removeItem(compression.getStartItem((int) (((double) startItemAmountInInv) / 9) * 9));
                            playerInventory.addItem(compression.getFinalItem(startItemAmountInInv / 9));
                        }
                    }
                } else if (compressor.getLocation() == CompressorItemManager.CompressorLocations.INVENTORY) {
                    if (playerInventory.containsAtLeast(compressor.getItemStack(), 1)) {
                        for (Compression compression : compressor.getCompressions()) {
                            int startItemAmountInInv = 0;
                            for (Map.Entry<Integer, ? extends ItemStack> entry : playerInventory.all(compression.getStartItem().getType()).entrySet()) {
                                if (entry.getValue().isSimilar(compression.getStartItem())) {
                                    startItemAmountInInv += entry.getValue().getAmount();
                                }
                            }
                            playerInventory.removeItem(compression.getStartItem((int) (((double) startItemAmountInInv) / 9) * 9));
                            playerInventory.addItem(compression.getFinalItem(startItemAmountInInv / 9));
                        }
                    }
                }
            }
        }
    }
}
