package net.nonswag.tnl.listener.listeners;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.api.plugin.CombinedPlugin;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ServerListener implements Listener {

    @EventHandler
    public void onException(ServerExceptionEvent event) {
        event.getException().printStackTrace();
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        if (!event.getType().equals(ServerLoadEvent.LoadType.STARTUP)) return;
        for (Plugin plugin : PluginManager.getPlugins()) {
            if (plugin instanceof CombinedPlugin pl) try {
                pl.startupFinished();
            } catch (Exception e) {
                Logger.error.println("An error occurred while passing startup finish to " + plugin.getName(), e);
            }
        }
    }
}
