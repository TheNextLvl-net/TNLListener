package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.LivingEntity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AnimatePacket extends PacketBuilder {
    private int entityId;
    private Animation animation;

    public static AnimatePacket create(int entityId, Animation animation) {
        return Mapping.get().packetManager().outgoing().animatePacket(entityId, animation);
    }

    public static AnimatePacket create(LivingEntity entity, Animation animation) {
        return create(entity.getEntityId(), animation);
    }

    @Getter
    @AllArgsConstructor
    public enum Animation {
        SWING_MAIN_HAND(0),
        HURT(1),
        WAKE_UP(2),
        SWING_OFF_HAND(3),
        CRITICAL_HIT(4),
        MAGIC_CRITICAL_HIT(5);

        private final int id;
    }
}
