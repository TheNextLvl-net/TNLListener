package net.nonswag.tnl.listener.api.mods.labymod.data;

import javax.annotation.Nonnull;

public class Entry {

    @Nonnull
    private final String name;
    @Nonnull
    private final String value;
    @Nonnull
    private final Action action;

    public Entry(@Nonnull String name, @Nonnull Action action, @Nonnull String value) {
        this.name = name;
        this.action = action;
        this.value = value;
    }

    public Entry(@Nonnull String name, @Nonnull Action action) {
        this(name, action, "");
    }

    public Entry(@Nonnull String name) {
        this(name, Action.NONE);
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    @Nonnull
    public Action getAction() {
        return action;
    }

    public enum Action {
        NONE,
        CLIPBOARD,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        OPEN_BROWSER
    }
}
