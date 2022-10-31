package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlayerAbilitiesPacket extends PacketBuilder {
    private boolean flying;

    protected PlayerAbilitiesPacket(boolean flying) {
        this.flying = flying;
    }

    @Nonnull
    public static PlayerAbilitiesPacket create(boolean flying) {
        return Mapping.get().packetManager().incoming().playerAbilitiesPacket(flying);
    }
}
