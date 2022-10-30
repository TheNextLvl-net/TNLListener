package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
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
    public CustomPayloadPacket setChannel(@Nonnull String channel) {
        this.channel = channel;
        return this;
    }

    @Nonnull
    public CustomPayloadPacket setBytes(@Nonnull byte[]... bytes) {
        this.bytes = bytes;
        return this;
    }

    @Nonnull
    public static CustomPayloadPacket create(@Nonnull String channel, @Nonnull byte[]... bytes) {
        return Mapping.get().packets().customPayloadPacket(channel, bytes);
    }
}
