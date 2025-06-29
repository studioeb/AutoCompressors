/*
Event where the item is compressed into another
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

import io.github.studioeb.autocompressors.items.Compression;
import io.github.studioeb.autocompressors.items.Compressor;
import io.github.studioeb.autocompressors.items.CompressorItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

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
