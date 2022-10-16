package net.nonswag.tnl.listener.api.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class WorldHelper {

    @Nonnull
    public static List<Block> cylinder(@Nonnull Location center, int radius, int size, @Nonnull Predicate<Block> condition) {
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

    @Nonnull
    public static List<Block> sphere(@Nonnull Location center, int radius, @Nonnull Predicate<Block> condition) {
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
}
