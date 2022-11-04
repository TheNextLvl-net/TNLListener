package net.nonswag.tnl.listener.api.plugin;

import net.nonswag.core.api.annotation.MethodsReturnNullableByDefault;

@MethodsReturnNullableByDefault
public interface Updatable {

    PluginUpdate getUpdater();
}
