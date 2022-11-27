package net.nonswag.tnl.listener.api.entity;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

import javax.annotation.Nullable;

public abstract class EntityHelper {
    @Getter
    public static EntityHelper instance = Mapping.get().entityHelper();

    @Nullable
    public abstract Entity getEntity(int id);
}
