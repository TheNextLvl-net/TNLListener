package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public class PlayerChatEvent extends PlayerEvent implements Cancellable {
    private String message;
    @Nullable
    private String format = null;
    private boolean cancelled;
    private List<TNLPlayer> receivers;

    public PlayerChatEvent(TNLPlayer player, String message, List<TNLPlayer> receivers) {
        super(player);
        this.message = message;
        this.receivers = receivers;
    }

    public PlayerChatEvent(TNLPlayer player, String message) {
        this(player, message, Listener.getOnlinePlayers());
    }
}
