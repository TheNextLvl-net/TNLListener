package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.List;

@Getter
@Setter
public abstract class ContainerSetContentPacket extends PacketBuilder {

    @Nonnull
    private List<TNLItem> content;
    @Nonnull
    private TNLItem cursor;
    private int containerId;
    private int stateId;

    protected ContainerSetContentPacket(int containerId, int stateId, @Nonnull List<TNLItem> content, @Nonnull TNLItem cursor) {
        this.containerId = containerId;
        this.content = content;
        this.stateId = stateId;
        this.cursor = cursor;
    }

    @Nonnull
    public static ContainerSetContentPacket create(int containerId, int stateId, @Nonnull List<TNLItem> content, @Nonnull TNLItem cursor) {
        return Mapping.get().packetManager().outgoing().containerSetContentPacket(containerId, stateId, content, cursor);
    }

    @Nonnull
    public static ContainerSetContentPacket create(@Nonnull List<TNLItem> content, @Nonnull TNLItem cursor) {
        return create(1, 0, content, cursor);
    }
}
