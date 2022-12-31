package net.nonswag.tnl.manager.commands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.command.exceptions.InsufficientPermissionException;
import net.nonswag.tnl.listener.api.command.simple.SimpleCommand;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.manager.api.config.Config;
import net.nonswag.tnl.manager.commands.subcommands.*;
import net.nonswag.tnl.manager.gui.PluginsGUI;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class PluginCommand extends SimpleCommand {

    public PluginCommand() {
        super("plugin", Config.getInstance().isPublishPlugins() ? null : "tnl.manage");
        addSubCommand(new PluginInfo());
        addSubCommand(new PluginList());
        addSubCommand(new PluginEnable());
        addSubCommand(new PluginDisable());
        addSubCommand(new PluginLoad());
        addSubCommand(new PluginReload());
        addSubCommand(new PluginUnload());
    }

    public static void sendPlugins(CommandSource source) {
        if (Config.getInstance().isPublishPlugins() || source.hasPermission("tnl.manage"))
            throw new InsufficientPermissionException("tnl.manage");
        if (!Config.getInstance().isPluginsGUI() || !(source instanceof TNLPlayer player)) {
            List<String> plugins = PluginManager.getPlugins(false);
            source.sendMessage("%prefix% §7Plugins §8(§6" + plugins.size() + "§8)§8: §6" + String.join("§8, §6", plugins));
        } else player.interfaceManager().openGUI(PluginsGUI.getInstance());
    }
}
