package net.nonswag.tnl.listener.api.entity;

import lombok.Getter;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.GameProfile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface TNLEntityPlayer extends TNLEntityLiving {

    static TNLEntityPlayer create(World world, double x, double y, double z, float yaw, float pitch, GameProfile profile) {
        return Mapping.get().createEntityPlayer(world, x, y, z, yaw, pitch, profile);
    }

    static TNLEntityPlayer create(World world, double x, double y, double z, GameProfile profile) {
        return create(world, x, y, z, 0f, 0f, profile);
    }

    static TNLEntityPlayer create(Location location, GameProfile profile) {
        if (location.getWorld() == null) throw new NullPointerException("<'null'> is not a world");
        return create(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), profile);
    }

    void setLocation(Location location);

    void setLocation(double x, double y, double z);

    void setLocation(double x, double y, double z, float yaw, float pitch);

    void setItem(SlotType slot, TNLItem item);

    void setPing(int ping);

    void setGlowing(boolean glowing);

    int getPing();

    Pose getPlayerPose();

    void setPlayerPose(Pose pose);

    GameProfile getGameProfile();

    void setCapeVisibility(boolean visible);

    boolean getCapeVisibility();

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
