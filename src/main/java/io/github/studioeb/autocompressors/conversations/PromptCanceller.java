/*
Canceller that goes back to the GUI when an prompt entry area is cancelled.
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

import io.github.studioeb.autocompressors.guis.BaseGUI;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class PromptCanceller implements ConversationCanceller {

    private final Player player;
    private final BaseGUI gui;

    @Override
    public void setConversation(@Nonnull Conversation conversation) {
        
    }

    @Override
    public boolean cancelBasedOnInput(@Nonnull ConversationContext context, String input) {
        if (input.equalsIgnoreCase("exit")) {
            gui.open(player);
            return true;
        } else {
            return false;
        }
    }

    @Override @Nonnull
    public ConversationCanceller clone() {
        ConversationCanceller conversationCanceller;
        try {
            conversationCanceller = (ConversationCanceller) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Will never be reached due to ConversationCanceller implementing cloneable
        }
        return conversationCanceller;
    }

    public PromptCanceller(BaseGUI gui, Player player) {
        this.gui = gui;
        this.player = player;
    }
}
