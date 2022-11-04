package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.manager.commands.PluginCommand;

public class PluginList extends SubCommand {

    public PluginList() {
        super("list");
    }

    @Override
    protected void execute(Invocation invocation) {
        PluginCommand.sendPlugins(invocation.source());
    }
}
