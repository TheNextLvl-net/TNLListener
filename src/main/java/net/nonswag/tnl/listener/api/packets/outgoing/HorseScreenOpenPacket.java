package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class HorseScreenOpenPacket extends PacketBuilder {

    private int containerId, size, entityId;

    protected HorseScreenOpenPacket(int containerId, int size, int entityId) {
        this.containerId = containerId;
        this.size = size;
        this.entityId = entityId;
    }

    @Nonnull
    public static HorseScreenOpenPacket create(int containerId, int size, int entityId) {
        return Mapping.get().packetManager().outgoing().horseScreenOpenPacket(containerId, size, entityId);
    }
}
