package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNullableByDefault;

@Getter
@Setter
@FieldsAreNullableByDefault
@ParametersAreNullableByDefault
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetBeaconPacket extends PacketBuilder {
    private Effect primary, secondary;

    public record Effect(@Nonnull Category category, int color, int id) {
        public enum Category {
            BENEFICIAL, HARMFUL, NEUTRAL
        }
    }

    public static SetBeaconPacket create(Effect primary, Effect secondary) {
        return Mapping.get().packetManager().incoming().setBeaconPacket(primary, secondary);
    }
}
