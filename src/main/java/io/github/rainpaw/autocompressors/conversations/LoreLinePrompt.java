package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressorEditGUI;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class LoreLinePrompt extends NumericPrompt {

    private final int numberOfLines;
    private final CompressorEditGUI editGUI;
    private final Player player;

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        return new LoreEditPrompt((int) input, player, editGUI);
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "§aEnter the number of the lore line you want to edit, type \"0\" to create a new line at the end of the lore, type a negative sign in front of an index to delete the lore line at that index, or type \"exit\" to exit:";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= (-numberOfLines) && number <= numberOfLines;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
        switch (Math.abs(numberOfLines)) {
            case 0:
                return "§cPlease create a new line by typing \"0\" before trying to edit or delete lines.";
            case 1:
                return "§cPlease edit line 1, type \"0\" to create a new line, or type a valid negative number to delete a line.";
            default:
                return "§cPlease enter a number in the range of 1-" + numberOfLines + ", \"0\" to create a line, or a valid negative number to delete a line.";
        }
    }

    public LoreLinePrompt(int lines, Player player, CompressorEditGUI editGUI) {
        numberOfLines = lines;
        this.editGUI = editGUI;
        this.player = player;
    }
}
