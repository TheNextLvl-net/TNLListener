package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.manager.Manager;

import javax.annotation.Nonnull;

public class PluginAutoReload extends SubCommand {

    public PluginAutoReload() {
        super("autoreload", "tnl.manage");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        Manager.getInstance().setAutoReload(!Manager.getInstance().isAutoReload());
        if (Manager.getInstance().isAutoReload()) source.sendMessage("%prefix% §aEnabled automatic reloading");
        else source.sendMessage("%prefix% §cDisabled automatic reloading");
    }
}
