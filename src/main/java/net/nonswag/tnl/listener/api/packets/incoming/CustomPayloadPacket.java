package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CustomPayloadPacket extends PacketBuilder {
    @Nonnull
    private NamespacedKey channel;
    @Nonnull
    private byte[] data;

    protected CustomPayloadPacket(@Nonnull NamespacedKey channel, @Nonnull byte[] data) {
        this.channel = channel;
        this.data = data;
    }

    @Nonnull
    public static CustomPayloadPacket create(@Nonnull NamespacedKey channel, @Nonnull byte[] data) {
        return Mapping.get().packetManager().incoming().customPayloadPacket(channel, data);
    }
}
