package net.nonswag.tnl.listener.api.entity;

import lombok.Getter;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TNLArmorStand extends TNLEntityLiving {

    @Nonnull
    static TNLArmorStand create(@Nonnull World world, double x, double y, double z, float yaw, float pitch) {
        return Mapping.get().createArmorStand(world, x, y, z, yaw, pitch);
    }

    @Nonnull
    static TNLArmorStand create(@Nonnull World world, double x, double y, double z) {
        return create(world, x, y, z, 0f, 0f);
    }

    @Nonnull
    static TNLArmorStand create(@Nonnull Location location) {
        if (location.getWorld() == null) throw new NullPointerException();
        return create(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    void setX(double x);

    void setY(double y);

    void setZ(double z);

    void updateSize();

    boolean doAITick();

    void setHeadRotation(float f);

    void tick();

    boolean isBaby();

    void killEntity();

    void setSmall(boolean flag);

    boolean isSmall();

    void setArms(boolean flag);

    boolean hasArms();

    void setBasePlate(boolean flag);

    boolean hasBasePlate();

    void setMarker(boolean flag);

    boolean isMarker();

    void setHeadPose(@Nullable Pose pose);

    void setBodyPose(@Nullable Pose pose);

    void setLeftArmPose(@Nullable Pose pose);

    void setRightArmPose(@Nullable Pose pose);

    void setLeftLegPose(@Nullable Pose pose);

    void setRightLegPose(@Nullable Pose pose);

    boolean isInteractable();

    void setCustomNameVisible(boolean flag);

    void setCustomName(@Nullable String customName);

    <D> D getDataWatcher();

    void setVisible(boolean visible);

    void setInvulnerable(boolean invulnerable);

    void setGravity(boolean gravity);

    void setItemInMainHand(@Nullable TNLItem item);

    void setItemInOffHand(@Nullable TNLItem item);

    void setHelmet(@Nullable TNLItem item);

    void setChestplate(@Nullable TNLItem item);

    void setLeggings(@Nullable TNLItem item);

    void setBoots(@Nullable TNLItem item);

    @Getter
    class Pose {
        private final float pitch, yaw, roll;

        public Pose(double pitch, double yaw, double roll) {
            this.pitch = Math.round(pitch);
            this.yaw = Math.round(yaw);
            this.roll = Math.round(roll);
        }
    }
}
