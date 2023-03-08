package net.nonswag.tnl.listener.listeners;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import net.nonswag.tnl.listener.api.plugin.CombinedPlugin;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ServerListener implements Listener {
    private static final Logger logger = LoggerFactory.getLogger(ServerListener.class);

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
                logger.error("An error occurred while passing startup finish to " + plugin.getName(), e);
            }
        }
    }
}
