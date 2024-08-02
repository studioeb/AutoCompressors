package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LoreEditPrompt extends StringPrompt {

    private final int lineNumber;
    private final CompressorEditGUI editGUI;
    private final Player player;

    @Override @Nonnull
    public String getPromptText(@Nonnull ConversationContext context) {
        if (lineNumber == 0) {
            return "§aEnter lore for the new line, with \"&\" for color codes, or type \"exit\" to exit:";
        } else if (lineNumber < 0) {
            return "§cAre you sure you want to delete line " + Math.abs(lineNumber) + "? (y/N)";
        } else {
            return "§aEnter new lore for line " + lineNumber + ", with \"&\" for color codes, or type \"exit\" to exit:";
        }
    }

    @Override
    public Prompt acceptInput(@Nonnull ConversationContext context, String input) {
        if (lineNumber < 0 && input.equalsIgnoreCase("y")) {
            editGUI.deleteLoreAtLine(Math.abs(lineNumber) - 1);
        } else if (lineNumber < 0) {
            return new LoreEditPrompt(lineNumber, player, editGUI);
        } else if (lineNumber == 0) {
            editGUI.addToLore(ChatColor.translateAlternateColorCodes('&', input));
        } else {
            editGUI.setLoreAtLine(lineNumber - 1, ChatColor.translateAlternateColorCodes('&', input));
        }
        editGUI.open(player);
        return null;
    }

    public LoreEditPrompt(int line, Player player, CompressorEditGUI editGUI) {
        this.editGUI = editGUI;
        this.player = player;
        lineNumber = line;
    }
}
