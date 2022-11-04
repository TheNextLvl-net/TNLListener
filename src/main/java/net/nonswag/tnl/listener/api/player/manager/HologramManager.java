package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.holograms.api.event.SendEvent;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;
import java.util.function.Consumer;

public abstract class HologramManager extends Manager {

    private final HashMap<Hologram, TNLArmorStand[][]> holograms = new HashMap<>();
    private final List<Hologram> registeredHolograms = new ArrayList<>();

    private synchronized Placeholder[] worldPlaceholders() {
        List<Placeholder> placeholders = new ArrayList<>();
        Bukkit.getWorlds().forEach(world -> placeholders.add(new Placeholder("players_" + world.getName(), world.getPlayers().size())));
        return placeholders.toArray(new Placeholder[]{});
    }

    public synchronized List<Integer> getIds(Hologram hologram) {
        List<Integer> ids = new ArrayList<>();
        getArmorStands(hologram).forEach(armorStand -> ids.add(armorStand.getEntityId()));
        return ids;
    }

    public synchronized List<TNLArmorStand> getArmorStands(Hologram hologram) {
        List<TNLArmorStand> armorStands = new ArrayList<>();
        if (!holograms.containsKey(hologram)) return armorStands;
        if (!hologram.canSee().test(getPlayer())) return armorStands;
        TNLArmorStand[][] armorStandLines = holograms.get(hologram);
        if (armorStandLines == null) return armorStands;
        for (TNLArmorStand[] line : armorStandLines) if (line != null) Collections.addAll(armorStands, line);
        return armorStands;
    }

    public synchronized boolean load(Hologram hologram) {
        if (!hologram.canSee().test(getPlayer())) return false;
        if (hologram.getLocation() == null || hologram.getWorld() == null) return false;
        if (!getPlayer().worldManager().getWorld().equals(hologram.getWorld())) return false;
        List<String> lines = new ArrayList<>();
        hologram.getLines().forEach(s -> lines.addAll(Arrays.asList(Message.format(s, getPlayer(), worldPlaceholders()).split("\n"))));
        TNLArmorStand[][] armorStandLines = new TNLArmorStand[lines.size()][];
        for (int line = 0; line < lines.size(); line++) {
            String text = lines.get((lines.size() - 1) - line);
            if (text == null || text.isEmpty()) continue;
            TNLArmorStand[] armorStands = new TNLArmorStand[hologram.getDarkness()];
            for (int darkness = 0; darkness < hologram.getDarkness(); darkness++) {
                TNLArmorStand armorStand = TNLArmorStand.create(hologram.getLocation().clone().add(0, line * hologram.getLineDistance(), 0));
                armorStand.setVisible(false);
                armorStand.setSmall(true);
                armorStand.setBasePlate(false);
                armorStand.setMarker(true);
                armorStand.setCustomName(text);
                armorStand.setCustomNameVisible(true);
                armorStands[darkness] = armorStand;
            }
            armorStandLines[line] = armorStands;
        }
        if (armorStandLines.length == 0) return false;
        SendEvent event = new SendEvent(hologram, getPlayer(), armorStandLines);
        event.call();
        hologram.onSend().accept(event);
        for (TNLArmorStand[] armorStands : event.getArmorStands()) {
            if (armorStands != null) for (TNLArmorStand armorStand : armorStands) {
                if (armorStand == null) continue;
                AddEntityPacket.create(armorStand.bukkit()).send(getPlayer());
                EntityMetadataPacket.create(armorStand.bukkit()).send(getPlayer());
                EntityEquipmentPacket.create(armorStand.bukkit()).send(getPlayer());
            }
        }
        holograms.put(hologram, armorStandLines);
        return true;
    }

    public synchronized void loadAll() {
        iterateHolograms(this::load);
    }

    public synchronized boolean unload(Hologram hologram) {
        if (!holograms.containsKey(hologram)) return false;
        List<PacketBuilder> packets = new ArrayList<>();
        getArmorStands(hologram).forEach(armorStand -> packets.add(RemoveEntitiesPacket.create(armorStand.bukkit())));
        packets.forEach(packet -> packet.send(getPlayer()));
        holograms.remove(hologram);
        return !packets.isEmpty();
    }

