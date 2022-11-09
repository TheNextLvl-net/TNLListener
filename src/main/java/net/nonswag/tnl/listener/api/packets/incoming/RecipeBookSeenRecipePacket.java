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
public abstract class RecipeBookSeenRecipePacket extends PacketBuilder {
    private NamespacedKey recipe;

    public static RecipeBookSeenRecipePacket create(NamespacedKey recipe) {
        return Mapping.get().packetManager().incoming().recipeBookSeenRecipePacket(recipe);
    }
}
