package net.nonswag.tnl.listener.api.entity;

import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;

public interface TNLEntityLiving extends TNLEntity {

    void setLocation(@Nonnull Location location);

    void setLocation(double x, double y, double z);

    void setLocation(double x, double y, double z, float yaw, float pitch);

    void setItem(@Nonnull SlotType slot, @Nonnull TNLItem item);

    @Nonnull
    @Override
    LivingEntity bukkit();
}
