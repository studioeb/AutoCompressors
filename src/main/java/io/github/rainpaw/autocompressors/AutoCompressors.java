package io.github.rainpaw.autocompressors;

import io.github.rainpaw.autocompressors.commands.GetCompressorCommand;
import io.github.rainpaw.autocompressors.commands.ReloadCommand;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AutoCompressors extends JavaPlugin {

    private final Logger LOGGER = getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        CompressorItemManager.initializeItems(this);

        getCommand("getcompressor").setExecutor(new GetCompressorCommand());
        getCommand("autocompressorsreload").setExecutor(new ReloadCommand(this));

        LOGGER.info("Plugin is enabled.");
    }
}