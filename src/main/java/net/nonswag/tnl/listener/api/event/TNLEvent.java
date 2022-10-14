package net.nonswag.tnl.listener.api.event;

import net.nonswag.core.api.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class TNLEvent extends org.bukkit.event.Event implements Cancellable {

    @Nonnull
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    protected TNLEvent() {
        super(!Bukkit.isPrimaryThread());
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean call() {
        for (RegisteredListener registration : getHandlers().getRegisteredListeners()) {
            Plugin plugin = registration.getPlugin();
            if (!plugin.isEnabled()) continue;
            try {
                registration.callEvent(this);
            } catch (Throwable t) {
                Logger.error.println("Could not pass event " + getEventName() + " to " + plugin.getName() + plugin.getDescription().getVersion(), t);
            }
        }
        return !isCancelled();
    }

    @Nonnull
    @Override
    public String getEventName() {
        return getClass().getSimpleName();
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Nonnull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public String toString() {
        return "TNLEvent{" +
                "cancelled=" + cancelled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TNLEvent tnlEvent = (TNLEvent) o;
        return cancelled == tnlEvent.cancelled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cancelled);
    }
}
