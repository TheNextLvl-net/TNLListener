package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlaceGhostRecipePacket extends PacketBuilder {
    private int containerId;
    private NamespacedKey recipe;

    public static PlaceGhostRecipePacket create(int containerId, NamespacedKey recipe) {
        return Mapping.get().packetManager().outgoing().placeGhostRecipePacket(containerId, recipe);
    }
}
