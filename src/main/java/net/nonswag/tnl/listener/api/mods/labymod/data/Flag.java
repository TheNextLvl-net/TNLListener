package net.nonswag.tnl.listener.api.mods.labymod.data;

public record Flag(String code) {
    public static final Flag GERMANY = new Flag("de");
    public static final Flag UNITED_STATES = new Flag("us");
    public static final Flag GREAT_BRITAIN = new Flag("gb");
    public static final Flag SWISS = new Flag("ch");
    public static final Flag SPAIN = new Flag("es");
    public static final Flag FRANCE = new Flag("fr");
}
