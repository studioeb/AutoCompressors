package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.conversations.DisplayNamePrompt;
import io.github.rainpaw.autocompressors.conversations.LoreLinePrompt;
import io.github.rainpaw.autocompressors.conversations.PromptCanceller;
import io.github.rainpaw.autocompressors.items.Compression;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class CompressorEditGUI extends BaseGUI {

    private final AutoCompressors plugin;

    private final ConversationFactory cf;

    private Conversation displayNameAsk;
    private Conversation loreAsk;

    private final GUIUtils.GUIMode guiMode;
    private final CompressorViewGUI viewGUI;

    private final List<CompressorItemManager.CompressorLocations> locationsList = Arrays.asList(CompressorItemManager.CompressorLocations.values());
    private ListIterator<CompressorItemManager.CompressorLocations> locationsIterator;

    private String tempDisplayName;
    private final List<String> tempLore = new ArrayList<>();
    private Material tempMaterial;
    private boolean tempEnchantGlint = false;
    private CompressorItemManager.CompressorLocations tempLocation = CompressorItemManager.CompressorLocations.INVENTORY;
    private final List<Compression> tempCompressions = new ArrayList<>();

    private final int compressorIndex;

    public CompressorEditGUI(Compressor comp, int index, AutoCompressors plugin, CompressorViewGUI viewGUI, GUIUtils.GUIMode mode) {
        super(27, mode.equals(GUIUtils.GUIMode.EDIT) ? "Editing " + comp.getDisplayName() : "Creating Compressor", GUIUtils.GUIType.NORMAL);

        cf = new ConversationFactory(plugin);
        this.viewGUI = viewGUI;
        this.plugin = plugin;
        guiMode = mode;

        compressorIndex = index;

        if (comp != null) {
            tempDisplayName = comp.getDisplayName();
            tempLore.addAll(comp.getLore());
            tempMaterial = comp.getMaterial();
            tempEnchantGlint = comp.hasEnchantGlint();
            tempLocation = comp.getLocation();
            tempCompressions.addAll(comp.getCompressions());
        } else {
            tempDisplayName = "§fCompressor " + (index + 1);
        }

        locationsIterator = locationsList.listIterator(locationsList.indexOf(tempLocation));
        locationsIterator = locationsIterator.hasNext() ? locationsIterator : locationsList.listIterator();
        locationsIterator.next();

        refreshGUI();
    }

    public void refreshGUI() {
        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(21, GUIUtils.createGuiItem("§cBack", Material.ARROW, "§c§lWARNING: THIS WILL DELETE UNSAVED CHANGES!"), player -> {
            close(player);
            viewGUI.open(player);
        });
        setItem(23, GUIUtils.createGuiItem("§aSave and Apply", Material.GREEN_TERRACOTTA, "§7Saves your changes and reloads the config.", "§7§lThis is permanent!", "", "§aClick to save!"), player -> {
            Compressor finalCompressor = new Compressor(
                    tempDisplayName,
                    tempLore,
                    tempMaterial,
                    tempEnchantGlint,
                    tempLocation,
                    tempCompressions,
                    compressorIndex
                    );

            if (guiMode.equals(GUIUtils.GUIMode.EDIT)) {
                CompressorItemManager.setCompressor(compressorIndex, finalCompressor);
            } else {
                CompressorItemManager.addCompressor(finalCompressor);
            }

            FileConfiguration config = plugin.getCompressorConfig();
            String configPath = "compressor" + finalCompressor.getDisplayIndex();
            config.set(configPath + ".display-name", finalCompressor.getDisplayName());
            config.set(configPath + ".lore", finalCompressor.getLore());
            config.set(configPath + ".material", finalCompressor.getMaterial().toString());
            config.set(configPath + ".enchant-glint", finalCompressor.hasEnchantGlint());
            config.set(configPath + ".location", finalCompressor.getLocation().toString());

            config.set(configPath + ".compressions", null);
            for (Compression compression : finalCompressor.getCompressions()) {
                String compressionPath = configPath + ".compressions.compression" + compression.getDisplayIndex();
                config.set(compressionPath + ".start-item", compression.getStartItem());
                config.set(compressionPath + ".final-item", compression.getFinalItem());
                config.set(compressionPath + ".start-amount", compression.getStartItemAmount());
                config.set(compressionPath + ".final-amount", compression.getFinalItemAmount());
            }

            plugin.saveCompressorConfig(config);

            viewGUI.refreshGUI();
            close(player);
            viewGUI.open(player);
        });

        if (tempMaterial == null) {
            setItem(26, GUIUtils.createGuiItem("§cCompressor Preview", Material.GRAY_CONCRETE, "§7Once a material has been set for this compressor,", "§7a preview will show up here."));
        } else {
            setItem(26, GUIUtils.appendLore(new Compressor(tempDisplayName, tempLore, tempMaterial, tempEnchantGlint, tempLocation, tempCompressions, compressorIndex).getItemStack(), "", "§7§oThis is a preview of the compressor with current changes.", "§7§oMake sure to apply the changes before closing!"));
        }

        setItem(10, GUIUtils.createGuiItem("§aDisplay Name", Material.NAME_TAG, "§7Current display name:", tempDisplayName, "", "§aClick to change!"), player -> {
            displayNameAsk = cf.withFirstPrompt(new DisplayNamePrompt(player, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player);
            close(player);
            displayNameAsk.begin();
        });

        setItem(11, GUIUtils.createGuiItem("§aLore", Material.PAPER, createLoreEditLore()), player -> {
            loreAsk = cf.withFirstPrompt(new LoreLinePrompt(tempLore.size(), player, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player);
            close(player);
            loreAsk.begin();
        });

        if (tempMaterial == null) {
            setItem(12, GUIUtils.createGuiItem("§aMaterial", Material.WHITE_CONCRETE, "§7Current material: §cNone", "", "§aClick to change!"), player -> {
                MaterialEnterGUI gui = new MaterialEnterGUI(this);
                close(player);
                gui.open(player);
            });
        } else {
            setItem(12, GUIUtils.createGuiItem("§aMaterial", tempMaterial, "§7Current material:", "§f" + GUIUtils.readable(tempMaterial.name()), "", "§aClick to change!"), player -> {
                MaterialEnterGUI gui = new MaterialEnterGUI(this);
                close(player);
                gui.open(player);
            });
        }
        setItem(13, GUIUtils.getBgItem());
        setItem(14, GUIUtils.createGuiItem("§aEnchant Glint", tempEnchantGlint ? Material.ENCHANTED_BOOK : Material.BOOK, "§7Enchant glint: " + (tempEnchantGlint ? "§aenabled" : "§cdisabled"), "", "§aClick to switch!"), player -> {
            tempEnchantGlint = !tempEnchantGlint;
            refreshGUI();
        });
        setItem(15, GUIUtils.createGuiItem("§aLocation in Inventory", Material.SPYGLASS, createLocationEditLore()), player -> {
            if (!locationsIterator.hasNext()) {
                locationsIterator = locationsList.listIterator();
            }
            tempLocation = locationsIterator.next();

            refreshGUI();
        });
        setItem(16, GUIUtils.createGuiItem("§aCompressions", Material.PAINTING, "§7The compressions that this auto compressor", "§7will perform.", "", "§aClick to view!"), player -> {
            CompressionViewGUI gui = new CompressionViewGUI(this, tempCompressions, plugin);
            close(player);
            gui.open(player);
        });
    }

    public void setDisplayName(String name) {
        tempDisplayName = name;
        refreshGUI();
    }

    public void setLoreAtLine(int index, String input) {
        tempLore.set(index, input);
        refreshGUI();
    }

    public void addToLore(String input) {
        tempLore.add(input);
        refreshGUI();
    }

    public void deleteLoreAtLine(int index) {
        tempLore.remove(index);
        refreshGUI();
    }

    public void setMaterial(Material material) {
        tempMaterial = material;
        refreshGUI();
    }

    private List<String> createLoreEditLore() {
        List<String> currentLore = new ArrayList<>();
        currentLore.add("§7The item's description.");
        currentLore.add("");
        currentLore.add("§7Current lore:");
        if (!tempLore.isEmpty()) {
            currentLore.addAll(tempLore);
        } else {
            currentLore.add("§cThis item doesn't have any lore!");
        }
        currentLore.add("");
        currentLore.add("§aClick to change!");
        return currentLore;
    }

    private List<String> createLocationEditLore() {
        List<String> currentLore = new ArrayList<>();
        currentLore.add("§7The place in the player inventory where");
        currentLore.add("§7this compressor will work.");
        currentLore.add("");
        currentLore.add("§7Current location:");

        for (CompressorItemManager.CompressorLocations value : CompressorItemManager.CompressorLocations.values()) {
            currentLore.add((tempLocation.equals(value) ? "§a▶ " : "§f  ") + GUIUtils.readable(value.toString()));
        }
        currentLore.add("");
        currentLore.add("§aClick to cycle!");
        return currentLore;
    }
}
