/*
Prompts the player to enter a new display name for the compressor.
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
