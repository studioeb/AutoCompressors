package io.github.rainpaw.autocompressors;

import io.github.rainpaw.autocompressors.commands.EditCompressorsCommand;
import io.github.rainpaw.autocompressors.commands.GetCompressorCommand;
import io.github.rainpaw.autocompressors.commands.ReloadCommand;
import io.github.rainpaw.autocompressors.events.CompressEvent;
import io.github.rainpaw.autocompressors.events.GUIEvents;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AutoCompressors extends JavaPlugin {

    private final Logger LOGGER = getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        CompressorItemManager.initializeItems(this);

        getServer().getPluginManager().registerEvents(new CompressEvent(), this);
        getServer().getPluginManager().registerEvents(new GUIEvents(), this);

        getCommand("getcompressor").setExecutor(new GetCompressorCommand());
        getCommand("autocompressorsreload").setExecutor(new ReloadCommand(this));
        getCommand("editcompressors").setExecutor(new EditCompressorsCommand(this));

        LOGGER.info("Plugin is enabled.");
    }
}