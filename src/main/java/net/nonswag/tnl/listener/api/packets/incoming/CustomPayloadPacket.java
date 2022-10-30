package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public class CustomPayloadPacket implements IncomingPacket {
    @Nonnull
    private NamespacedKey channel;
    @Nonnull
    private byte[] data;

    public CustomPayloadPacket(@Nonnull NamespacedKey channel, @Nonnull byte[] data) {
        this.channel = channel;
        this.data = data;
    }
}
