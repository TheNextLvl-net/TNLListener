package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectTradePacket implements IncomingPacket {
    private int trade;

    public SelectTradePacket(int trade) {
        this.trade = trade;
    }
}
