package net.nonswag.tnl.manager;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.file.formats.PropertyFile;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.manager.commands.PluginCommand;
import net.nonswag.tnl.manager.commands.PluginsCommand;
import net.nonswag.tnl.manager.runnables.AutoReload;

import javax.annotation.Nonnull;

@Getter
@Setter
public final class Manager extends PluginBuilder {

    @Getter
    @Nonnull
    private static final Manager instance = (Manager) new Manager().register();

    @Nonnull
    private static final PropertyFile configuration = new PropertyFile("plugins/Listener/Manager/", "config.properties");

    private boolean autoReload = false;
    private boolean pluginsGUI = true;
    private boolean publishPlugins = true;

    private Manager() {
        super(Manager.class, Listener.getInstance());
    }

    @Override
    public void enable() {
        configuration.setIfAbsent("auto-reload", isAutoReload());
        configuration.setIfAbsent("plugins-gui", isPluginsGUI());
        configuration.setIfAbsent("everyone-can-see-plugins", isPublishPlugins());
        setAutoReload(configuration.getBoolean("auto-reload"));
        setPluginsGUI(configuration.getBoolean("plugins-gui"));
        setPublishPlugins(configuration.getBoolean("everyone-can-see-plugins"));
        configuration.save();
        AutoReload.start();
        getCommandManager().registerCommand(new PluginCommand());
        getCommandManager().registerCommand(new PluginsCommand());
    }

    @Override
    public void disable() {
        AutoReload.shutdown();
        configuration.save();
    }
}
