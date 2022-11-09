package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RecipeBookChangeSettingsPacket extends PacketBuilder {
    private RecipeBookType category;
    private boolean guiOpen;
    private boolean filteringCraftable;

    public enum RecipeBookType {
        CRAFTING, FURNACE, BLAST_FURNACE, SMOKER
    }

    public static RecipeBookChangeSettingsPacket create(RecipeBookType category, boolean guiOpen, boolean filteringCraftable) {
        return Mapping.get().packetManager().incoming().recipeBookChangeSettingsPacket(category, guiOpen, filteringCraftable);
    }
}
