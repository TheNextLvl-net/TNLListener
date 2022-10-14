package net.nonswag.tnl.listener.api.entity;

import lombok.Getter;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.GameProfile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public interface TNLEntityPlayer extends TNLEntityLiving {

    @Nonnull
    static TNLEntityPlayer create(@Nonnull World world, double x, double y, double z, float yaw, float pitch, @Nonnull GameProfile profile) {
        return Mapping.get().createEntityPlayer(world, x, y, z, yaw, pitch, profile);
    }

    @Nonnull
    static TNLEntityPlayer create(@Nonnull World world, double x, double y, double z, @Nonnull GameProfile profile) {
        return create(world, x, y, z, 0f, 0f, profile);
    }

    @Nonnull
    static TNLEntityPlayer create(@Nonnull Location location, @Nonnull GameProfile profile) {
        if (location.getWorld() == null) throw new NullPointerException("<'null'> is not a world");
        return create(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), profile);
    }

    void setLocation(@Nonnull Location location);

    void setLocation(double x, double y, double z);

    void setLocation(double x, double y, double z, float yaw, float pitch);

    void setItem(@Nonnull SlotType slot, @Nonnull TNLItem item);

    void setPing(int ping);

    void setGlowing(boolean glowing);

    int getPing();

    @Nonnull
    Pose getPlayerPose();

    void setPlayerPose(@Nonnull Pose pose);

    @Nonnull
    GameProfile getGameProfile();

    void setCapeVisibility(boolean visible);

    boolean getCapeVisibility();

    @Nonnull
    @Override
    Player bukkit();

    @Getter
    enum Pose {
        DIGGING,
        ROARING,
        CROAKING,
        EMERGING,
        SNIFFING,
        LONG_JUMPING,
        USING_TONGUE,
        STANDING,
        FALL_FLYING,
        SLEEPING,
        SWIMMING,
        SPIN_ATTACK,
        SNEAKING,
        DYING
    }
}
