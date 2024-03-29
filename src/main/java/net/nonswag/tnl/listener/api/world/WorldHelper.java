package net.nonswag.tnl.listener.api.world;

import lombok.Getter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class WorldHelper {

    @Getter
    private static final WorldHelper instance = Mapping.get().worldHelper();

    public List<Block> cylinder(Location center, int radius, int size, Predicate<Block> condition) {
        World world = center.getWorld();
        List<Block> cylinder = new ArrayList<>();
        if (world == null || size < 1 || radius < 1) return cylinder;
        int X = center.getBlockX();
        int Z = center.getBlockZ();
        for (int x = X - radius; x <= X + radius; x++) {
            for (int z = Z - radius; z <= Z + radius; z++) {
                if ((X - x) * (X - x) + (Z - z) * (Z - z) <= (radius * radius)) {
                    Block block = world.getBlockAt(x, center.getBlockY() - 1, z);
                    if (condition.test(block)) cylinder.add(block);
                }
            }
        }
        return cylinder;
    }

    public List<Block> sphere(Location center, int radius, Predicate<Block> condition) {
        World world = center.getWorld();
        List<Block> sphere = new ArrayList<>();
        if (world == null || radius < 1) return sphere;
        for (int Y = -radius; Y < radius; Y++) {
            for (int X = -radius; X < radius; X++) {
                for (int Z = -radius; Z < radius; Z++) {
                    if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                        Block block = world.getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
                        if (condition.test(block)) sphere.add(block);
                    }
                }
            }
        }
        return sphere;
    }

    public abstract Dimension getDimension(World world);

    public abstract boolean isRegistered(Dimension dimension);

    public abstract List<TNLPlayer> getPlayers(World world);

    public boolean hasPlayers(World world) {
        return !getPlayers(world).isEmpty();
    }

    public boolean hasPlayer(World world, TNLPlayer player) {
        return getPlayers(world).contains(player);
    }

    public void removePlayers(World world) {
        getPlayers(world).forEach(all -> removePlayer(world, all));
    }

    public abstract void removePlayer(World world, TNLPlayer player);
}
