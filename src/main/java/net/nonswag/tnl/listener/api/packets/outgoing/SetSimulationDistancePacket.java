package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetSimulationDistancePacket extends PacketBuilder {
    private int simulationDistance;

    public static SetSimulationDistancePacket create(int simulationDistance) {
        return Mapping.get().packetManager().outgoing().setSimulationDistancePacket(simulationDistance);
    }
}
