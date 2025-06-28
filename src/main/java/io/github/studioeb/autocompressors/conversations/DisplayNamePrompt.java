package io.github.studioeb.autocompressors.conversations;

import io.github.studioeb.autocompressors.guis.CompressorEditGUI;
import io.github.studioeb.autocompressors.items.CompressorItemManager;
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
        return "§aEnter new display name, with \"" + CompressorItemManager.getAlternateColorCode() + "\" for color codes, or type \"exit\" to quit:";
    }

    @Override
    public Prompt acceptInput(@Nonnull ConversationContext context, String input) {
        editGUI.setDisplayName(ChatColor.translateAlternateColorCodes(CompressorItemManager.getAlternateColorCode(), input));
        editGUI.open(player);
        return null;
    }

    public DisplayNamePrompt(Player player, CompressorEditGUI editGUI) {
        this.editGUI = editGUI;
        this.player = player;
    }
}
