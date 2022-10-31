package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class PlaceRecipePacket extends PacketBuilder {
    @Nonnull
    private NamespacedKey recipe;
    private int containerId;
    private boolean shift;

    protected PlaceRecipePacket(int containerId, @Nonnull NamespacedKey recipe, boolean shift) {
        this.containerId = containerId;
        this.recipe = recipe;
        this.shift = shift;
    }

    @Nonnull
    public static PlaceRecipePacket create(int containerId, @Nonnull NamespacedKey recipe, boolean shift) {
        return Mapping.get().packetManager().incoming().placeRecipePacket(containerId, recipe, shift);
    }
}
