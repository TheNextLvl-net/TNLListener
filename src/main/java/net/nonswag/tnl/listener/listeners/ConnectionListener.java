package net.nonswag.tnl.listener.listeners;

import net.kyori.adventure.text.Component;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.events.TNLPlayerJoinEvent;
import net.nonswag.tnl.listener.events.TNLPlayerQuitEvent;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;

public class ConnectionListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoin(@Nonnull PlayerJoinEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer(), true);
        player.pipeline().inject();
        player.debugManager().setBrand("%server%");
        event.joinMessage(Component.empty());
        if (player.hasPlayedBefore() && Settings.JOIN_MESSAGE.getValue()) {
            Listener.broadcast(Messages.JOIN_MESSAGE, player);
        } else if (Settings.FIRST_JOIN_MESSAGE.getValue()) {
            Listener.broadcast(Messages.FIRST_JOIN_MESSAGE, player);
        }
        new TNLPlayerJoinEvent(player).call();
        player.permissionManager().updatePermissions();
        player.scoreboardManager().updateTeam();
        Bootstrap.getInstance().async(() -> player.hologramManager().loadAll());
        player.npcFactory().registerAll();
    }

    @EventHandler
    public void onQuit(@Nonnull PlayerQuitEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        TNLPlayerQuitEvent quitEvent = new TNLPlayerQuitEvent(player);
        player.hologramManager().unloadAll();
        event.quitMessage(Component.empty());
        quitEvent.call();
        if (Settings.QUIT_MESSAGE.getValue()) {
            Listener.getOnlinePlayers().forEach(all -> all.messenger().sendMessage(Messages.QUIT_MESSAGE, player));
        }
        player.pipeline().uninject();
    }

    @SuppressWarnings("deprecation")
    @EventHandler(ignoreCancelled = true)
    public void onKick(@Nonnull PlayerKickEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        if (event.getReason().equals("disconnect.spam") && !Settings.PUNISH_SPAMMING.getValue()) {
            event.setCancelled(true);
            return;
        }
        player.hologramManager().unloadAll();
        event.leaveMessage(Component.empty());
        if (!event.getReason().startsWith("%prefix%")) event.reason(Component.text("%prefix%\n§c" + event.getReason()));
        event.reason(Component.text(Message.format(event.getReason(), player, new Placeholder("reason", event.getReason()))));
        player.pipeline().uninject();
    }
}
