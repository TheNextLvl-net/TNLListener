package net.nonswag.tnl.holograms.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.nonswag.core.api.math.Range;
import net.nonswag.core.utils.StringUtil;
import net.nonswag.tnl.holograms.Holograms;
import net.nonswag.tnl.holograms.api.event.InteractEvent;
import net.nonswag.tnl.holograms.api.event.SendEvent;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Hologram {

    @Nonnull
    private static final HashMap<String, Hologram> holograms = new HashMap<>();

    @Getter
    @Nonnull
    private final String name;
    @Getter
    @Nonnull
    private final List<String> lines = new ArrayList<>();
    @Getter
    @Nullable
    private Location location;
    @Getter
    private double lineDistance = 0.25D;
    @Getter
    @Range(from = 1, to = 5)
    private int darkness = 1;
    @Nonnull
    private Consumer<SendEvent> onSend = event -> {
    };
    @Nonnull
    private Consumer<InteractEvent> onInteract = event -> {
    };
    @Nonnull
    private Predicate<TNLPlayer> canSee = player -> true;
    @Getter
    private final boolean automaticReload;

    public Hologram() {
        this(false);
    }

    public Hologram(boolean automaticReload) {
        this(StringUtil.random(6), automaticReload);
    }

    public Hologram(@Nonnull String name, @Nonnull String... lines) {
        this(name, true, lines);
    }

    public Hologram(@Nonnull String name, boolean automaticReload, @Nonnull String... lines) {
        this.name = name;
        this.automaticReload = automaticReload;
        setLines(lines);
    }

    @Nonnull
    public final Hologram addLines(@Nonnull String... lines) {
        for (String line : lines) this.lines.addAll(Arrays.asList(line.split("\n")));
        return this;
    }

    @Nonnull
    public Hologram addLines(@Nonnull List<String> lines) {
        return addLines(lines.toArray(new String[]{}));
    }

    @Nonnull
    public Hologram addLine(@Nonnull String line) {
        return addLines(line);
    }

    @Nonnull
    public Hologram setLines(@Nonnull String... lines) {
        getLines().clear();
        return addLines(lines);
    }


    @Nonnull
    public Hologram setLineDistance(double lineDistance) {
        this.lineDistance = lineDistance;
        return this;
    }

    @Nonnull
    public Hologram setDarkness(@Range(from = 1, to = 5) int darkness) {
        if (darkness > 5) throw new IllegalArgumentException("The hologram darkness can't be higher then 5");
        if (darkness < 1) throw new IllegalArgumentException("The hologram darkness can't be lower then 1");
        this.darkness = darkness;
        return this;
    }

    @Nonnull
    public Consumer<SendEvent> onSend() {
        return onSend;
    }

    @Nonnull
    public Consumer<InteractEvent> onInteract() {
        return onInteract;
    }

    @Nonnull
    public Predicate<TNLPlayer> canSee() {
        return canSee;
    }

    @Nonnull
    public Hologram canSee(@Nonnull Predicate<TNLPlayer> canSee) {
        this.canSee = canSee;
        return this;
    }

    @Nonnull
    public Hologram onSend(@Nonnull Consumer<SendEvent> onSend) {
        this.onSend = onSend;
        return this;
    }

    @Nonnull
    public Hologram onInteract(@Nonnull Consumer<InteractEvent> onInteract) {
        this.onInteract = onInteract;
        return this;
    }

    @Nonnull
    public Hologram setLocation(@Nonnull Location location) {
        this.location = location;
        return this;
    }

    public double getX() {
        return getLocation() == null ? 0 : getLocation().getX();
    }

    public double getY() {
        return getLocation() == null ? 0 : getLocation().getY();
    }

    public double getZ() {
        return getLocation() == null ? 0 : getLocation().getZ();
    }

    @Nullable
    public World getWorld() {
        return getLocation() != null ? getLocation().getWorld() : null;
    }

    @Nonnull
    public Hologram save() {
        if (getLocation() == null) throw new NullPointerException("Location can't be null");
        JsonObject object = new JsonObject();
        object.addProperty("darkness", getDarkness());
        object.addProperty("line-distance", getLineDistance());
        object.addProperty("position", (getWorld() != null ? getWorld().getName() : "world") + ", " + getX() + ", " + getY() + ", " + getZ());
        JsonArray lines = new JsonArray();
        for (int l = 0; l < getLines().size(); l++) lines.add(getLines().get(l) != null ? getLines().get(l) : "");
        object.add("lines", lines);
        Holograms.getInstance().getSaves().getJsonElement().getAsJsonObject().add(getName(), object);
        return register();
    }

    @Nonnull
    public Hologram delete() {
        Holograms.getInstance().getSaves().getJsonElement().getAsJsonObject().remove(getName());
        return unregister();
    }

    @Nonnull
    public Hologram updateAll() {
        Listener.getOnlinePlayers().forEach(all -> all.hologramManager().update(this));
        return this;
    }

    @Nonnull
    public Hologram teleportAll(@Nonnull Location location) {
        Listener.getOnlinePlayers().forEach(all -> all.hologramManager().teleport(this, location));
        return this;
    }

    @Nonnull
    public Hologram teleportAll(double offsetX, double offsetY, double offsetZ) {
        if (getLocation() != null) teleportAll(getLocation().clone().add(offsetX, offsetY, offsetZ));
        return this;
    }

    @Nonnull
    public Hologram loadAll() {
        Listener.getOnlinePlayers().forEach(all -> all.hologramManager().load(this));
        return this;
    }

    @Nonnull
    public Hologram unloadAll() {
        Listener.getOnlinePlayers().forEach(all -> all.hologramManager().unload(this));
        return this;
    }

    @Nonnull
    public Hologram reloadAll() {
        Listener.getOnlinePlayers().forEach(all -> {
            all.hologramManager().unloadAll();
            all.hologramManager().loadAll();
        });
        return this;
    }

    public boolean isRegistered() {
        return holograms.containsKey(getName());
    }

    @Nonnull
    public Hologram register() {
        if (isRegistered()) unregister();
        holograms.put(getName(), this);
        return loadAll();
    }

    @Nonnull
    public Hologram unregister() {
        if (!holograms.containsKey(getName())) return this;
        holograms.remove(getName());
        return unloadAll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hologram hologram = (Hologram) o;
        return Double.compare(hologram.lineDistance, lineDistance) == 0 && darkness == hologram.darkness && automaticReload == hologram.automaticReload && name.equals(hologram.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lineDistance, darkness, automaticReload);
    }

    @Nonnull
    public static Hologram getOrCreate(@Nonnull String name) {
        return getOrDefault(name, new Hologram(name));
    }

    @Nonnull
    public static Hologram getOrDefault(@Nonnull String name, @Nonnull Hologram hologram) {
        if (!holograms.containsKey(name)) holograms.put(name, hologram);
        return holograms.get(name);
    }

    @Nullable
    public static Hologram get(@Nonnull String name) {
        return holograms.get(name);
    }

    @Nonnull
    public static List<Hologram> getHolograms() {
        return new ArrayList<>(holograms.values());
    }
}
