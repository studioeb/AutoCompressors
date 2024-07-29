package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import io.github.rainpaw.autocompressors.items.ModifiableCompressor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class LoreLinePrompt extends NumericPrompt {

    private final int numberOfLines;
    private final CompressorEditGUI editGUI;
    private final Player player;
    private final ModifiableCompressor compressor;

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        return new LoreEditPrompt((int) input, player, compressor, editGUI);
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "§aEnter the number of the lore line you want to edit, or type \"exit\" to exit:";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1 && number <= numberOfLines;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
        return "§cPlease enter a number in the range of 1-" + numberOfLines + ".";
    }

    public LoreLinePrompt(int lines, Player player, ModifiableCompressor compressor, CompressorEditGUI editGUI) {
        numberOfLines = lines;
        this.editGUI = editGUI;
        this.player = player;
        this.compressor = compressor;
    }
}
