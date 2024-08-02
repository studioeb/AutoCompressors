package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressionEditGUI;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

public class ItemAmountPrompt extends NumericPrompt {

    private final String itemType;
    private final CompressionEditGUI editGUI;
    private final Player player;

    @Override
    protected Prompt acceptValidatedInput(ConversationContext context, Number input) {
        if (itemType.equals("start")) {
            editGUI.setStartItemAmount((int) input);
        } else {
            editGUI.setFinalItemAmount((int) input);
        }
        editGUI.refreshGUI();
        editGUI.open(player);
        return null;
    }

    @Override
    public String getPromptText(ConversationContext context) {
        return "§aEnter the amount of the " + itemType + " item, or type \"exit\" to exit:";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
        return "§cPlease enter a number greater than 0.";
    }

    public ItemAmountPrompt(String itemType, Player player, CompressionEditGUI editGUI) {
        this.itemType = itemType;
        this.editGUI = editGUI;
        this.player = player;
    }
}
