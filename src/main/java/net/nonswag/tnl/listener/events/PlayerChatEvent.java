package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.Cancellable;

import javax.annotation.Nullable;

@Getter
@Setter
public class PlayerChatEvent extends PlayerEvent implements Cancellable {
    private String message;
    @Nullable
    private String format = null;
    private boolean cancelled;

    public PlayerChatEvent(TNLPlayer player, String message) {
        super(player);
        this.message = message;
    }

    public boolean isCommand() {
        return getMessage().startsWith("/");
    }
}
