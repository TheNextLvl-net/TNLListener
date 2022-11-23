package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Sound;

@Getter
@Setter
public class ChatMentionEvent extends PlayerEvent {
    private final TNLPlayer mentioned;
    private Sound sound;

    public ChatMentionEvent(TNLPlayer player, TNLPlayer mentioned) {
        this(player, mentioned, Sound.BLOCK_NOTE_BLOCK_PLING);
    }

    public ChatMentionEvent(TNLPlayer player, TNLPlayer mentioned, Sound sound) {
        super(player);
        this.mentioned = mentioned;
        this.sound = sound;
    }
}
