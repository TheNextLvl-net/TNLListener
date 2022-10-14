package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
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
    public WorldBorderPacket setWorldBorder(@Nonnull VirtualBorder border) {
        this.border = border;
        return this;
    }

    @Nonnull
    public WorldBorderPacket setAction(@Nonnull Action action) {
        this.action = action;
        return this;
    }

    @Nonnull
    public static WorldBorderPacket create(@Nonnull VirtualBorder virtualBorder, @Nonnull Action action) {
        return Mapping.get().packets().worldBorderPacket(virtualBorder, action);
    }

    @Getter
    public enum Action {
        SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS
    }
}
