package net.nonswag.tnl.listener.listeners;

import net.nonswag.core.api.language.Language;
import net.nonswag.core.api.logger.Console;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginHelper;
import net.nonswag.tnl.listener.events.InsufficientPermissionEvent;
import net.nonswag.tnl.listener.events.UnknownCommandEvent;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        String name = event.getMessage().split(" ")[0];
        if (!(name = name.substring(1)).isBlank()) {
            Command command = PluginHelper.getInstance().getCommand(name);
            if (command == null) {
                event.setCancelled(true);
                UnknownCommandEvent commandEvent = new UnknownCommandEvent(player, event.getMessage());
                if (!commandEvent.call()) return;
                player.messenger().sendMessage(Messages.UNKNOWN_COMMAND, player, new Placeholder("command", commandEvent.getCommand()));
            } else {
                String permission = command.getPermission();
                if (permission == null || player.permissionManager().hasPermission(permission)) return;
                event.setCancelled(true);
                InsufficientPermissionEvent commandEvent = new InsufficientPermissionEvent(player, event.getMessage(), permission);
                if (!commandEvent.call()) return;
                player.messenger().sendMessage(Messages.NO_PERMISSION, player, new Placeholder("permission", commandEvent.getPermission()));
            }
        } else event.setCancelled(true);
    }

    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent event) {
        event.getCommands().removeIf(name -> PluginHelper.getInstance().getCommand(name) == null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onConsoleCommand(ServerCommandEvent event) {
        String name = event.getCommand().split(" ")[0];
        if (!name.isEmpty()) {
            Command command = PluginHelper.getInstance().getCommand(name);
            if (command != null) return;
            event.setCancelled(true);
            UnknownCommandEvent commandEvent = new UnknownCommandEvent(Console.getInstance(), event.getCommand());
            if (!commandEvent.call()) return;
            event.getSender().sendMessage(Message.format(Messages.UNKNOWN_COMMAND.message(Language.AMERICAN_ENGLISH),
                    new Placeholder("command", commandEvent.getCommand())));
        } else event.setCancelled(true);
    }
}
