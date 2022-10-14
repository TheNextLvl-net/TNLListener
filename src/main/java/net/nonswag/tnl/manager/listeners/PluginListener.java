package net.nonswag.tnl.manager.listeners;

import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.listener.api.settings.Settings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import javax.annotation.Nonnull;

public class PluginListener implements Listener {

    @EventHandler
    public void onDisable(@Nonnull PluginDisableEvent event) {
        if (Settings.PLUGIN_MANAGER.getValue()) try {
            PluginManager.checkFile(event.getPlugin());
        } catch (Exception e) {
            Logger.error.println(e);
        }
    }

    @EventHandler
    public void onEnable(@Nonnull PluginEnableEvent event) {
        if (Settings.PLUGIN_MANAGER.getValue()) try {
            PluginManager.checkFile(event.getPlugin());
        } catch (Exception e) {
            Logger.error.println(e);
        }
    }
}
