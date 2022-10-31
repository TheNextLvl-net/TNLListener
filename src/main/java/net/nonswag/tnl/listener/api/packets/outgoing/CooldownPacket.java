package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Material;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class CooldownPacket extends PacketBuilder {

    @Nonnull
    private Material item;
    private int cooldown;

    protected CooldownPacket(@Nonnull Material item, int cooldown) {
        this.item = item;
        this.cooldown = cooldown;
    }

    @Nonnull
    public static CooldownPacket create(@Nonnull Material item, int cooldown) {
        return Mapping.get().packetManager().outgoing().cooldownPacket(item, cooldown);
    }
}
