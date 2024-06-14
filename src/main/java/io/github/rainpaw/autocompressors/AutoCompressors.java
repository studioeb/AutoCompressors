package io.github.rainpaw.autocompressors;

import io.github.rainpaw.autocompressors.events.ExampleEvent;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoCompressors extends JavaPlugin {

    @Override
    public void onEnable() {
        CompressorItemManager.initializeItems(getConfig());
        getServer().getPluginManager().registerEvents(new ExampleEvent(), this);

        getLogger().info("Plugin is enabled.");
    }

}