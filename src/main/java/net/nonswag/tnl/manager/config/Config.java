package net.nonswag.tnl.manager.config;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.file.formats.PropertyFile;

@Getter
@Setter
public class Config extends PropertyFile {
    @Getter
    private static final Config instance = new Config();

    private boolean pluginsGUI = true;
    private boolean publishPlugins = true;

    private Config() {
        super("plugins/Listener/Manager", "config.properties");
        setIfAbsent("plugins-gui", isPluginsGUI());
        setIfAbsent("everyone-can-see-plugins", isPublishPlugins());
        setPluginsGUI(getBoolean("plugins-gui"));
        setPublishPlugins(getBoolean("everyone-can-see-plugins"));
        save();
    }
}
