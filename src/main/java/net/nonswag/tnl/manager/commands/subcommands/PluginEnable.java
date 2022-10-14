package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PluginEnable extends SubCommand {

    public PluginEnable() {
        super("enable", "tnl.manage");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 2) throw new InvalidUseException(this);
        Plugin plugin = PluginManager.getPlugin(args[1]);
        if (plugin == null || plugin instanceof PluginBuilder builder && !builder.isManageable()) {
            throw new InvalidUseException(this);
        }
        if (!plugin.isEnabled()) {
            source.sendMessage("%prefix% §aEnabling Plugin §6" + plugin.getName());
            PluginManager.enable(plugin);
            source.sendMessage("%prefix% §aSuccessfully enabled Plugin §6" + plugin.getName());
        } else source.sendMessage("%prefix% §cThe Plugin §4" + plugin.getName() + "§c is already enabled");
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        if (invocation.arguments().length > 2) return new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        for (Plugin plugin : PluginManager.getPlugins()) {
            if (plugin instanceof PluginBuilder builder && !builder.isManageable()) continue;
            if (!plugin.isEnabled()) suggestions.add(plugin.getName());
        }
        return suggestions;
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin enable §8[§6Plugin§8]");
    }
}
