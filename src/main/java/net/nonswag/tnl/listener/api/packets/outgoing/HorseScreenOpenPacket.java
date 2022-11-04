package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class HorseScreenOpenPacket extends PacketBuilder {

    private int containerId, size, entityId;

    public static HorseScreenOpenPacket create(int containerId, int size, int entityId) {
        return Mapping.get().packetManager().outgoing().horseScreenOpenPacket(containerId, size, entityId);
    }
}
