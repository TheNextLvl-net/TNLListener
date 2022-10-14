package net.nonswag.tnl.listener.api.entity;

import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;

public interface TNLFallingBlock extends TNLEntity {

    @Nonnull
    static TNLFallingBlock create(@Nonnull Location location, @Nonnull Material type) {
        if (location.getWorld() == null) throw new NullPointerException();
        return Mapping.get().createFallingBlock(location, type);
    }

    void setType(@Nonnull Material type);

    void setGlowing(boolean glowing);

    boolean teleport(@Nonnull Location location);

    boolean teleport(@Nonnull Entity entity);

    boolean teleport(@Nonnull TNLEntity entity);

    void setCustomName(@Nonnull String customName);

    void setCustomNameVisible(boolean visible);
}
