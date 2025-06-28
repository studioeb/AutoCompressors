/*
Prompt to edit the content of a selected lore line.
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
import io.github.studioeb.autocompressors.items.CompressorItemManager;
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
            return "§aEnter lore for the new line, with \"" + CompressorItemManager.getAlternateColorCode() + "\" for color codes, or type \"exit\" to exit:";
        } else if (lineNumber < 0) {
            return "§cAre you sure you want to delete line " + Math.abs(lineNumber) + "? (y/N)";
        } else {
            return "§aEnter new lore for line " + lineNumber + ", with \"" + CompressorItemManager.getAlternateColorCode() + "\" for color codes, or type \"exit\" to exit:";
        }
    }

    @Override
    public Prompt acceptInput(@Nonnull ConversationContext context, String input) {
        if (lineNumber < 0 && input.equalsIgnoreCase("y")) {
            editGUI.deleteLoreAtLine(Math.abs(lineNumber) - 1);
        } else if (lineNumber < 0) {
            return new LoreEditPrompt(lineNumber, player, editGUI);
        } else if (lineNumber == 0) {
            editGUI.addToLore(ChatColor.translateAlternateColorCodes(CompressorItemManager.getAlternateColorCode(), input));
        } else {
            editGUI.setLoreAtLine(lineNumber - 1, ChatColor.translateAlternateColorCodes(CompressorItemManager.getAlternateColorCode(), input));
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
