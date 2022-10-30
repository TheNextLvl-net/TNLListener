package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class PlayerCommandPacket implements IncomingPacket {
    private int entityId;
    @Nonnull
    private Action action;
    private int data;

    public PlayerCommandPacket(int entityId, @Nonnull Action action, int data) {
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
}
