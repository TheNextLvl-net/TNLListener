package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AddExperienceOrbPacket extends PacketBuilder {
    private int entityId;
    private Position position;
    private int value;

    public static AddExperienceOrbPacket create(int entityId, Position position, int value) {
        return Mapping.get().packetManager().outgoing().addExperienceOrbPacket(entityId, position, value);
    }
}
