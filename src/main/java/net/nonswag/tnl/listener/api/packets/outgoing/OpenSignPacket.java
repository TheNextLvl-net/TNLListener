package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OpenSignPacket extends PacketBuilder {

    private BlockLocation location;

    public static OpenSignPacket create(BlockLocation location) {
        return Mapping.get().packetManager().outgoing().openSignPacket(location);
    }

    public static OpenSignPacket create(Location location) {
        return create(new BlockLocation(location));
    }
}
