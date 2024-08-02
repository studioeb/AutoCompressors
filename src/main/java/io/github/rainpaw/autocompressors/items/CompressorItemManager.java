package io.github.rainpaw.autocompressors.items;

import io.github.rainpaw.autocompressors.AutoCompressors;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompressorItemManager {

    private static final List<Compressor> compressors = new ArrayList<>();

    public enum CompressorLocations {
        INVENTORY,
        OFFHAND
    }

    public static void initializeItems(AutoCompressors plugin) {
        compressors.clear();

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