    public synchronized void unloadAll() {
        iterateHolograms(this::unload);
    }

    public synchronized boolean unregister(Hologram hologram) {
        registeredHolograms.remove(hologram);
        return unload(hologram);
    }

    public synchronized boolean register(Hologram hologram) {
        if (registeredHolograms.contains(hologram)) return false;
        registeredHolograms.add(hologram);
        return load(hologram);
    }

    public synchronized boolean reload(Hologram hologram) {
        boolean unload = unload(hologram);
        boolean load = load(hologram);
        return unload && load;
    }

    public synchronized void reloadAll() {
        unloadAll();
        loadAll();
    }

    private synchronized void iterateHolograms(Consumer<Hologram> consumer) {
        List<Hologram> holograms = Hologram.getHolograms();
        this.holograms.keySet().forEach(hologram -> {
            if (!holograms.contains(hologram)) holograms.add(hologram);
        });
        registeredHolograms.forEach(hologram -> {
            if (!holograms.contains(hologram)) holograms.add(hologram);
        });
        iterateHolograms(holograms, consumer);
    }

    private synchronized void iterateHolograms(List<Hologram> holograms, Consumer<Hologram> consumer) {
        holograms.forEach(hologram -> {
            if (hologram != null) consumer.accept(hologram);
        });
    }

    public synchronized boolean update(Hologram hologram) {
        if (!holograms.containsKey(hologram)) return false;
        if (!hologram.canSee().test(getPlayer())) return false;
        List<String> lines = new ArrayList<>();
        hologram.getLines().forEach(s -> lines.addAll(Arrays.asList(Message.format(s, getPlayer(), worldPlaceholders()).split("\n"))));
        if (lines.isEmpty()) return unload(hologram);
        TNLArmorStand[][] armorStandLines = holograms.get(hologram);
        List<PacketBuilder> packets = new ArrayList<>();
        for (int line = 0; line < armorStandLines.length; line++) {
            if (armorStandLines[line] == null) continue;
            for (TNLArmorStand armorStand : armorStandLines[line]) {
                if (armorStand == null) continue;
                String text = lines.get((lines.size() - 1) - line);
                if (armorStand.bukkit().getName().equals(text)) continue;
                armorStand.setCustomName(text);
                packets.add(EntityMetadataPacket.create(armorStand.bukkit()));
            }
        }
        packets.forEach(packet -> packet.send(getPlayer()));
        return !packets.isEmpty();
    }

    public synchronized void updateAll() {
        new ArrayList<>(this.holograms.keySet()).forEach(this::update);
    }

    public synchronized boolean teleport(Hologram hologram, Location location) {
        if (!holograms.containsKey(hologram)) return false;
        if (!hologram.canSee().test(getPlayer())) return false;
        if (hologram.getLines().isEmpty()) return unload(hologram);
        TNLArmorStand[][] armorStandLines = holograms.get(hologram);
        List<PacketBuilder> packets = new ArrayList<>();
        for (int line = 0; line < hologram.getLines().size(); line++) {
            if (armorStandLines.length <= line) continue;
            TNLArmorStand[] armorStands = armorStandLines[line];
            if (armorStands == null) continue;
            for (int darkness = 0; darkness < hologram.getDarkness(); darkness++) {
                if (armorStands.length <= darkness || armorStands[darkness] == null) continue;
                Position position = new Position(location.getX(), location.getY() + (line * hologram.getLineDistance()), location.getZ(), location.getYaw(), location.getPitch());
                packets.add(TeleportEntityPacket.create(armorStands[darkness].bukkit(), position));
            }
        }
        packets.forEach(packet -> packet.send(getPlayer()));
        return !packets.isEmpty();
    }

    public synchronized boolean teleport(Hologram hologram, double offsetX, double offsetY, double offsetZ) {
        if (hologram.getLocation() == null) return false;
        return teleport(hologram, hologram.getLocation().clone().add(offsetX, offsetY, offsetZ));
    }
}
