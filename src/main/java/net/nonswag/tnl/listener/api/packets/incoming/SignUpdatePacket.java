package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;

import javax.annotation.Nonnull;

@Getter
@Setter
public class SignUpdatePacket implements IncomingPacket {
    @Nonnull
    private BlockPosition position;
    @Nonnull
    private final String[] lines = new String[4];

    public SignUpdatePacket(@Nonnull BlockPosition position, @Nonnull String[] lines) {
        this.position = position;
        setLines(lines);
    }

    public void setLines(@Nonnull String[] lines) {
        for (int i = 0; i < 4; i++) this.lines[i] = lines.length >= i ? lines[i] : "";
    }
}
