package net.nonswag.tnl.listener;

import net.nonswag.core.Core;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.logger.ColoredPrintStream;
import net.nonswag.tnl.holograms.Holograms;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.utils.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public final class Bootstrap extends TNLPlugin {
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    @Nullable
    private static Bootstrap instance = null;

    static {
        try {
            Core.init();
            Messages.init();
            ColoredPrintStream.debug.setCondition(Settings.DEBUG::getValue);
            Listener.initialize();
        } catch (MappingError e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("An error occurred while initializing the Core", e);
        }
    }

    {
        instance = this;
    }

    @Override
    public void load() {
        try {
            Listener.getInstance().onLoad();
        } catch (Exception e) {
            logger.error("An error occurred on load", e);
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
        } catch (Exception t) {
            logger.error("An error occurred after startup was finished", t);
        }
    }

    public static Bootstrap getInstance() {
        assert instance != null;
        return instance;
    }
}
