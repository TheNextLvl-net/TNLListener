package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class SetDisplayObjectivePacket extends PacketBuilder {

    @Nullable
    private String objectiveName;
    private int slot;

    protected SetDisplayObjectivePacket(int slot, @Nullable String objectiveName) {
        this.slot = slot;
        this.objectiveName = objectiveName;
    }

    @Nonnull
    public static SetDisplayObjectivePacket create(int slot, @Nullable String objectiveName) {
        return Mapping.get().packetManager().outgoing().setDisplayObjectivePacket(slot, objectiveName);
    }
}
