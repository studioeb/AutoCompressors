package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.conversations.DisplayNamePrompt;
import io.github.rainpaw.autocompressors.conversations.LoreLinePrompt;
import io.github.rainpaw.autocompressors.conversations.PromptCanceller;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import io.github.rainpaw.autocompressors.items.ModifiableCompressor;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class CompressorEditGUI extends BaseGUI {

    private final ConversationFactory cf;

    private Conversation displayNameAsk;
    private Conversation loreAsk;

    private final ModifiableCompressor compressor;
    private final CompressorViewGUI viewGUI;

    private final List<CompressorItemManager.CompressorLocations> locationsList = Arrays.asList(CompressorItemManager.CompressorLocations.values());
    private ListIterator<CompressorItemManager.CompressorLocations> locationsIterator;

    // Temp compressor for editing
    public CompressorEditGUI(Compressor comp, AutoCompressors plugin, CompressorViewGUI viewGUI) {
        super(27, "Editing " + comp.getDisplayName(), GUIUtils.GUIType.NORMAL);

        compressor = new ModifiableCompressor(comp);
        cf = new ConversationFactory(plugin);
        this.viewGUI = viewGUI;
        locationsIterator = locationsList.listIterator(locationsList.indexOf(compressor.getLocation()));
        locationsIterator = locationsIterator.hasNext() ? locationsIterator : locationsList.listIterator();
        locationsIterator.next();

        refreshGUI();
    }

    public void refreshGUI() {
        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(21, GUIUtils.createGuiItem("§cBack", Material.ARROW, "§c§lWARNING: THIS WILL DELETE UNSAVED CHANGES!"), player -> {
            close(player);
            viewGUI.open(player); // TODO: Replace with a "Confirm" screen
        });
        setItem(23, GUIUtils.createGuiItem("§aSave and Apply", Material.EMERALD, "§7Saves your changes and reloads the config."));
        setItem(26, GUIUtils.appendLore(compressor.getItemStack(), "", "§7§oThis is a preview of the compressor with current changes.", "§7§oMake sure to apply the changes before closing!"));

        setItem(10, GUIUtils.createGuiItem("§aDisplay Name", Material.NAME_TAG, "§7Current display name:", compressor.getDisplayName(), "", "§aClick to change!"), player -> {
            displayNameAsk = cf.withFirstPrompt(new DisplayNamePrompt(player, compressor, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player);
            close(player);
            displayNameAsk.begin();
        });

        setItem(11, GUIUtils.createGuiItem("§aLore", Material.PAPER, createLoreEditLore()), player -> {
            loreAsk = cf.withFirstPrompt(new LoreLinePrompt(compressor.getLore().size(), player, compressor, this)).withLocalEcho(false).withConversationCanceller(new PromptCanceller(this, player)).buildConversation(player);
            close(player);
            loreAsk.begin();
        });
        setItem(12, GUIUtils.createGuiItem("§aMaterial", compressor.getMaterial(), "§7Current material:", "§f" + GUIUtils.readable(compressor.getMaterial().name()), "", "§aClick to change!"), player -> {
            MaterialEnterGUI gui = new MaterialEnterGUI(this, compressor);
            close(player);
            gui.open(player);
        });
        setItem(13, GUIUtils.getBgItem());
        setItem(14, GUIUtils.createGuiItem("§aEnchant Glint", compressor.hasEnchantGlint() ? Material.ENCHANTED_BOOK : Material.BOOK, "§7Enchant glint: " + (compressor.hasEnchantGlint() ? "§aenabled" : "§cdisabled"), "", "§aClick to switch!"), player -> {
            compressor.setEnchantGlint(!compressor.hasEnchantGlint());
            refreshGUI();
        });
        setItem(15, GUIUtils.createGuiItem("§aLocation in Inventory", Material.SPYGLASS, createLocationEditLore()), player -> {
            if (locationsIterator.hasNext()) {
                compressor.setLocation(locationsIterator.next());
            } else {
                locationsIterator = locationsList.listIterator();
                compressor.setLocation(locationsIterator.next());
            }
            refreshGUI();
        });
        setItem(16, GUIUtils.createGuiItem("§aCompressions", Material.PAINTING, "§7Current compressions:", "", "§aClick to edit!"));
    }

    public List<String> createLoreEditLore() {
        List<String> currentLore = new ArrayList<>();
        currentLore.add("§7The item's description.");
        currentLore.add("");
        currentLore.add("§7Current lore:");
        currentLore.addAll(compressor.getLore());
        currentLore.add("");
        currentLore.add("§aClick to change!");
        return currentLore;
    }

    public List<String> createLocationEditLore() {
        List<String> currentLore = new ArrayList<>();
        currentLore.add("§7The place in the player inventory where");
        currentLore.add("§7this compressor will work.");
        currentLore.add("");
        currentLore.add("§7Current location:");

        for (CompressorItemManager.CompressorLocations value : CompressorItemManager.CompressorLocations.values()) {
            currentLore.add((compressor.getLocation().equals(value) ? "§a▶ " : "§f  ") + GUIUtils.readable(value.toString()));
        }
        currentLore.add("");
        currentLore.add("§aClick to cycle!");
        return currentLore;
    }
}
