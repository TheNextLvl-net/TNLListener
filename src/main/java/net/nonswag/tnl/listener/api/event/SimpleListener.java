package net.nonswag.tnl.listener.api.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;

import javax.annotation.Nonnull;

public interface SimpleListener<E extends Event> extends org.bukkit.event.Listener {

    @EventHandler
    void trigger(@Nonnull E event);
}
