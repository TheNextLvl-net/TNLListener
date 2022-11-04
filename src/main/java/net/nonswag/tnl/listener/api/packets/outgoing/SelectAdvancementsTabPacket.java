package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;
import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SelectAdvancementsTabPacket extends PacketBuilder {

    @Nullable
    private NamespacedKey tab;

    public static SelectAdvancementsTabPacket create(@Nullable NamespacedKey tab) {
        return Mapping.get().packetManager().outgoing().selectAdvancementsTabPacket(tab);
    }
}
