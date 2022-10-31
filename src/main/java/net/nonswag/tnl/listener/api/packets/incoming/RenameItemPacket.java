package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class RenameItemPacket extends PacketBuilder {
    @Nonnull
    private String name;

    protected RenameItemPacket(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public static RenameItemPacket create(@Nonnull String name) {
        return Mapping.get().packetManager().incoming().renameItemPacket(name);
    }
}
