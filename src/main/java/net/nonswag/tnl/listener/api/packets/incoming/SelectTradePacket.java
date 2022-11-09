package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SelectTradePacket extends PacketBuilder {
    private int trade;

    public static SelectTradePacket create(int trade) {
        return Mapping.get().packetManager().incoming().selectTradePacket(trade);
    }
}
