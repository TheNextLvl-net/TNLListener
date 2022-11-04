package net.nonswag.tnl.listener.api.gamemode;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.GameMode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@ToString
public enum Gamemode {
    UNKNOWN("Unknown", -1),
    SURVIVAL("Survival", 0),
    CREATIVE("Creative", 1),
    ADVENTURE("Adventure", 2),
    SPECTATOR("Spectator", 3);

    private final String name;
    private final int id;

    Gamemode(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public boolean isUnknown() {
        return equals(UNKNOWN);
    }

    public boolean isSurvival() {
        return equals(SURVIVAL);
    }

    public boolean isCreative() {
        return equals(CREATIVE);
    }

    public boolean isAdventure() {
        return equals(ADVENTURE);
    }

    public boolean isSpectator() {
        return equals(SPECTATOR);
    }

    public boolean isVincible() {
        return isUnknown() || isSurvival() || isAdventure();
    }

    public boolean isInvincible() {
        return isCreative() || isSpectator();
    }

    @Nonnull
    public GameMode bukkit() {
        if (equals(Gamemode.CREATIVE)) return GameMode.CREATIVE;
        else if (equals(Gamemode.ADVENTURE)) return GameMode.ADVENTURE;
        else if (equals(Gamemode.SPECTATOR)) return GameMode.SPECTATOR;
        return GameMode.SURVIVAL;
    }

    @Nonnull
    public static Gamemode cast(GameMode bukkit) {
        if (bukkit.equals(GameMode.CREATIVE)) return CREATIVE;
        else if (bukkit.equals(GameMode.ADVENTURE)) return ADVENTURE;
        else if (bukkit.equals(GameMode.SPECTATOR)) return SPECTATOR;
        return SURVIVAL;
    }

    @Nullable
    public static Gamemode cast(String string) {
        for (Gamemode gm : values()) {
            if (gm.name().toLowerCase().startsWith(string.toLowerCase()) || string.equals(gm.getId() + "")) return gm;
        }
        return null;
    }

    @Nullable
    public static Gamemode cast(int i) {
        for (Gamemode mode : values()) if (mode.getId() == i) return mode;
        return null;
    }
}
