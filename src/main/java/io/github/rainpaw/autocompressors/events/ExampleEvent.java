package io.github.rainpaw.autocompressors.events;

import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ExampleEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getInventory().addItem(CompressorItemManager.getCompressor(0).getItemStack());
    }
}
