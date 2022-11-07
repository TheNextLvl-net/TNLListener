package net.nonswag.tnl.listener.api.event;

import net.nonswag.core.api.object.Getter;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;
import net.nonswag.tnl.listener.api.packets.listener.PacketReader;
import net.nonswag.tnl.listener.api.packets.listener.PacketWriter;
import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;
import net.nonswag.tnl.listener.api.plugin.CombinedPlugin;
import net.nonswag.tnl.listener.api.plugin.PluginHelper;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

@lombok.Getter
public final class EventManager {

    private final Plugin plugin;
    private final HashMap<PacketReader<?>, Class<? extends IncomingPacket>> readers = new HashMap<>();
    private final HashMap<PacketWriter<?>, Class<? extends OutgoingPacket>> writers = new HashMap<>();

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public <P extends IncomingPacket> void registerPacketReader(PacketReader<P> reader, Class<P> clazz) throws IllegalStateException {
        if (!readers.containsKey(reader)) readers.put(reader, clazz);
        else throw new IllegalStateException("reader already registered");
    }

    public <P extends OutgoingPacket> void registerPacketWriter(PacketWriter<P> writer, Class<P> clazz) throws IllegalStateException {
        if (!writers.containsKey(writer)) writers.put(writer, clazz);
        else throw new IllegalStateException("writer already registered");
    }

    public void registerListener(org.bukkit.event.Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    public void unregisterListener(org.bukkit.event.Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void unregisterEvents(Event... events) {
        for (Event event : events) event.getHandlers().unregister(getPlugin());
    }

    public boolean registerListener(Version version, Getter<org.bukkit.event.Listener> listener) {
        if (!Listener.getVersion().equals(version)) return false;
        registerListener(listener.get());
        return true;
    }

    public static HashMap<PacketReader<?>, Class<? extends IncomingPacket>> getAllReaders() {
        HashMap<PacketReader<?>, Class<? extends IncomingPacket>> readers = new HashMap<>();
        PluginHelper.getInstance().getPlugins().forEach(plugin -> {
            if (plugin instanceof CombinedPlugin combo) readers.putAll(combo.getEventManager().getReaders());
        });
        return readers;
    }

    public static HashMap<PacketWriter<?>, Class<? extends OutgoingPacket>> getAllWriters() {
        HashMap<PacketWriter<?>, Class<? extends OutgoingPacket>> writers = new HashMap<>();
        PluginHelper.getInstance().getPlugins().forEach(plugin -> {
            if (plugin instanceof CombinedPlugin combo) writers.putAll(combo.getEventManager().getWriters());
        });
        return writers;
    }
}
