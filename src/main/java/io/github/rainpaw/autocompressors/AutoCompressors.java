package io.github.rainpaw.autocompressors;

import io.github.rainpaw.autocompressors.commands.EditCompressorsCommand;
import io.github.rainpaw.autocompressors.commands.GetCompressorCommand;
import io.github.rainpaw.autocompressors.commands.ReloadCommand;
import io.github.rainpaw.autocompressors.events.CompressEvent;
import io.github.rainpaw.autocompressors.events.GUIEvents;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class AutoCompressors extends JavaPlugin {

    private final Logger LOGGER = getLogger();

    private File compressorConfigFile;
    private FileConfiguration compressorConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createCompressorConfig();

        CompressorItemManager.initializeItems(this);

        getServer().getPluginManager().registerEvents(new CompressEvent(), this);
        getServer().getPluginManager().registerEvents(new GUIEvents(), this);

        getCommand("getcompressor").setExecutor(new GetCompressorCommand());
        getCommand("autocompressorsreload").setExecutor(new ReloadCommand(this));
        getCommand("editcompressors").setExecutor(new EditCompressorsCommand(this));

        LOGGER.info("Plugin is enabled.");
    }

    /* Compressor Config */
    public FileConfiguration getCompressorConfig() {
        return this.compressorConfig;
    }

    public void saveCompressorConfig(FileConfiguration config) {
        try {
            config.save(compressorConfigFile);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private void createCompressorConfig() {
        compressorConfigFile = new File(getDataFolder(), "compressors.yml");
        if (!compressorConfigFile.exists()) {
            compressorConfigFile.getParentFile().mkdirs();
            saveResource("compressors.yml", false);
        }

        compressorConfig = YamlConfiguration.loadConfiguration(compressorConfigFile);

        try {
            compressorConfig.save(compressorConfigFile);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
    }
}