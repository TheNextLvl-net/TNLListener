package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

@Getter
@Setter
public class PlaceRecipePacket implements IncomingPacket {
    @Nonnull
    private NamespacedKey recipe;
    private int containerId;
    private boolean shift;

    public PlaceRecipePacket(int containerId, @Nonnull NamespacedKey recipe, boolean shift) {
        this.containerId = containerId;
        this.recipe = recipe;
        this.shift = shift;
    }
}
