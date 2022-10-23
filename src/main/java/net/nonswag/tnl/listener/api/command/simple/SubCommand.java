package net.nonswag.tnl.listener.api.command.simple;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.Usable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand implements Usable {

    @Getter
    @Nonnull
    private final String name;
    @Getter
    @Nonnull
    private final List<String> aliases;
    @Getter
    @Nullable
    private final String permission;

    protected SubCommand(@Nonnull String name, @Nullable String permission, @Nonnull String... aliases) {
        this.name = name.toLowerCase();
        this.permission = permission;
        List<String> strings = new ArrayList<>();
        for (String alias : aliases) strings.add(alias.toLowerCase());
        this.aliases = ImmutableList.copyOf(strings);
        initialize();
    }

    protected SubCommand(@Nonnull String name) {
        this(name, null);
    }

    protected void initialize() {
    }

    protected abstract void execute(@Nonnull Invocation invocation);

    @Nonnull
    protected List<String> suggest(@Nonnull Invocation invocation) {
        return new ArrayList<>();
    }

    @Override
    public boolean canUse(@Nonnull CommandSource source) {
        return true;
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/" + invocation.label() + " " + getName());
    }
}
