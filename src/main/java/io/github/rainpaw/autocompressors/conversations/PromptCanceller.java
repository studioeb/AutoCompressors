package io.github.rainpaw.autocompressors.conversations;

import io.github.rainpaw.autocompressors.guis.BaseGUI;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationCanceller;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;

public class PromptCanceller implements ConversationCanceller {

    private final Player player;
    private final BaseGUI gui;

    @Override
    public void setConversation(Conversation conversation) {
        
    }

    @Override
    public boolean cancelBasedOnInput(ConversationContext context, String input) {
        if (input.equalsIgnoreCase("exit")) {
            gui.open(player);
            return true;
        } else {
            return false;
        }
    }

    @Override
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
