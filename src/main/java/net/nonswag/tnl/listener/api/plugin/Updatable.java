package net.nonswag.tnl.listener.api.plugin;

import javax.annotation.Nullable;

public interface Updatable {

    @Nullable
    PluginUpdate getUpdater();
}
