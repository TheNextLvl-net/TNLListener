package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.ParametersAreNullableByDefault;

@Getter
@Setter
@ParametersAreNullableByDefault
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetDisplayObjectivePacket extends PacketBuilder {
    private int slot;
    private String objectiveName;

    public static SetDisplayObjectivePacket create(int slot, String objectiveName) {
        return Mapping.get().packetManager().outgoing().setDisplayObjectivePacket(slot, objectiveName);
    }
}
