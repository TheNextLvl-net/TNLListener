package net.nonswag.tnl.listener.api.chat;

import lombok.Getter;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.PlayerChatEvent;

import java.util.function.BiPredicate;

@Getter
public class Conversation {

    private final BiPredicate<TNLPlayer, String> response;
    private boolean retryOnFail = false;

    public Conversation(BiPredicate<TNLPlayer, String> response) {
        this.response = response;
    }

    public Conversation retryOnFail(boolean retryOnFail) {
        this.retryOnFail = retryOnFail;
        return this;
    }

    public static boolean test(PlayerChatEvent chatEvent, TNLPlayer player, String message) {
        Conversation conversation = player.messenger().getConversation();
        if (player.messenger().isInConversation() && conversation != null) {
            chatEvent.setCancelled(true);
            if (conversation.getResponse().test(player, message)) player.messenger().stopConversation();
            return true;
        }
        return false;
    }
}
