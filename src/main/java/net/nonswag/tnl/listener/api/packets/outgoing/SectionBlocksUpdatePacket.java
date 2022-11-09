package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor
public abstract class SectionBlocksUpdatePacket extends PacketBuilder {
    private long section;
    private short[] positions;
    private int[] states;
    private boolean suppressLightUpdates;

    public static SectionBlocksUpdatePacket create(long section, short[] positions, int[] states, boolean suppressLightUpdates) {
        return Mapping.get().packetManager().outgoing().sectionBlocksUpdatePacket(section, positions, states, suppressLightUpdates);
    }
}
