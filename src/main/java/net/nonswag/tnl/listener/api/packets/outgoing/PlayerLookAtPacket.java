package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerLookAtPacket extends PacketBuilder {
    private Anchor self;
    private Position position;
    private int entityId;
    @Nullable
    private Anchor target;

    public enum Anchor {
        FEET, EYES
    }

    public static PlayerLookAtPacket create(Anchor self, Position position, int entityId, @Nullable Anchor target) {
        return Mapping.get().packetManager().outgoing().playerLookAtPacket(self, position, entityId, target);
    }

    public static PlayerLookAtPacket create(Anchor self, Position position) {
        return create(self, position, 0, null);
    }
}
