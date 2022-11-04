package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Material;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CooldownPacket extends PacketBuilder {

    private Material item;
    private int cooldown;

    public static CooldownPacket create(Material item, int cooldown) {
        return Mapping.get().packetManager().outgoing().cooldownPacket(item, cooldown);
    }
}
