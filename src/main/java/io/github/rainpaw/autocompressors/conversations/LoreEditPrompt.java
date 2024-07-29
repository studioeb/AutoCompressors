package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import io.github.rainpaw.autocompressors.items.ModifiableCompressor;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import java.util.List;

public class LoreEditPrompt extends StringPrompt {

    private final int lineNumber;
    private final CompressorEditGUI editGUI;
    private final Player player;
    private final ModifiableCompressor compressor;

    @Override
    public String getPromptText(ConversationContext context) {
        return "§aEnter new lore for line " + lineNumber + ", with \"&\" for color codes, or type \"exit\" to exit:";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        List<String> lore = compressor.getLore();
        lore.set(lineNumber - 1, ChatColor.translateAlternateColorCodes('&', input));
        compressor.setLore(lore);
        editGUI.refreshGUI();
        editGUI.open(player);
        return null;
    }

    public LoreEditPrompt(int line, Player player, ModifiableCompressor compressor, CompressorEditGUI editGUI) {
        this.editGUI = editGUI;
        this.player = player;
        this.compressor = compressor;
        lineNumber = line;
    }
}
