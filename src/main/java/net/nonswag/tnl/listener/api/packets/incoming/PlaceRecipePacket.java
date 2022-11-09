package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlaceRecipePacket extends PacketBuilder {
    private int containerId;
    private NamespacedKey recipe;
    private boolean shift;

    public static PlaceRecipePacket create(int containerId, NamespacedKey recipe, boolean shift) {
        return Mapping.get().packetManager().incoming().placeRecipePacket(containerId, recipe, shift);
    }
}
