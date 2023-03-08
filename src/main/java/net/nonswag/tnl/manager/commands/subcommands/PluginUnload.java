package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginUnload extends SubCommand {

    public PluginUnload() {
        super("unload", "tnl.manage");
    }

    @Override
    protected void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 2) throw new InvalidUseException(this);
        Plugin plugin = PluginManager.getPlugin(args[1]);
        if (plugin == null || plugin instanceof PluginBuilder builder && !builder.isManageable()) {
            throw new InvalidUseException(this);
        }
        try {
            source.sendMessage("%prefix% §aUnloading plugin §6" + plugin.getName());
            PluginManager.unload(plugin);
            source.sendMessage("%prefix% §aSuccessfully unloaded plugin §6" + plugin.getName());
        } catch (Exception e) {
            source.sendMessage("%prefix% §cAn error has occurred while unloading plugin §4" + plugin.getName());
            e.printStackTrace();
        }
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length > 2) return new ArrayList<>();
        return PluginManager.getPlugins(false);
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin unload §8[§6Plugin§8]");
    }
}
