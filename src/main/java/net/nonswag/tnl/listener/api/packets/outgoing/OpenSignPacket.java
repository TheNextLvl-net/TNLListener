package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class OpenSignPacket extends PacketBuilder {

    @Nonnull
    private BlockLocation location;

    protected OpenSignPacket(@Nonnull BlockLocation location) {
        this.location = location;
    }

    @Nonnull
    public static OpenSignPacket create(@Nonnull BlockLocation location) {
        return Mapping.get().packetManager().outgoing().openSignPacket(location);
    }

    @Nonnull
    public static OpenSignPacket create(@Nonnull Location location) {
        return create(new BlockLocation(location));
    }
}