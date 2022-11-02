package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetSimulationDistancePacket extends PacketBuilder {

    private int simulationDistance;

    protected SetSimulationDistancePacket(int simulationDistance) {
        this.simulationDistance = simulationDistance;
    }

    @Nonnull
    public static SetSimulationDistancePacket create(int simulationDistance) {
        return Mapping.get().packetManager().outgoing().setSimulationDistancePacket(simulationDistance);
    }
}
