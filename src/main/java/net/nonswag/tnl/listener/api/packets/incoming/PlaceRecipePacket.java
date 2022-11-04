package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
public abstract class PlaceRecipePacket extends PacketBuilder {
    private NamespacedKey recipe;
    private int containerId;
    private boolean shift;

    protected PlaceRecipePacket(int containerId, NamespacedKey recipe, boolean shift) {
        this.containerId = containerId;
        this.recipe = recipe;
        this.shift = shift;
    }

    public static PlaceRecipePacket create(int containerId, NamespacedKey recipe, boolean shift) {
        return Mapping.get().packetManager().incoming().placeRecipePacket(containerId, recipe, shift);
    }
}
