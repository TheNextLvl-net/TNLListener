package net.nonswag.tnl.listener.api.packets.incoming;

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
public abstract class SeenAdvancementsPacket extends PacketBuilder {
    private Action action;
    @Nullable
    private NamespacedKey tab;

    public enum Action {
        OPENED_TAB, CLOSED_SCREEN
    }

    public static SeenAdvancementsPacket create(Action action, @Nullable NamespacedKey tab) {
        return Mapping.get().packetManager().incoming().seenAdvancementsPacket(action, tab);
    }
}
