package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ClearTitlesPacket extends PacketBuilder {

    private boolean resetTimes;

    public static ClearTitlesPacket create(boolean resetTimes) {
        return Mapping.get().packetManager().outgoing().clearTitlesPacket(resetTimes);
    }
}
