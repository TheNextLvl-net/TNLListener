package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class SeenAdvancementsPacket extends PacketBuilder {
    @Nonnull
    private Action action;
    @Nullable
    private NamespacedKey tab;

    protected SeenAdvancementsPacket(@Nonnull Action action, @Nullable NamespacedKey tab) {
        this.action = action;
        this.tab = tab;
    }

    public enum Action {
        OPENED_TAB, CLOSED_SCREEN
    }

    @Nonnull
    public static SeenAdvancementsPacket create(@Nonnull Action action, @Nullable NamespacedKey tab) {
        return Mapping.get().packetManager().incoming().seenAdvancementsPacket(action, tab);
    }
}
