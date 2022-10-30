package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public class SetBeaconPacket implements IncomingPacket {
    @Nullable
    private Effect primary;
    @Nullable
    private Effect secondary;

    public SetBeaconPacket(@Nullable Effect primary, @Nullable Effect secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public record Effect(@Nonnull Category category, int color, int id) {

        public enum Category {
            BENEFICIAL, HARMFUL, NEUTRAL
        }
    }
}
