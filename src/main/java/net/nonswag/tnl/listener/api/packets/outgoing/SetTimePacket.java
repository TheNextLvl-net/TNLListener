package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetTimePacket extends PacketBuilder {
    private long age, timestamp;
    private boolean cycle;

    public static SetTimePacket create(long age, long timestamp, boolean cycle) {
        return Mapping.get().packetManager().outgoing().setTimePacket(age, timestamp, cycle);
    }

    public static SetTimePacket create(long timestamp, boolean cycle) {
        return create(0, timestamp, cycle);
    }

    public static SetTimePacket create(long timestamp) {
        return create(timestamp, timestamp, true);
    }
}
