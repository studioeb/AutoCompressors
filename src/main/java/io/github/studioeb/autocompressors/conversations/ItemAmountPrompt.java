/*
Prompt to set the amount of an item.
Copyright (C) 2025 Ethan Bayer

This file is part of AutoCompressors.

AutoCompressors is free software: you can redistribute it and/or
modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

AutoCompressors is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package io.github.studioeb.autocompressors.conversations;

import io.github.studioeb.autocompressors.guis.CompressionEditGUI;
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
