package net.nonswag.tnl.listener.api.event;

import net.nonswag.core.api.object.Getter;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.packets.listener.PacketReader;
import net.nonswag.tnl.listener.api.packets.listener.PacketWriter;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public record EventManager(@Nonnull Plugin plugin) {

    public void registerPacketReader(@Nonnull PacketReader reader) {
        Mapping.get().packets().registerPacketReader(reader);
    }

    public void registerPacketWriter(@Nonnull PacketWriter writer) {
        Mapping.get().packets().registerPacketWriter(writer);
    }

    public void registerListener(@Nonnull org.bukkit.event.Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin());
    }

    public void unregisterListener(@Nonnull org.bukkit.event.Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void unregisterEvents(@Nonnull Event... events) {
        for (Event event : events) event.getHandlers().unregister(plugin());
    }

    public boolean registerListener(@Nonnull Version version, @Nonnull Getter<org.bukkit.event.Listener> listener) {
        if (!Listener.getVersion().equals(version)) return false;
        registerListener(listener.get());
        return true;
    }
}
