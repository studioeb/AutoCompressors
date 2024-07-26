package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.AutoCompressors;
import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import io.github.rainpaw.autocompressors.items.Compressor;
import io.github.rainpaw.autocompressors.items.CompressorItemManager;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class DisplayNamePrompt extends StringPrompt {

    private AutoCompressors plugin;

    private final CompressorEditGUI editGUI;
    private final Player player;

    @Override
    public String getPromptText(ConversationContext context) {
        return "§aEnter new display name, with \"&\" for color codes:";
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        CompressorItemManager.getTempCompressor().setDisplayName(input);
        editGUI.refreshGUI();
        editGUI.open(player);
        return null;
    }

    public DisplayNamePrompt(CompressorEditGUI gui, Player player) {
        editGUI = gui;
        this.player = player;
    }
}
