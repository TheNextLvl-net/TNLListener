package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerAbilitiesPacket extends PacketBuilder {
    private boolean flying;

    public static PlayerAbilitiesPacket create(boolean flying) {
        return Mapping.get().packetManager().incoming().playerAbilitiesPacket(flying);
    }
}
