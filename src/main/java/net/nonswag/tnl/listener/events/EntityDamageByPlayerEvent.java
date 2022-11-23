package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.entity.Entity;

@Getter
public class EntityDamageByPlayerEvent extends PlayerEvent {
    private final Entity entity;

    public EntityDamageByPlayerEvent(TNLPlayer player, Entity entity) {
        super(player);
        this.entity = entity;
    }
}
