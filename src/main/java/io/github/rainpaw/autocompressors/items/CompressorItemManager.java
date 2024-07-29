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

        FileConfiguration configuration = plugin.getConfig();

        for (int index = 1; configuration.get("compressors.compressor" + index) != null; index++) {
            String configPath = "compressors.compressor" + index;

            // Add §r (reset style) to beginning of each element in lore list
            List<String> lore = configuration.getStringList(configPath + ".lore");
            lore.replaceAll(s -> "§7"+s);

            Compressor compressor = new Compressor(
                    "§f" + configuration.getString(configPath + ".display-name"),
                    lore,
                    Objects.requireNonNull(Material.matchMaterial(
                            Objects.requireNonNull(configuration.getString(configPath + ".material"),
                                    "Not every compressor has a material section.")),
                            "The config file contains a material that does not exist."),
                    configuration.getBoolean(configPath + ".enchant-glint"),
                    CompressorLocations.valueOf(configuration.getString(configPath + ".location", "INVENTORY").toUpperCase()),
                    index
            );
            compressors.add(compressor);
        }
    }

    // Compressor getter
    public static Compressor getCompressor(int index) {
        return compressors.get(index);
    }

    // Compressors list length getter
    public static int getCompressorAmount() {
        return compressors.size();
    }

    public static List<Compressor> getCompressorList() {
        return compressors;
    }
}
