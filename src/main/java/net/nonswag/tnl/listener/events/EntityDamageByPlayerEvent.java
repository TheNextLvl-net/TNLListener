package net.nonswag.tnl.listener.events;

import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

public class EntityDamageByPlayerEvent extends PlayerEvent {

    @Nonnull
    private final Entity entity;

    public EntityDamageByPlayerEvent(@Nonnull TNLPlayer player, @Nonnull Entity entity) {
        super(player);
        this.entity = entity;
    }

    @Nonnull
    public Entity getEntity() {
        return entity;
    }
}
