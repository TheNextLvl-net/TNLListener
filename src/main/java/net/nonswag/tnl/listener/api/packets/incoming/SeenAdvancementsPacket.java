package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class SeenAdvancementsPacket implements IncomingPacket {
    @Nonnull
    private Action action;
    @Nullable
    private NamespacedKey tab;

    public SeenAdvancementsPacket(@Nonnull Action action, @Nullable NamespacedKey tab) {
        this.action = action;
        this.tab = tab;
    }

    public enum Action {
        OPENED_TAB, CLOSED_SCREEN
    }
}
