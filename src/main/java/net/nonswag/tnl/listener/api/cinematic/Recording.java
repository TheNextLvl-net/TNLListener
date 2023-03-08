package net.nonswag.tnl.listener.api.cinematic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.nonswag.core.api.file.formats.JsonFile;
import net.nonswag.core.api.object.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recording implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(Recording.class);

    private final List<Pair<Location, Long>> steps = new ArrayList<>();
    private final String name;
    private final JsonFile saves;

    public Recording(String name) {
        this.name = name;
        this.saves = new JsonFile("plugins/Listener/Cinematics", name + ".json");
    }

    public JsonFile getSaves() {
        return saves;
    }

    public List<Pair<Location, Long>> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    private long CAPTURE = 0;

    public Recording addStep(Location location) {
        if (location.getWorld() == null) throw new NullPointerException("World cannot be null");
        if (CAPTURE == 0) CAPTURE = System.currentTimeMillis();
        long capture = System.currentTimeMillis() - CAPTURE;
        getSteps().add(new Pair<>(location, capture));
        CAPTURE = System.currentTimeMillis();
        return this;
    }

    public boolean removeStep(Location location) {
        return getSteps().removeIf(step -> step.getKey().equals(location));
    }

    public void export() {
        JsonObject object = new JsonObject();
        for (Pair<Location, Long> step : getSteps()) {
            Location key = step.getKey();
            if (key.getWorld() == null) continue;
            String location = key.getWorld().getName() + ", " + key.getX() + ", " + key.getY() + ", " + key.getZ() + ", " + key.getYaw() + ", " + key.getPitch();
            object.addProperty(location, step.getValue());
        }
        getSaves().setRoot(object);
        getSaves().save();
    }

    public File getFile() {
        return getSaves().getFile();
    }

    public boolean delete() {
        return getFile().delete();
    }

    @Nullable
    public static Recording load(String name) {
        if (!exists(name)) return null;
        Recording recording = new Recording(name);
        JsonFile config = new JsonFile("plugins/Listener/Cinematics/", name + ".json");
        JsonObject steps = config.getRoot().getAsJsonObject();
        int i = 0;
        for (Map.Entry<String, JsonElement> step : steps.entrySet()) {
            i++;
            String[] keys = step.getKey().split(", ");
            if (keys.length == 6) {
                World world = Bukkit.getWorld(keys[0]);
                if (world != null) {
                    try {
                        double x = Double.parseDouble(keys[1]);
                        double y = Double.parseDouble(keys[2]);
                        double z = Double.parseDouble(keys[3]);
                        float yaw = Float.parseFloat(keys[4]);
                        float pitch = Float.parseFloat(keys[5]);
                        Location location = new Location(world, x, y, z, yaw, pitch);
                        recording.getSteps().add(new Pair<>(location, step.getValue().getAsLong()));
                    } catch (IllegalStateException e) {
                        logger.error("Can't load timestamp signature <'" + i + "'> of animation <'" + name + "'>");
                        logger.error("<'" + step.getValue().getAsString() + "'> is not a number");
                    } catch (NumberFormatException e) {
                        logger.error("Can't load Frame <'" + i + "'> of animation <'" + name + "'>");
                        logger.error(e.getMessage());
                    }
                } else {
                    logger.error("Can't load Frame <'" + i + "'> of animation <'" + name + "'>");
                    logger.error("Can't find world <'" + keys[0] + "'>");
                }
            } else {
                logger.error("Can't load Frame <'" + i + "'> of animation <'" + name + "'>");
                logger.error(String.join(", ", keys));
            }
        }
        return recording;
    }

    public static boolean exists(String name) {
        return new File("plugins/Listener/Cinematics/" + name + ".json").exists();
    }

    public static File[] getRecordings() {
        File[] files = new File("plugins/Listener/Cinematics").listFiles((file, name) -> name.endsWith(".json"));
        return files == null ? new File[0] : files;
    }

    @Override
    public Recording clone() {
        Recording recording = new Recording(getName());
        recording.getSteps().addAll(getSteps());
        return recording;
    }
}
