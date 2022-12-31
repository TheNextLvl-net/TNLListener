package net.nonswag.tnl.manager;

import lombok.Getter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.manager.commands.PluginCommand;
import net.nonswag.tnl.manager.commands.PluginsCommand;

@FieldsAreNonnullByDefault
public final class Manager extends PluginBuilder {
    @Getter
    private static final Manager instance = new Manager();

    private Manager() {
        super(Manager.class, Listener.getInstance());
    }

    @Override
    public void enable() {
        if (Settings.PLUGIN_MANAGEMENT.getValue()) getCommandManager().registerCommand(new PluginCommand());
        getCommandManager().registerCommand(new PluginsCommand());
    }
}
