package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class WorldBorderPacket extends PacketBuilder {

    @Nonnull
    private VirtualBorder border;
    @Nonnull
    private Action action;

    protected WorldBorderPacket(@Nonnull VirtualBorder virtualBorder, @Nonnull Action action) {
        this.border = virtualBorder;
        this.action = action;
    }

    @Nonnull
    public static WorldBorderPacket create(@Nonnull VirtualBorder virtualBorder, @Nonnull Action action) {
        return Mapping.get().packetManager().outgoing().worldBorderPacket(virtualBorder, action);
    }

    @Getter
    public enum Action {
        SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS
    }
}
