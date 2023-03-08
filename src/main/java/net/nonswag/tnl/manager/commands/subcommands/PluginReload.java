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

public class PluginReload extends SubCommand {

    public PluginReload() {
        super("reload", "tnl.manage");
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
        if (plugin.isEnabled()) {
            source.sendMessage("%prefix% §aReloading plugin §6" + plugin.getName());
            try {
                PluginManager.reload(plugin);
                source.sendMessage("%prefix% §aSuccessfully reloaded plugin §6" + plugin.getName());
            } catch (Throwable t) {
                source.sendMessage("%prefix% §cAn error has occurred while reloading plugin §4" + plugin.getName());
                t.printStackTrace();
            }
        } else {
            source.sendMessage("%prefix% §aEnabling Plugin §6" + plugin.getName());
            PluginManager.enable(plugin);
            source.sendMessage("%prefix% §aSuccessfully enabled Plugin §6" + plugin.getName());
        }
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        if (invocation.arguments().length > 2) return suggestions;
        for (Plugin plugin : PluginManager.getPlugins()) {
            if (plugin instanceof PluginBuilder builder && !builder.isManageable()) continue;
            if (plugin.isEnabled()) suggestions.add(plugin.getName());
        }
        return suggestions;
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin reload §8[§6Plugin§8]");
    }
}
