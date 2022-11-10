package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.Info;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.entity.TNLEntity;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityAttachPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.GameEventPacket;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Getter
public abstract class WorldManager extends Manager {

    private boolean immediateRespawn = Boolean.TRUE.equals(getWorld().getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN));

    public Location getLocation() {
        return getPlayer().bukkit().getLocation();
    }

    public Location getLocation(Location location) {
        return getPlayer().bukkit().getLocation(location);
    }

    public Location getEyeLocation() {
        return getPlayer().bukkit().getEyeLocation();
    }

    public void setVelocity(Vector vector) {
        getPlayer().bukkit().setVelocity(vector);
    }

    public Vector getVelocity() {
        return getPlayer().bukkit().getVelocity();
    }

    public BoundingBox getBoundingBox() {
        return getPlayer().bukkit().getBoundingBox();
    }

    public Pose getPose() {
        return getPlayer().bukkit().getPose();
    }

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

    public World getWorld() {
        return getPlayer().bukkit().getWorld();
    }

    public WeatherType getWeather() {
        return getWorld().isClearWeather() ? WeatherType.CLEAR : WeatherType.DOWNFALL;
    }

    public void setRotation(float yaw, float pitch) {
        Location location = getLocation();
        location.setYaw(yaw);
        location.setPitch(pitch);
        teleport(location);
    }

    public void teleport(Location location) {
        teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public void teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().teleport(location));
    }

    public void teleport(Entity entity) {
        teleport(entity.getLocation());
    }

    public void teleport(Entity entity, PlayerTeleportEvent.TeleportCause cause) {
        teleport(entity.getLocation(), cause);
    }

    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return getPlayer().bukkit().getNearbyEntities(x, y, z);
    }

    public void sendBlockChange(Location location, BlockData blockData) {
        getPlayer().bukkit().sendBlockChange(location, blockData);
    }

    public void sendBlockChange(Location location, BlockFace blockFace) {
        Block relative = location.getBlock().getRelative(blockFace);
        sendBlockChange(relative.getLocation());
    }

    public void sendBlockChange(Location location) {
        sendBlockChange(location.getBlock());
    }

    public void sendBlockChange(Block block) {
        sendBlockChange(block.getLocation(), block.getBlockData());
    }

    public void sendSignChange(Location location, String[] lines) {
        sendSignChange(location, lines, DyeColor.BLACK);
    }

    public void sendSignChange(Location location, String[] lines, DyeColor color) {
        List<Component> components = new ArrayList<>();
        for (String line : lines) components.add(Component.text(line));
        getPlayer().bukkit().sendSignChange(location, components, color);
    }

    public void sendMap(MapView mapView) {
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
        GameEventPacket.create(GameEventPacket.IMMEDIATE_RESPAWN, respawn -> respawn.immediate(immediate)).send(getPlayer());
    }

    @Nullable
    public RayTraceResult rayTraceBlocks(double range) {
        return getPlayer().bukkit().rayTraceBlocks(range);
    }

    @Nullable
    public RayTraceResult rayTraceBlocks(double range, FluidCollisionMode mode) {
        return getPlayer().bukkit().rayTraceBlocks(range, mode);
    }

    @Nullable
    public RayTraceResult rayTraceEntities(double range) {
        return getWorld().rayTraceEntities(getEyeLocation(), getEyeLocation().getDirection(), range, entity ->
                !getPlayer().getUniqueId().equals(entity.getUniqueId()));
    }

    public List<Block> getLineOfSight(@Nullable Set<Material> ignoredBlocks, int range) {
        return getPlayer().bukkit().getLineOfSight(ignoredBlocks, range);
    }

    @Nullable
    public Entity getTargetEntity(double range) {
        RayTraceResult result = rayTraceEntities(range);
        return result == null ? null : result.getHitEntity();
    }

    public Block getTargetBlock(@Nullable Set<Material> ignoredBlocks, int range) {
        return getPlayer().bukkit().getTargetBlock(ignoredBlocks, range);
    }

    @Nullable
    public Block getTargetBlock(double range) {
        return getTargetBlock(range, FluidCollisionMode.NEVER);
    }

    @Nullable
    public Block getTargetBlock(double range, FluidCollisionMode mode) {
        RayTraceResult hitResult = rayTraceBlocks(range, mode);
        return hitResult != null ? hitResult.getHitBlock() : null;
    }

    public Location getTargetLocation(double range) {
        return getLocation().clone().add(getLocation().getDirection().multiply(range));
    }

    public void leash(TNLEntity entity) {
        EntityAttachPacket.create(getPlayer().getEntityId(), entity.getEntityId()).send(getPlayer());
    }

    public void unleash() {
        EntityAttachPacket.create(getPlayer().bukkit()).send(getPlayer());
    }

    public void strikeLightning(Location location) {
        strikeLightning(location, true, true);
    }

    public abstract void strikeLightning(Location location, boolean effect, boolean sound);

    public void setCompassTarget(Location location) {
        getPlayer().bukkit().setCompassTarget(location);
    }

    public Entity spawn(EntityType type) {
        return getWorld().spawnEntity(getLocation(), type);
    }

    public <T extends Entity> T spawn(EntityType type, Class<T> clazz) {
        return spawn(type, clazz, t -> {});
    }

    public <T extends Entity> T spawn(EntityType type, Class<T> clazz, Consumer<T> function) {
        return getWorld().spawn(getLocation(), clazz);
    }
}
