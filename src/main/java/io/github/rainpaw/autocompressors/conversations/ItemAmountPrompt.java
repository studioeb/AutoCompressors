package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.CompressionEditGUI;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ItemAmountPrompt extends NumericPrompt {

    private final String itemType;
    private final CompressionEditGUI editGUI;
    private final Player player;

    @Override
    protected Prompt acceptValidatedInput(@Nonnull ConversationContext context, @Nonnull Number input) {
        if (itemType.equals("start")) {
            editGUI.setStartItemAmount((int) input);
        } else {
            editGUI.setFinalItemAmount((int) input);
        }
        editGUI.refreshGUI();
        editGUI.open(player);
        return null;
    }

    @Override @Nonnull
    public String getPromptText(@Nonnull ConversationContext context) {
        return "§aEnter the amount of the " + itemType + " item, or type \"exit\" to exit:";
    }

    @Override
    protected boolean isInputValid(@Nonnull ConversationContext context, @Nonnull String input) {
        try {
            int number = Integer.parseInt(input);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getFailedValidationText(@Nonnull ConversationContext context, @Nonnull Number invalidInput) {
        return "§cPlease enter a number greater than 0.";
    }

    public ItemAmountPrompt(String itemType, Player player, CompressionEditGUI editGUI) {
        this.itemType = itemType;
        this.editGUI = editGUI;
        this.player = player;
    }
}
