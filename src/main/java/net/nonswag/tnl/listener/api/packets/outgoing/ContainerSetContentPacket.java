package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerSetContentPacket extends PacketBuilder {

    private int containerId;
    private int stateId;
    private List<TNLItem> content;
    private TNLItem cursor;

    public static ContainerSetContentPacket create(int containerId, int stateId, List<TNLItem> content, TNLItem cursor) {
        return Mapping.get().packetManager().outgoing().containerSetContentPacket(containerId, stateId, content, cursor);
    }

    public static ContainerSetContentPacket create(List<TNLItem> content, TNLItem cursor) {
        return create(1, 0, content, cursor);
    }
}
