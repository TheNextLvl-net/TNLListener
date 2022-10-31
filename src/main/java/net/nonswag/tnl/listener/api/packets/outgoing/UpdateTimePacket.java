package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class UpdateTimePacket extends PacketBuilder {

    private long age;
    private long timestamp;
    private boolean cycle;

    protected UpdateTimePacket(long age, long timestamp, boolean cycle) {
        this.age = age;
        this.timestamp = timestamp;
        this.cycle = cycle;
    }

    @Nonnull
    public static UpdateTimePacket create(long age, long timestamp, boolean cycle) {
        return Mapping.get().packetManager().outgoing().updateTimePacket(age, timestamp, cycle);
    }

    @Nonnull
    public static UpdateTimePacket create(long timestamp, boolean cycle) {
        return create(0, timestamp, cycle);
    }

    @Nonnull
    public static UpdateTimePacket create(long timestamp) {
        return create(timestamp, timestamp, true);
    }
}
