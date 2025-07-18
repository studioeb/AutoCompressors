/*
Static methods for managing compressors
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

package io.github.studioeb.autocompressors.items;

import io.github.studioeb.autocompressors.AutoCompressors;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompressorItemManager {

    private static final List<Compressor> compressors = new ArrayList<>();

    private static char alternateColorCode;

    public enum CompressorLocations {
        INVENTORY,
        OFFHAND
    }

    public static void initializeItems(AutoCompressors plugin) {
        compressors.clear();

        alternateColorCode = plugin.getConfig().getString("alternate-color-code-character", "&").charAt(0);

        FileConfiguration configuration = plugin.getCompressorConfig();

        for (int index = 0; configuration.get("compressor" + (index + 1)) != null; index++) {
            String configPath = "compressor" + (index + 1);

            Compressor compressor = new Compressor(
                    configuration.getString(configPath + ".display-name"),
                    configuration.getStringList(configPath + ".lore"),
                    Objects.requireNonNull(Material.matchMaterial(
                            Objects.requireNonNull(configuration.getString(configPath + ".material"),
                                    "Not every compressor has a material section.")),
                            "The config file contains a material that does not exist."),
                    configuration.getBoolean(configPath + ".enchant-glint", false),
                    CompressorLocations.valueOf(configuration.getString(configPath + ".location", "INVENTORY").toUpperCase()),
                    getCompressionList(configuration, configPath),
                    index
            );
            compressors.add(compressor);
        }
    }

    private static List<Compression> getCompressionList(FileConfiguration config, String compressorConfigPath) {
        List<Compression> compressionList = new ArrayList<>();

        for (int index = 0; config.get(compressorConfigPath + ".compressions.compression" + (index + 1)) != null; index++) {
            String configPath = compressorConfigPath + ".compressions.compression" + (index + 1);

            compressionList.add(new Compression(
                    config.getItemStack(configPath + ".start-item"),
                    config.getItemStack(configPath + ".final-item"),
                    config.getInt(configPath + ".start-amount"),
                    config.getInt(configPath + ".final-amount"),
                    index
            ));
        }

        return compressionList;
    }

    public static char getAlternateColorCode() {
        return alternateColorCode;
    }

    // Compressor getter
    public static Compressor getCompressor(int index) {
        return compressors.get(index);
    }

    public static void setCompressor(int index, Compressor compressor) {
        compressors.set(index, compressor);
    }

    public static void addCompressor(Compressor compressor) {
        compressors.add(compressor);
    }

    // Compressors list length getter
    public static int getCompressorAmount() {
        return compressors.size();
    }

    public static List<Compressor> getCompressorList() {
        return compressors;
    }
}
