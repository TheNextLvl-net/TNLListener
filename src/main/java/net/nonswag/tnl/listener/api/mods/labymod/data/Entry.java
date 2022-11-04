package net.nonswag.tnl.listener.api.mods.labymod.data;

public class Entry {

    private final String name;
    private final String value;
    private final Action action;

    public Entry(String name, Action action, String value) {
        this.name = name;
        this.action = action;
        this.value = value;
    }

    public Entry(String name, Action action) {
        this(name, action, "");
    }

    public Entry(String name) {
        this(name, Action.NONE);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

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
