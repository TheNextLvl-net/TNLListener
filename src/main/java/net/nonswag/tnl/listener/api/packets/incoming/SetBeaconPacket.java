package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class SetBeaconPacket extends PacketBuilder {
    @Nullable
    private Effect primary;
    @Nullable
    private Effect secondary;

    protected SetBeaconPacket(@Nullable Effect primary, @Nullable Effect secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public record Effect(@Nonnull Category category, int color, int id) {

        public enum Category {
            BENEFICIAL, HARMFUL, NEUTRAL
        }
    }

    @Nonnull
    public static SetBeaconPacket create(@Nullable Effect primary, @Nullable Effect secondary) {
        return Mapping.get().packetManager().incoming().setBeaconPacket(primary, secondary);
    }
}
