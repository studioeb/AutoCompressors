/*
Prompt to select a line of lore to edit.
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

import io.github.studioeb.autocompressors.guis.CompressorEditGUI;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class LoreLinePrompt extends NumericPrompt {

    private final int numberOfLines;
    private final CompressorEditGUI editGUI;
    private final Player player;

    @Override
    protected Prompt acceptValidatedInput(@Nonnull ConversationContext context, @Nonnull Number input) {
        return new LoreEditPrompt((int) input, player, editGUI);
    }

    @Override @Nonnull
    public String getPromptText(@Nonnull ConversationContext context) {
        return "§aEnter the number of the lore line you want to edit, type \"0\" to create a new line at the end of the lore, type a negative sign in front of an index to delete the lore line at that index, or type \"exit\" to exit:";
    }

    @Override
    protected boolean isInputValid(@Nonnull ConversationContext context, @Nonnull String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= (-numberOfLines) && number <= numberOfLines;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getFailedValidationText(@Nonnull ConversationContext context, @Nonnull Number invalidInput) {
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
