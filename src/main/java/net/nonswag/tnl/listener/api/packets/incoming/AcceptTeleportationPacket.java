package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class AcceptTeleportationPacket extends PacketBuilder {

    private int id;

    protected AcceptTeleportationPacket(int id) {
        this.id = id;
    }

    @Nonnull
    public static AcceptTeleportationPacket create(int id) {
        return Mapping.get().packetManager().incoming().acceptTeleportationPacket(id);
    }
}
