package net.nonswag.tnl.manager.commands;

import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.manager.config.Config;

import javax.annotation.Nonnull;

public class PluginsCommand extends TNLCommand {

    public PluginsCommand() {
        super("plugins", Config.getInstance().isPublishPlugins() ? null : "tnl.manage", "pl");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        PluginCommand.sendPlugins(invocation.source());
    }
}
