package io.github.rainpaw.autocompressors.guis;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.conversations.DisplayNamePrompt;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import io.github.rainpaw.autocompressors.items.TempCompressor;
import io.github.rainpaw.autocompressors.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;

import java.util.ArrayList;
import java.util.List;

public class CompressorEditGUI extends BaseGUI {

    private final AutoCompressors plugin;
    private final ConversationFactory cf;

    private Conversation displayNameAsk;
    private Conversation loreAsk;

    private final TempCompressor compressor;

    private final CompressorViewGUI viewGUI;

    public CompressorEditGUI(Compressor compressor, AutoCompressors pl, CompressorViewGUI viewGUI) {
        super(27, "Editing " + compressor.getDisplayName());

        this.compressor = CompressorItemManager.getNewTempCompressor(compressor);
        this.viewGUI = viewGUI;
        plugin = pl;
        cf = new ConversationFactory(plugin);

        refreshGUI();

    }

    public void refreshGUI() {
        GUIUtils.drawGrayGlassBorder(getInventory());
        setItem(21, GUIUtils.createGuiItem("§cBack", Material.ARROW, "§c§lWARNING: THIS WILL DELETE UNSAVED CHANGES!"), player -> {
            close(player);
            viewGUI.open(player); // TODO: Replace with a "Confirm" screen
            System.out.println(GUIUtils.inventoriesByUUID);
        });
        setItem(23, GUIUtils.createGuiItem("§aSave and Apply", Material.EMERALD, "§7Saves your changes and reloads the config."));
        setItem(26, GUIUtils.appendLore(compressor.getItemStack(), "", "§7§oThis is a preview of the compressor with current changes.", "§7§oMake sure to apply the changes before closing!"));

        setItem(10, GUIUtils.createGuiItem("§aDisplay Name", Material.NAME_TAG, "§7Current display name:", compressor.getDisplayName(), "", "§aClick to change!"), player -> {
            displayNameAsk = cf.withFirstPrompt(new DisplayNamePrompt(this, player)).withLocalEcho(false).buildConversation(player);
            close(player);
            displayNameAsk.begin();
        });

        List<String> currentLore = new ArrayList<>();
        currentLore.add("§7Current lore:");
        currentLore.addAll(compressor.getLore());
        currentLore.add("");
        currentLore.add("§aClick to change!");

        setItem(11, GUIUtils.createGuiItem("§aLore", Material.PAPER, currentLore));
        setItem(12, GUIUtils.createGuiItem("§aMaterial", compressor.getMaterial(), "§7Current material:", compressor.getMaterial().name(), "", "§aClick to change!"));
        setItem(13, GUIUtils.getBgItem());
        setItem(14, GUIUtils.createGuiItem("§aEnchant Glint", Material.ENCHANTED_BOOK, "§7Enchant glint: " + (compressor.hasEnchantGlint() ? "§aenabled" : "§cdisabled"), "", "§aClick to switch!"));
        setItem(15, GUIUtils.createGuiItem("§aCompressions", Material.PAINTING, "§7Current compressions:", "", "§aClick to edit!"));
    }

}
