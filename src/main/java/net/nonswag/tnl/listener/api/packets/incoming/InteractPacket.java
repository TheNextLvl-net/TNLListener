package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class InteractPacket implements IncomingPacket {
    @Nonnull
    private Action action;
    private int entityId;
    private boolean sneaking;

    public InteractPacket(int entityId, boolean sneaking, @Nonnull Action action) {
        this.entityId = entityId;
        this.sneaking = sneaking;
        this.action = action;
    }

    public enum Action {
        INTERACT, ATTACK, INTERACT_AT
    }
}
