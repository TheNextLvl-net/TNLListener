package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

@Getter
@Setter
public class EntityDamageByPlayerEvent extends PlayerEvent implements Cancellable {
    private final Entity entity;
    private boolean cancelled;

    public EntityDamageByPlayerEvent(TNLPlayer player, Entity entity) {
        super(player);
        this.entity = entity;
    }
}
