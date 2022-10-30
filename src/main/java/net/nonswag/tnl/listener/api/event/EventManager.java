package net.nonswag.tnl.listener.api.event;

import net.nonswag.core.api.object.Getter;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.packets.incoming.IncomingPacket;
import net.nonswag.tnl.listener.api.packets.listener.PacketReader;
import net.nonswag.tnl.listener.api.packets.listener.PacketWriter;
import net.nonswag.tnl.listener.api.packets.outgoing.OutgoingPacket;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.HashMap;

@lombok.Getter
public class EventManager {

    @Nonnull
    private final Plugin plugin;
    @Nonnull
    private final HashMap<PacketReader<?>, Class<? extends IncomingPacket>> readers = new HashMap<>();
    @Nonnull
    private final HashMap<PacketWriter<?>, Class<? extends OutgoingPacket>> writers = new HashMap<>();

    public EventManager(@Nonnull Plugin plugin) {
        this.plugin = plugin;
    }

    public <P extends IncomingPacket> void registerPacketReader(@Nonnull PacketReader<P> reader, @Nonnull Class<P> clazz) throws IllegalStateException {
        if (!readers.containsKey(reader)) readers.put(reader, clazz);
        else throw new IllegalStateException("reader already registered");
    }

    public void registerPacketReader(@Nonnull PacketReader<IncomingPacket> reader) {
        registerPacketReader(reader, IncomingPacket.class);
    }

    public <P extends OutgoingPacket> void registerPacketWriter(@Nonnull PacketWriter<P> writer, @Nonnull Class<P> clazz) throws IllegalStateException {
        if (!writers.containsKey(writer)) writers.put(writer, clazz);
        else throw new IllegalStateException("writer already registered");
    }

    public void registerPacketWriter(@Nonnull PacketWriter<OutgoingPacket> writer) {
        registerPacketWriter(writer, OutgoingPacket.class);
    }

    public void registerListener(@Nonnull org.bukkit.event.Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
    }

    public void unregisterListener(@Nonnull org.bukkit.event.Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public void unregisterEvents(@Nonnull Event... events) {
        for (Event event : events) event.getHandlers().unregister(getPlugin());
    }

    public boolean registerListener(@Nonnull Version version, @Nonnull Getter<org.bukkit.event.Listener> listener) {
        if (!Listener.getVersion().equals(version)) return false;
        registerListener(listener.get());
        return true;
    }
}
