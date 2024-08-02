package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class DisplayNamePrompt extends StringPrompt {

    private final Player player;

    private final CompressorEditGUI editGUI;

    @Override @Nonnull
    public String getPromptText(@Nonnull ConversationContext context) {
        return "§aEnter new display name, with \"&\" for color codes, or type \"exit\" to quit:";
    }

    @Override
    public Prompt acceptInput(@Nonnull ConversationContext context, String input) {
        editGUI.setDisplayName(ChatColor.translateAlternateColorCodes('&', input));
        editGUI.open(player);
        return null;
    }

    public DisplayNamePrompt(Player player, CompressorEditGUI editGUI) {
        this.editGUI = editGUI;
        this.player = player;
    }
}
