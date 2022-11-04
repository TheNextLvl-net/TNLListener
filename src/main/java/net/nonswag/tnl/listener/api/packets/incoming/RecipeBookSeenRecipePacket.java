package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

@Getter
@Setter
public abstract class RecipeBookSeenRecipePacket extends PacketBuilder {
    private NamespacedKey recipe;

    protected RecipeBookSeenRecipePacket(NamespacedKey recipe) {
        this.recipe = recipe;
    }

    public static RecipeBookSeenRecipePacket create(NamespacedKey recipe) {
        return Mapping.get().packetManager().incoming().recipeBookSeenRecipePacket(recipe);
    }
}
