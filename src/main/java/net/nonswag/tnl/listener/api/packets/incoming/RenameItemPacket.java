package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class RenameItemPacket implements IncomingPacket {
    @Nonnull
    private String name;

    public RenameItemPacket(@Nonnull String name) {
        this.name = name;
    }
}
