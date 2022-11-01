package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class AnimationPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Animation animation;

    protected AnimationPacket(int entityId, @Nonnull Animation animation) {
        this.entityId = entityId;
        this.animation = animation;
    }

    @Nonnull
    public static AnimationPacket create(int entityId, @Nonnull Animation animation) {
        return Mapping.get().packetManager().outgoing().animationPacket(entityId, animation);
    }

    @Nonnull
    public static AnimationPacket create(@Nonnull LivingEntity entity, @Nonnull Animation animation) {
        return create(entity.getEntityId(), animation);
    }

    @Getter
    public enum Animation {
        SWING_MAIN_HAND(0),
        HURT(1),
        WAKE_UP(2),
        SWING_OFF_HAND(3),
        CRITICAL_HIT(4),
        MAGIC_CRITICAL_HIT(5);

        private final int id;

        Animation(int id) {
            this.id = id;
        }
    }
}
