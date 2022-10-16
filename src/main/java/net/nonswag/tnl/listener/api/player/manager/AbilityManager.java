package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.EntityMetadataPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbilityManager extends Manager {

    @Nonnull
    protected final List<Integer> glowingEntities = new ArrayList<>();

    public void setAllowFlight(boolean flight) {
        getPlayer().bukkit().setAllowFlight(flight);
    }

    public boolean getAllowFlight() {
        return getPlayer().bukkit().getAllowFlight();
    }

    public void setFlying(boolean flying) {
        getPlayer().bukkit().setFlying(flying);
    }

    public boolean isFlying() {
        return getPlayer().bukkit().isFlying();
    }

    public void setFlySpeed(float speed) throws IllegalArgumentException {
        getPlayer().bukkit().setFlySpeed(speed);
    }

    public float getFlySpeed() {
        return getPlayer().bukkit().getFlySpeed();
    }

    public void setWalkSpeed(float speed) throws IllegalArgumentException {
        getPlayer().bukkit().setWalkSpeed(speed);
    }

    public float getWalkSpeed() {
        return getPlayer().bukkit().getWalkSpeed();
    }

    public void setInvulnerable(boolean invulnerable) {
        getPlayer().bukkit().setInvulnerable(invulnerable);
    }

    public boolean isInvulnerable() {
        return getPlayer().bukkit().isInvulnerable();
    }

    public void show(@Nonnull Plugin plugin, @Nonnull TNLPlayer player) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().showPlayer(plugin, player.bukkit()));
    }

    public void hide(@Nonnull Plugin plugin, @Nonnull TNLPlayer player) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().hidePlayer(plugin, player.bukkit()));
    }

    public boolean canSee(@Nonnull TNLPlayer player) {
        return getPlayer().bukkit().canSee(player.bukkit());
    }

    public void setGlowing(@Nonnull Entity entity, boolean glowing) {
        EntityMetadataPacket.create(entity).send(getPlayer());
        glowingEntities.remove((Object) entity.getEntityId());
        if (glowing) glowingEntities.add(entity.getEntityId());
    }

    public boolean isGlowing(@Nonnull Entity entity) {
        return glowingEntities.contains(entity.getEntityId());
    }

    public void setGlowing(boolean glowing) {
        getPlayer().bukkit().setGlowing(glowing);
    }

    public boolean isGlowing() {
        return getPlayer().bukkit().isGlowing();
    }

    public void updateAbilities() {
        setFlying(isFlying());
        setAllowFlight(getAllowFlight());
        setFlySpeed(getFlySpeed());
        setWalkSpeed(getWalkSpeed());
    }
}
