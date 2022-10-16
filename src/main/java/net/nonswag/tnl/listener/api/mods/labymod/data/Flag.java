package net.nonswag.tnl.listener.api.mods.labymod.data;

import javax.annotation.Nonnull;

public record Flag(@Nonnull String code) {

    @Nonnull
    public static final Flag GERMANY = new Flag("de");
    @Nonnull
    public static final Flag UNITED_STATES = new Flag("us");
    @Nonnull
    public static final Flag GREAT_BRITAIN = new Flag("gb");
    @Nonnull
    public static final Flag SWISS = new Flag("ch");
    @Nonnull
    public static final Flag SPAIN = new Flag("es");
    @Nonnull
    public static final Flag FRANCE = new Flag("fr");
}
