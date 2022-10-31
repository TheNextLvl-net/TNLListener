package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlayerCommandPacket extends PacketBuilder {
    private int entityId;
    @Nonnull
    private Action action;
    private int data;

    protected PlayerCommandPacket(int entityId, @Nonnull Action action, int data) {
        this.entityId = entityId;
        this.action = action;
        this.data = data;
    }

    public enum Action {
        PRESS_SHIFT_KEY,
        RELEASE_SHIFT_KEY,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        START_RIDING_JUMP,
        STOP_RIDING_JUMP,
        OPEN_INVENTORY,
        START_FALL_FLYING
    }

    @Nonnull
    public static PlayerCommandPacket create(int entityId, @Nonnull Action action, int data) {
        return Mapping.get().packetManager().incoming().playerCommandPacket(entityId, action, data);
    }
}
