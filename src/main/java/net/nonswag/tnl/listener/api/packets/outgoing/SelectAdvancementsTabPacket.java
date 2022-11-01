package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class SelectAdvancementsTabPacket extends PacketBuilder {

    @Nullable
    private NamespacedKey tab;

    protected SelectAdvancementsTabPacket(@Nullable NamespacedKey tab) {
        this.tab = tab;
    }

    @Nonnull
    public static SelectAdvancementsTabPacket create(@Nullable NamespacedKey tab) {
        return Mapping.get().packetManager().outgoing().selectAdvancementsTabPacket(tab);
    }
}
