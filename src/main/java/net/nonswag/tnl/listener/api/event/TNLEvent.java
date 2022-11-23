package net.nonswag.tnl.listener.api.event;

import com.destroystokyo.paper.event.server.ServerExceptionEvent;
import com.destroystokyo.paper.exception.ServerEventException;
import lombok.Getter;
import net.nonswag.core.api.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import javax.annotation.Nonnull;

public abstract class TNLEvent extends Event {
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    protected TNLEvent() {
        super(!Bukkit.isPrimaryThread());
    }

    public boolean call() {
        for (RegisteredListener registration : getHandlers().getRegisteredListeners()) {
            Plugin plugin = registration.getPlugin();
            if (!plugin.isEnabled()) continue;
            try {
                registration.callEvent(this);
            } catch (Throwable t) {
                String string = "Could not pass event %s to %s (%s)".formatted(getEventName(), plugin.getName(), plugin.getDescription().getVersion());
                new ServerExceptionEvent(new ServerEventException(string, t, registration.getPlugin(), registration.getListener(), this)).callEvent();
                Logger.error.println(string);
            }
        }
        return !(this instanceof Cancellable cancellable) || !cancellable.isCancelled();
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
