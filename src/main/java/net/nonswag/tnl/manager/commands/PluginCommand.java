package net.nonswag.tnl.manager.commands;

import net.nonswag.core.api.command.CommandSource;
import net.nonswag.tnl.listener.api.command.exceptions.InsufficientPermissionException;
import net.nonswag.tnl.listener.api.command.simple.SimpleCommand;
import net.nonswag.tnl.listener.api.gui.builtin.PluginsGUI;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.manager.Manager;
import net.nonswag.tnl.manager.commands.subcommands.*;

import javax.annotation.Nonnull;
import java.util.List;

public class PluginCommand extends SimpleCommand {

    public PluginCommand() {
        super("plugin", Manager.getInstance().isPublishPlugins() ? null : "tnl.manage");
        addSubCommand(new PluginInfo());
        addSubCommand(new PluginList());
        addSubCommand(new PluginEnable());
        addSubCommand(new PluginAutoReload());
        addSubCommand(new PluginDisable());
        addSubCommand(new PluginLoad());
        addSubCommand(new PluginInstall());
        addSubCommand(new PluginReload());
        addSubCommand(new PluginUnload());
        addSubCommand(new PluginUpdate());
    }

    public static void sendPlugins(@Nonnull CommandSource source) {
        if (Manager.getInstance().isPublishPlugins() || source.hasPermission("tnl.manage")) {
            if (!Manager.getInstance().isPluginsGUI() || !source.isPlayer()) {
                List<String> pluginList = PluginManager.getPlugins(false);
                source.sendMessage("%prefix% §7Plugins §8(§6" + pluginList.size() + "§8)§8: §6" + String.join("§8, §6", pluginList));
            } else ((TNLPlayer) source.player()).interfaceManager().openGUI(PluginsGUI.getInstance());
        } else throw new InsufficientPermissionException("tnl.manage");
    }
}
