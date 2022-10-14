package net.nonswag.tnl.listener.events;

import lombok.Getter;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.packets.PacketBuilder;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerPacketEvent extends PlayerEvent {

    @Nonnull
    private final Object packet;
    @Nonnull
    private final ChannelDirection direction;

    public PlayerPacketEvent(@Nonnull TNLPlayer player, @Nonnull Object packet, @Nonnull ChannelDirection direction) {
        super(player);
        this.packet = packet;
        this.direction = direction;
    }

    @Nonnull
    public String getPacketName() {
        return packet.getClass().getSimpleName();
    }

    public void reply(@Nonnull PacketBuilder... packets) {
        for (PacketBuilder builder : packets) builder.send(getPlayer());
    }

    public void setPacketField(@Nonnull String packetField, @Nullable Object value) {
        Reflection.Field.set(getPacket(), packetField, value);
    }

    public void failedToWrite() {
        setCancelled(true);
        if (!getPacketFields().isEmpty()) {
            List<String> strings = new ArrayList<>();
            for (String field : getPacketFields()) {
                strings.add("§8(§7field§8: §6" + field + " §8-> '§6" + getPacketField(field, "-/-") + "§8')");
            }
            getPlayer().pipeline().disconnect("§cFailed to write Packet\n§4" + getPacketName() + "\n\n" + String.join("\n", strings).replace("null", "-/-"));
        } else
            getPlayer().pipeline().disconnect("§cFailed to write Packet\n§4%packet%", new Placeholder("packet", getPacketName()));
    }

    @Nonnull
    public <V> V getPacketField(@Nonnull String field, @Nonnull V defaultValue) {
        return getPacketField(field, packet.getClass(), defaultValue);
    }

    @Nonnull
    public <V> V getPacketField(@Nonnull String field, @Nonnull Class<?> superclass, @Nonnull V defaultValue) {
        V v = Reflection.Field.get(packet, superclass, field);
        return v != null ? v : defaultValue;
    }

    @Nullable
    public <V> V getPacketField(@Nonnull String field) {
        return getPacketField(field, packet.getClass());
    }

    @Nullable
    public <V> V getPacketField(@Nonnull String field, @Nonnull Class<?> superclass) {
        return Reflection.Field.get(packet, superclass, field);
    }

    @Nonnull
    public List<String> getPacketFields() {
        return Reflection.Field.list(packet.getClass());
    }

    public enum ChannelDirection {
        IN, OUT;

        public boolean isIncoming() {
            return equals(IN);
        }

        public boolean isOutgoing() {
            return equals(OUT);
        }
    }
}
