package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CustomPayloadPacket extends PacketBuilder {

    @Nonnull
    private String channel;
    @Nonnull
    private byte[][] bytes;

    protected CustomPayloadPacket(@Nonnull String channel, @Nonnull byte[]... bytes) {
        this.channel = channel;
        this.bytes = bytes;
    }

    @Nonnull
    public static CustomPayloadPacket create(@Nonnull String channel, @Nonnull byte[]... bytes) {
        return Mapping.get().packetManager().outgoing().customPayloadPacket(channel, bytes);
    }
}
