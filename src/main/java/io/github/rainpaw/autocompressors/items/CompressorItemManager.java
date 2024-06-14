package io.github.rainpaw.autocompressors.items;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompressorItemManager {

    private static List<Compressor> compressors = new ArrayList<>();

    public static void initializeItems(FileConfiguration configuration) {
        for (int index = 1; configuration.get("compressors.compressor" + index) != null; index++) {
            String configPath = "compressors.compressor" + index;
            Compressor compressor = new Compressor(
                    configuration.getString(configPath + ".display-name"),
                    configuration.getStringList(configPath + ".lore"),
                    Objects.requireNonNull(Material.matchMaterial(
                            Objects.requireNonNull(configuration.getString(configPath + ".material"),
                                    "Not every compressor has a material section.")),
                            "The config file contains a material that does not exist."),
                    configuration.getBoolean(configPath + ".enchant-glint"),
                    configuration.getBoolean(configPath + ".stackable")
            );
            compressors.add(compressor);
        }
    }

    // Compressor getter
    public static Compressor getCompressor(int index) {
        return compressors.get(index);
    }
}
