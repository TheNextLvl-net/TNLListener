package net.nonswag.tnl.listener.api.entity;

import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

public interface TNLFallingBlock extends TNLEntity {

    static TNLFallingBlock create(Location location, Material type) {
        if (location.getWorld() == null) throw new NullPointerException();
        return Mapping.get().createFallingBlock(location, type);
    }

    void setType(Material type);

    void setGlowing(boolean glowing);

    boolean teleport(Location location);

    boolean teleport(Entity entity);

    boolean teleport(TNLEntity entity);

    void setCustomName(String customName);

    void setCustomNameVisible(boolean visible);
}
