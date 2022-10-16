package net.nonswag.tnl.listener.api.chat;

import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.PlayerChatEvent;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public class Conversation {

    @Nonnull
    private final BiPredicate<TNLPlayer, String> response;
    private boolean retryOnFail = false;

    public Conversation(@Nonnull BiPredicate<TNLPlayer, String> response) {
        this.response = response;
    }

    @Nonnull
    public BiPredicate<TNLPlayer, String> getResponse() {
        return response;
    }

    @Nonnull
    public Conversation setRetryOnFail(boolean retryOnFail) {
        this.retryOnFail = retryOnFail;
        return this;
    }

    public boolean isRetryOnFail() {
        return retryOnFail;
    }

    public static boolean test(@Nonnull PlayerChatEvent chatEvent, @Nonnull TNLPlayer player, @Nonnull String message) {
        Conversation conversation = player.messenger().getConversation();
        if (player.messenger().isInConversation() && conversation != null) {
            chatEvent.setCancelled(true);
            if (conversation.getResponse().test(player, message)) player.messenger().stopConversation();
            return true;
        }
        return false;
    }
}
