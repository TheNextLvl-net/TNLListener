package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class SelectTradePacket extends PacketBuilder {
    private int trade;

    protected SelectTradePacket(int trade) {
        this.trade = trade;
    }

    public static SelectTradePacket create(int trade) {
        return Mapping.get().packetManager().incoming().selectTradePacket(trade);
    }
}
