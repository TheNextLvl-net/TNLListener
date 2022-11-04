package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.command.TNLCommand;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginInfo extends SubCommand {

    public PluginInfo() {
        super("info", "tnl.manage");
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
        source.sendMessage("%prefix% §7Name§8: §6" + plugin.getName());
        if (plugin.getDescription().getPrefix() != null) {
            source.sendMessage("%prefix% §7Prefix§8: §6" + plugin.getDescription().getPrefix());
        } else source.sendMessage("%prefix% §7Prefix§8: §7-§8/§7-");
        source.sendMessage("%prefix% §7Version§8: §6" + plugin.getDescription().getVersion());
        source.sendMessage("%prefix% §7Enabled§8: §6" + plugin.isEnabled());
        if (plugin.getDescription().getDescription() != null) {
            source.sendMessage("%prefix% §7Description§8: §6" + plugin.getDescription().getDescription());
        } else source.sendMessage("%prefix% §7Description§8: §7-§8/§7-");
        if (plugin.getDescription().getAuthors().size() > 0) {
            source.sendMessage("%prefix% §7Authors§8: §6" + String.join("§8, §6", plugin.getDescription().getAuthors()));
        } else source.sendMessage("%prefix% §7Authors§8: §7-§8/§7-");
        if (plugin.getDescription().getWebsite() != null) {
            source.sendMessage("%prefix% §7Website§8: §6" + plugin.getDescription().getWebsite());
        } else source.sendMessage("%prefix% §7Website§8: §7-§8/§7-");
        if (plugin.getDescription().getDepend().size() > 0) {
            source.sendMessage("%prefix% §7Depends§8: §6" + String.join("§8, §6", plugin.getDescription().getDepend()));
        } else source.sendMessage("%prefix% §7Depends§8: §7-§8/§7-");
        source.sendMessage("%prefix% §7Main§8: §6" + plugin.getDescription().getMain());
        source.sendMessage("%prefix% §7DataFolder§8: §6" + plugin.getDataFolder().getAbsolutePath());
        if (Listener.getVersion().isAtLeast(Version.v1_13)) {
            source.sendMessage("%prefix% §7API§8: §6" + plugin.getDescription().getAPIVersion());
        }
        source.sendMessage("%prefix% §7Logger§8: §6" + plugin.getLogger().getName());
        List<String> commands = new ArrayList<>();
        for (TNLCommand command : CommandManager.getRegisteredCommands(plugin)) {
            commands.add(command.getName());
            commands.addAll(command.getAliases());
        }
        commands.addAll(plugin.getDescription().getCommands().keySet());
        if (!commands.isEmpty()) {
            source.sendMessage("%prefix% §7Commands§8: §6" + String.join("§8, §6", commands));
        } else source.sendMessage("%prefix% §7Commands§8: §7-§8/§7-");
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length > 2) return new ArrayList<>();
        return PluginManager.getPlugins(false);
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin info §8[§6Plugin§8]");
    }
}
