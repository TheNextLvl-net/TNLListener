package net.nonswag.tnl.listener.api.player.manager;

import org.bukkit.Location;
import org.bukkit.Particle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ParticleManager extends Manager {

    public void spawn(@Nonnull Particle particle, @Nonnull Location location, int count) {
        getPlayer().bukkit().spawnParticle(particle, location, count);
    }

    public void spawn(@Nonnull Particle particle, double x, double y, double z, int count) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count);
    }

    public <T> void spawn(@Nonnull Particle particle, @Nonnull Location location, int count, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, location, count, data);
    }

    public <T> void spawn(@Nonnull Particle particle, double x, double y, double z, int count, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count, data);
    }

    public void spawn(@Nonnull Particle particle, @Nonnull Location location, int count, double offsetX, double offsetY, double offsetZ) {
        getPlayer().bukkit().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ);
    }

    public void spawn(@Nonnull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ);
    }

    public <T> void spawn(@Nonnull Particle particle, @Nonnull Location location, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, data);
    }

    public <T> void spawn(@Nonnull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, data);
    }

    public void spawn(@Nonnull Particle particle, @Nonnull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        getPlayer().bukkit().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra);
    }

    public void spawn(@Nonnull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra);
    }

    public <T> void spawn(@Nonnull Particle particle, @Nonnull Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, data);
    }

    public <T> void spawn(@Nonnull Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, @Nullable T data) {
        getPlayer().bukkit().spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
    }
}
