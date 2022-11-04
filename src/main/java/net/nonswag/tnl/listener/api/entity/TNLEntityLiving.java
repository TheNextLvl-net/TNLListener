package net.nonswag.tnl.listener.api.entity;

import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface TNLEntityLiving extends TNLEntity {

    void setLocation(Location location);

    void setLocation(double x, double y, double z);

    void setLocation(double x, double y, double z, float yaw, float pitch);

    void setItem(SlotType slot, TNLItem item);

    @Override
    LivingEntity bukkit();
}
