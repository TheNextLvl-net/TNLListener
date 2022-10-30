package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

@Getter
public abstract class EntityAnimationPacket extends PacketBuilder {

    private int entityId;
    @Nonnull
    private Animation animation;

    protected EntityAnimationPacket(int entityId, @Nonnull Animation animation) {
        this.entityId = entityId;
        this.animation = animation;
    }

    @Nonnull
    public EntityAnimationPacket setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    @Nonnull
    public EntityAnimationPacket setAnimation(@Nonnull Animation animation) {
        this.animation = animation;
        return this;
    }

    @Nonnull
    public static EntityAnimationPacket create(int entityId, @Nonnull Animation animation) {
        return Mapping.get().packets().entityAnimationPacket(entityId, animation);
    }

    @Nonnull
    public static EntityAnimationPacket create(@Nonnull LivingEntity entity, @Nonnull Animation animation) {
        return create(entity.getEntityId(), animation);
    }

    public enum Animation {
        SWING_HAND(0),
        SWING_OFFHAND(3),
        NORMAL_DAMAGE(1),
        CRITICAL_DAMAGE(4),
        MAGICAL_DAMAGE(5);

        private final int id;

        Animation(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
