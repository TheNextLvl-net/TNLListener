package net.nonswag.tnl.listener.listeners;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;

public class WorldListener implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWorldChange(@Nonnull PlayerChangedWorldEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        Bootstrap.getInstance().async(() -> {
            player.hologramManager().reloadAll();
            player.npcFactory().updateAll(player.worldManager().getLocation());
        });
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMove(@Nonnull PlayerMoveEvent event) {
        TNLPlayer.cast(event.getPlayer()).npcFactory().updateAll(event.getTo());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTeleport(@Nonnull PlayerTeleportEvent event) {
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)) return;
        Bootstrap.getInstance().async(() -> {
            TNLPlayer player = TNLPlayer.cast(event.getPlayer());
            player.npcFactory().updateAll(event.getTo());
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(@Nonnull PlayerRespawnEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        player.hologramManager().reloadAll();
        player.npcFactory().updateAll(event.getRespawnLocation());
    }
}
