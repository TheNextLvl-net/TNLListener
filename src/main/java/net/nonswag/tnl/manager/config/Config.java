package net.nonswag.tnl.manager.config;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.file.formats.PropertiesFile;

@Getter
@Setter
public class Config extends PropertiesFile {
    @Getter
    private static final Config instance = new Config();

    private boolean pluginsGUI = true;
    private boolean publishPlugins = true;

    private Config() {
        super("plugins/Listener/Manager", "config.properties");
        getRoot().setIfAbsent("plugins-gui", isPluginsGUI());
        getRoot().setIfAbsent("everyone-can-see-plugins", isPublishPlugins());
        setPluginsGUI(getRoot().getBoolean("plugins-gui"));
        setPublishPlugins(getRoot().getBoolean("everyone-can-see-plugins"));
        save();
    }
}
