package net.nonswag.tnl.manager.commands.subcommands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.listener.api.plugin.Updatable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginUpdate extends SubCommand {

    public PluginUpdate() {
        super("update", "tnl.manage");
    }

    @Override
    protected void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 2) throw new InvalidUseException(this);
        Plugin plugin = args[1].equalsIgnoreCase("Mapping") ? Mapping.get() : PluginManager.getPlugin(args[1]);
        if (plugin instanceof Updatable product) {
            Bootstrap.getInstance().async(() -> {
                net.nonswag.tnl.listener.api.plugin.PluginUpdate updater = product.getUpdater();
                if (updater != null) {
                    updater.update();
                    if (updater.isUpToDate()) {
                        source.sendMessage("%prefix% §4" + plugin.getName() + "§c is already up to date");
                    } else if (!updater.isAccessible()) {
                        source.sendMessage("%prefix% §cInvalid license key");
                    } else if (updater.isDownloaded()) {
                        String name = plugin instanceof Mapping ? "TNLListener" : plugin.getName();
                        source.sendMessage("%prefix% §cReload §4" + plugin.getName() + "§c to install the update");
                    } else {
                        updater.downloadUpdate();
                        if (updater.isFailed()) source.sendMessage("%prefix% §cFailed to update §4" + plugin.getName());
                        else source.sendMessage("%prefix% §aDownloaded update for §6" + plugin.getName());
                    }
                } else source.sendMessage("%prefix% §4" + plugin.getName() + "§c has no updater");
            });
        } else if (plugin == null) throw new InvalidUseException(this);
        else source.sendMessage("%prefix% §4" + plugin.getName() + "§c does not define an update");
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length > 2) return new ArrayList<>();
        return PluginInstall.getInstalledTNLProducts();
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin update §8[§6Product§8]");
    }
}
