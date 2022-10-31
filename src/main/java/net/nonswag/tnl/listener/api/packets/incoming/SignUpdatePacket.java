package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SignUpdatePacket extends PacketBuilder {
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private final String[] lines = new String[4];

    protected SignUpdatePacket(@Nonnull BlockPosition position, @Nonnull String[] lines) {
        this.position = position;
        setLines(lines);
    }

    public void setLines(@Nonnull String[] lines) {
        for (int i = 0; i < 4; i++) this.lines[i] = lines.length >= i ? lines[i] : "";
    }

    @Nonnull
    public static SignUpdatePacket create(@Nonnull BlockPosition position, @Nonnull String[] lines) {
        return Mapping.get().packetManager().incoming().signUpdatePacket(position, lines);
    }
}
