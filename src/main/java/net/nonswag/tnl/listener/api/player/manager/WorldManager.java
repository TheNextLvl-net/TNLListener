package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.Info;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.entity.TNLEntity;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityAttachPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.GameStateChangePacket;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pose;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.map.MapView;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class WorldManager extends Manager {

    private boolean immediateRespawn = Boolean.TRUE.equals(getWorld().getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN));

    @Nonnull
    public Location getLocation() {
        return getPlayer().bukkit().getLocation();
    }

    @Nonnull
    public Location getLocation(@Nonnull Location location) {
        return getPlayer().bukkit().getLocation(location);
    }

    @Nonnull
    public Location getEyeLocation() {
        return getPlayer().bukkit().getEyeLocation();
    }

    public void setVelocity(@Nonnull Vector vector) {
        getPlayer().bukkit().setVelocity(vector);
    }

    @Nonnull
    public Vector getVelocity() {
        return getPlayer().bukkit().getVelocity();
    }

    @Nonnull
    public BoundingBox getBoundingBox() {
        return getPlayer().bukkit().getBoundingBox();
    }

    @Nonnull
    public Pose getPose() {
        return getPlayer().bukkit().getPose();
    }

    @Nonnull
    public BlockFace getFacing() {
        return getPlayer().bukkit().getFacing();
    }

    @Deprecated
    @Info("don't trust this information too much, everything the client says could be fake")
    public boolean isOnGround() {
        return getPlayer().bukkit().isOnGround();
    }

    public boolean isInWater() {
        return getPlayer().bukkit().isInWater();
    }

    public boolean isInWaterOrRain() {
        return isInRain() || isInWater();
    }

    public abstract boolean isInRain();

    @Nonnull
    public World getWorld() {
        return getPlayer().bukkit().getWorld();
    }

    @Nonnull
    public WeatherType getWeather() {
        return getWorld().isClearWeather() ? WeatherType.CLEAR : WeatherType.DOWNFALL;
    }

    public void setRotation(float yaw, float pitch) {
        Location location = getLocation();
        location.setYaw(yaw);
        location.setPitch(pitch);
        teleport(location);
    }

    public void teleport(@Nonnull Location location) {
        teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public void teleport(@Nonnull Location location, @Nonnull PlayerTeleportEvent.TeleportCause cause) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().teleport(location));
    }

    public void teleport(@Nonnull Entity entity) {
        teleport(entity.getLocation());
    }

    public void teleport(@Nonnull Entity entity, @Nonnull PlayerTeleportEvent.TeleportCause cause) {
        teleport(entity.getLocation(), cause);
    }

    @Nonnull
    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return getPlayer().bukkit().getNearbyEntities(x, y, z);
    }

    public void sendBlockChange(@Nonnull Location location, @Nonnull BlockData blockData) {
        getPlayer().bukkit().sendBlockChange(location, blockData);
    }

    public void sendBlockChange(@Nonnull Location location, @Nonnull BlockFace blockFace) {
        Block relative = location.getBlock().getRelative(blockFace);
        sendBlockChange(relative.getLocation());
    }

    public void sendBlockChange(@Nonnull Location location) {
        sendBlockChange(location.getBlock());
    }

    public void sendBlockChange(@Nonnull Block block) {
        sendBlockChange(block.getLocation(), block.getBlockData());
    }

    public void sendSignChange(@Nonnull Location location, @Nonnull String[] lines) {
        sendSignChange(location, lines, DyeColor.BLACK);
    }

    public void sendSignChange(@Nonnull Location location, @Nonnull String[] lines, @Nonnull DyeColor color) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) components.add(Component.text(line));
        getPlayer().bukkit().sendSignChange(location, components, color);
    }

    public void sendMap(@Nonnull MapView mapView) {
        getPlayer().bukkit().sendMap(mapView);
    }

    public void setSneaking(boolean sneaking) {
        getPlayer().bukkit().setSneaking(sneaking);
    }

    public boolean isSneaking() {
        return getPlayer().bukkit().isSneaking();
    }

    public void setSprinting(boolean sprinting) {
        getPlayer().bukkit().setSprinting(sprinting);
    }

    public boolean isSprinting() {
        return getPlayer().bukkit().isSprinting();
    }

    public void setImmediateRespawn(boolean immediate) {
        immediateRespawn = immediate;
        GameStateChangePacket.create(GameStateChangePacket.IMMEDIATE_RESPAWN, respawn -> respawn.immediate(immediate)).send(getPlayer());
    }

    @Nullable
    public RayTraceResult rayTraceBlocks(double range) {
        return getPlayer().bukkit().rayTraceBlocks(range);
    }

    @Nullable
    public RayTraceResult rayTraceBlocks(double range, @Nonnull FluidCollisionMode mode) {
        return getPlayer().bukkit().rayTraceBlocks(range, mode);
    }

    @Nullable
    public RayTraceResult rayTraceEntities(double range) {
        return getWorld().rayTraceEntities(getEyeLocation(), getEyeLocation().getDirection(), range, entity ->
                !getPlayer().getUniqueId().equals(entity.getUniqueId()));
    }

    @Nonnull
    public List<Block> getLineOfSight(@Nullable Set<Material> ignoredBlocks, int range) {
        return getPlayer().bukkit().getLineOfSight(ignoredBlocks, range);
    }

    @Nullable
    public Entity getTargetEntity(double range) {
        RayTraceResult result = rayTraceEntities(range);
        return result == null ? null : result.getHitEntity();
    }

    @Nonnull
    public Block getTargetBlock(@Nullable Set<Material> ignoredBlocks, int range) {
        return getPlayer().bukkit().getTargetBlock(ignoredBlocks, range);
    }

    @Nullable
    public Block getTargetBlock(double range) {
        return getTargetBlock(range, FluidCollisionMode.NEVER);
    }

    @Nullable
    public Block getTargetBlock(double range, @Nonnull FluidCollisionMode mode) {
        RayTraceResult hitResult = rayTraceBlocks(range, mode);
        return hitResult != null ? hitResult.getHitBlock() : null;
    }

    @Nonnull
    public Location getTargetLocation(double range) {
        return getLocation().clone().add(getLocation().getDirection().multiply(range));
    }

    public void leash(@Nonnull TNLEntity entity) {
        EntityAttachPacket.create(getPlayer().getEntityId(), entity.getEntityId()).send(getPlayer());
    }

    public void unleash() {
        EntityAttachPacket.create(getPlayer().bukkit()).send(getPlayer());
    }

    public void strikeLightning(@Nonnull Location location) {
        strikeLightning(location, true, true);
    }

    public abstract void strikeLightning(@Nonnull Location location, boolean effect, boolean sound);

    public void setCompassTarget(@Nonnull Location location) {
        getPlayer().bukkit().setCompassTarget(location);
    }

    @Nonnull
    public Entity spawn(@Nonnull EntityType type) {
        return getWorld().spawnEntity(getLocation(), type);
    }

    @Nonnull
    public <T extends Entity> T spawn(@Nonnull EntityType type, @Nonnull Class<T> clazz) {
        return spawn(type, clazz, t -> {});
    }

    @Nonnull
    public <T extends Entity> T spawn(@Nonnull EntityType type, @Nonnull Class<T> clazz, @Nonnull Consumer<T> function) {
        return getWorld().spawn(getLocation(), clazz);
    }
}
