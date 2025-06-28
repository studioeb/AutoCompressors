/*
Main AutoCompressors Class
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

package io.github.studioeb.autocompressors;

import io.github.studioeb.autocompressors.commands.EditCompressorsCommand;
import io.github.studioeb.autocompressors.commands.GetCompressorCommand;
import io.github.studioeb.autocompressors.commands.ReloadCommand;
import io.github.studioeb.autocompressors.events.CompressEvent;
import io.github.studioeb.autocompressors.events.GUIEvents;
import io.github.studioeb.autocompressors.items.CompressorItemManager;
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