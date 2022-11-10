package net.nonswag.tnl.listener.api.event;

import net.nonswag.core.api.object.Getter;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;
import net.nonswag.tnl.listener.api.packets.listener.PacketReader;
import net.nonswag.tnl.listener.api.packets.listener.PacketWriter;
import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@lombok.Getter
public final class EventManager {

    public static final List<EventManager> MANAGERS = new ArrayList<>();

    private final Plugin plugin;
    private final HashMap<Class<? extends IncomingPacket>, List<PacketReader<?>>> readers = new HashMap<>();
    private final HashMap<Class<? extends OutgoingPacket>, List<PacketWriter<?>>> writers = new HashMap<>();

    public EventManager(Plugin plugin) {
        this.plugin = plugin;
        MANAGERS.add(this);
    }

    public void unregisterAll() {
        MANAGERS.remove(this);
    }

    public <P extends IncomingPacket> void registerPacketReader(Class<P> clazz, PacketReader<P> reader) throws IllegalStateException {
        var readers = this.readers.getOrDefault(clazz, new ArrayList<>());
        if (readers.contains(reader)) throw new IllegalStateException("reader already registered");
        readers.add(reader);
        this.readers.put(clazz, readers);
    }

    public <P extends OutgoingPacket> void registerPacketWriter(Class<P> clazz, PacketWriter<P> writer) throws IllegalStateException {
        var writers = this.writers.getOrDefault(clazz, new ArrayList<>());
        if (writers.contains(writer)) throw new IllegalStateException("writer already registered");
        writers.add(writer);
        this.writers.put(clazz, writers);
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

    public static HashMap<Class<? extends IncomingPacket>, List<PacketReader<?>>> getAllReaders() {
        HashMap<Class<? extends IncomingPacket>, List<PacketReader<?>>> readers = new HashMap<>();
        MANAGERS.forEach(manager -> manager.getReaders().forEach((clazz, reader) ->
                readers.put(clazz, readers.getOrDefault(clazz, new ArrayList<>()))));
        return readers;
    }

    public static List<PacketReader<?>> getAllReaders(Class<?> clazz) {
        var mapped = Mapping.get().packetManager().incoming().map(clazz);
        List<PacketReader<?>> readers = new ArrayList<>();
        MANAGERS.forEach(manager -> readers.addAll(manager.getReaders().getOrDefault(mapped, new ArrayList<>())));
        return readers;
    }

    public static HashMap<Class<? extends OutgoingPacket>, List<PacketWriter<?>>> getAllWriters() {
        HashMap<Class<? extends OutgoingPacket>, List<PacketWriter<?>>> writers = new HashMap<>();
        MANAGERS.forEach(manager -> manager.getWriters().forEach((clazz, writer) ->
                writers.put(clazz, writers.getOrDefault(clazz, new ArrayList<>()))));
        return writers;
    }

    public static List<PacketWriter<?>> getAllWriters(Class<?> clazz) {
        var mapped = Mapping.get().packetManager().outgoing().map(clazz);
        List<PacketWriter<?>> writers = new ArrayList<>();
        MANAGERS.forEach(manager -> writers.addAll(manager.getWriters().getOrDefault(mapped, new ArrayList<>())));
        return writers;
    }
}
