package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Material;

import javax.annotation.Nonnull;

@Getter
public abstract class CooldownPacket extends PacketBuilder {

    @Nonnull
    private Material item;
    private int cooldown;

    protected CooldownPacket(@Nonnull Material item, int cooldown) {
        this.item = item;
        this.cooldown = cooldown;
    }

    @Nonnull
    public CooldownPacket setItem(@Nonnull Material item) {
        this.item = item;
        return this;
    }

    @Nonnull
    public CooldownPacket setCooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    @Nonnull
    public static CooldownPacket create(@Nonnull Material item, int cooldown) {
        return Mapping.get().packets().cooldownPacket(item, cooldown);
    }
}
