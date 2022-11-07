package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class OpenSignEditorPacket extends PacketBuilder {
    private BlockPosition position;

    public static OpenSignEditorPacket create(BlockPosition position) {
        return Mapping.get().packetManager().outgoing().openSignEditorPacket(position);
    }

    public static OpenSignEditorPacket create(Location location) {
        return create(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }
}
