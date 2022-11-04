package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
public abstract class SignUpdatePacket extends PacketBuilder {
    private BlockPosition position;
    private final String[] lines = new String[4];

    protected SignUpdatePacket(BlockPosition position, String[] lines) {
        this.position = position;
        setLines(lines);
    }

    public void setLines(String[] lines) {
        for (int i = 0; i < 4; i++) this.lines[i] = lines.length >= i ? lines[i] : "";
    }

    public static SignUpdatePacket create(BlockPosition position, String[] lines) {
        return Mapping.get().packetManager().incoming().signUpdatePacket(position, lines);
    }
}
