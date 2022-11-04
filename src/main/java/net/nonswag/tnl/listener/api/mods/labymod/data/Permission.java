package net.nonswag.tnl.listener.api.mods.labymod.data;

import lombok.Getter;

@Getter
public enum Permission {
    IMPROVED_LAVA("Improved Lava"),
    CROSSHAIR_SYNC("Crosshair sync"),
    REFILL_FIX("Refill fix"),
    RANGE("Range"),
    SLOWDOWN("Slowdown"),

    GUI_ALL("LabyMod GUI"),
    GUI_POTION_EFFECTS("Potion Effects"),
    GUI_ARMOR_HUD("Armor HUD"),
    GUI_ITEM_HUD("Item HUD"),

    BLOCKBUILD("Blockbuild"),
    TAGS("Tags"),
    CHAT("Chat features"),
    ANIMATIONS("Animations"),
    SATURATION_BAR("Saturation bar");

    private final String name;

    Permission(String name) {
        this.name = name;
    }
}
