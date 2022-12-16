package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerInfoRemovePacket extends PacketBuilder {
    private List<UUID> profileIds;

    public static PlayerInfoRemovePacket create(List<UUID> profileIds) {
        return Mapping.get().packetManager().outgoing().playerInfoRemovePacket(profileIds);
    }

    public static PlayerInfoRemovePacket create(UUID profileId) {
        return create(List.of(profileId));
    }
}
