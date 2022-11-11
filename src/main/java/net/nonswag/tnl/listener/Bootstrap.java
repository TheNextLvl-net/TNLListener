package net.nonswag.tnl.listener;

import net.nonswag.core.Core;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.holograms.Holograms;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.listener.api.settings.Settings;

@FieldsAreNullableByDefault
@MethodsReturnNonnullByDefault
public final class Bootstrap extends TNLPlugin {
    private static Bootstrap instance = null;

    {
        try {
            Core.init();
            instance = this;
            Logger.debug.setCondition(Settings.DEBUG::getValue);
            Logger.tip.setCondition(Settings.TIPS::getValue);
        } catch (Exception e) {
            Logger.error.println("An error occurred while initializing the Core", e);
        }
    }

    @Override
    public void load() {
        try {
            Listener.getInstance().onLoad();
        } catch (Exception e) {
            Logger.error.println("An error occurred on load", e);
        }
    }

    @Override
    public void enable() {
        Listener.getInstance().setEnabled(true);
        Holograms.getInstance().setEnabled(true);
    }

    @Override
    public void disable() {
        Holograms.getInstance().setEnabled(false);
        Listener.getInstance().setEnabled(false);
    }

    @Override
    public void startupFinished() {
        try {
            Listener.getInstance().startupFinished();
            Holograms.getInstance().startupFinished();
            Message.saveDefaults();
        } catch (Exception t) {
            Logger.error.println("An error occurred after startup was finished", t);
        }
    }

    public static Bootstrap getInstance() {
        assert instance != null;
        return instance;
    }
}
