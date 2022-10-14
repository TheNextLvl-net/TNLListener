package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Sound;

import javax.annotation.Nonnull;

public class ChatMentionEvent extends PlayerEvent {

    @Nonnull
    private final TNLPlayer mentioned;
    @Nonnull
    private Sound sound;

    public ChatMentionEvent(@Nonnull TNLPlayer player, @Nonnull TNLPlayer mentioned) {
        this(player, mentioned, Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public ChatMentionEvent(@Nonnull TNLPlayer player, @Nonnull TNLPlayer mentioned, @Nonnull Sound sound) {
        super(player);
        this.mentioned = mentioned;
        this.sound = sound;
    }

    @Nonnull
    public TNLPlayer getMentioned() {
        return mentioned;
    }

    @Nonnull
    public Sound getSound() {
        return sound;
    }

    public void setSound(@Nonnull Sound sound) {
        this.sound = sound;
    }
}
