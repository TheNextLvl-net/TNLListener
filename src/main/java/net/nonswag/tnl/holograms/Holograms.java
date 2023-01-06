package net.nonswag.tnl.holograms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.file.formats.JsonFile;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.holograms.api.UpdateRunnable;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nonnull;

@Getter
@Setter
public final class Holograms extends PluginBuilder {

    @Getter
    @Nonnull
    private static final Holograms instance = new Holograms();

    @Nonnull
    private final JsonFile saves = new JsonFile("plugins/Listener/Holograms/", "saves.json");
    private long updateTime = 5000L;

    private Holograms() {
        super(Holograms.class, Bootstrap.getInstance());
        if (!saves.getRoot().getAsJsonObject().has("update-time")) {
            saves.getRoot().getAsJsonObject().addProperty("update-time", updateTime);
        } else setUpdateTime(saves.getRoot().getAsJsonObject().get("update-time").getAsLong());
    }

    @Override
    public void startupFinished() {
        JsonObject root = saves.getRoot().getAsJsonObject();
        root.entrySet().forEach(entry -> {
            if (!entry.getValue().isJsonObject()) return;
            Hologram hologram = Hologram.getOrCreate(entry.getKey());
            JsonObject object = root.getAsJsonObject(hologram.getName());
            String position = object.get("position").getAsString();
            String[] split = position.split(", ");
            if (split.length != 4 && split.length != 6) return;
            double x = Double.parseDouble(split[1]), y = Double.parseDouble(split[2]), z = Double.parseDouble(split[3]);
            float yaw = split.length == 6 ? Float.parseFloat(split[4]) : 0, pitch = split.length == 6 ? Float.parseFloat(split[5]) : 0;
            hologram.setLocation(new Location(Bukkit.getWorld(split[0]), x, y, z, yaw, pitch));
            hologram.setDarkness(object.get("darkness").getAsInt());
            hologram.setLineDistance(object.get("line-distance").getAsDouble());
            JsonArray lines = object.get("lines").getAsJsonArray();
            lines.forEach(line -> {
                String string = line.getAsString();
                if (string != null && !string.isBlank()) hologram.getLines().add(string);
                else hologram.getLines().add("");
            });
            hologram.register();
        });
        UpdateRunnable.start();
    }

    @Override
    public void disable() {
        getSaves().save();
        UpdateRunnable.stop();
        Listener.getOnlinePlayers().forEach(all -> all.hologramManager().unloadAll());
    }
}
