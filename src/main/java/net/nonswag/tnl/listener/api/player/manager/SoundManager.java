package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.packets.GameStateChangePacket;
import org.bukkit.*;

import javax.annotation.Nonnull;

public abstract class SoundManager extends Manager {

    public void playNote(@Nonnull Location location, @Nonnull Instrument instrument, @Nonnull Note note) {
        getPlayer().bukkit().playNote(location, instrument, note);
    }

    public void playNote(@Nonnull Instrument instrument, @Nonnull Note note) {
        playNote(getPlayer().bukkit().getLocation(), instrument, note);
    }

    public void playSound(@Nonnull Sound sound) {
        playSound(sound, 0.6f, 1);
    }

    public void playSound(@Nonnull String sound) {
        playSound(sound, 0.6f, 1);
    }

    public void playSound(@Nonnull Sound sound, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, volume, pitch);
    }

    public void playSound(@Nonnull String sound, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, volume, pitch);
    }

    public void playSound(@Nonnull Location location, @Nonnull Sound sound, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, volume, pitch);
    }

    public void playSound(@Nonnull Location location, @Nonnull String sound, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, volume, pitch);
    }

    public void playSound(@Nonnull Sound sound, @Nonnull SoundCategory category) {
        playSound(sound, category, 0.6f, 1);
    }

    public void playSound(@Nonnull String sound, @Nonnull SoundCategory category) {
        playSound(sound, category, 0.6f, 1);
    }

    public void playSound(@Nonnull Sound sound, @Nonnull SoundCategory category, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, category, volume, pitch);
    }

    public void playSound(@Nonnull String sound, @Nonnull SoundCategory category, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, category, volume, pitch);
    }

    public void playSound(@Nonnull Location location, @Nonnull Sound sound, @Nonnull SoundCategory category, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, category, volume, pitch);
    }

    public void playSound(@Nonnull Location location, @Nonnull String sound, @Nonnull SoundCategory category, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, category, volume, pitch);
    }

    public void stopSound(@Nonnull Sound sound) {
        getPlayer().bukkit().stopSound(sound, SoundCategory.MASTER);
    }

    public void stopSound(@Nonnull String sound) {
        getPlayer().bukkit().stopSound(sound, SoundCategory.MASTER);
    }

    public void stopSound(@Nonnull Sound sound, @Nonnull SoundCategory category) {
        getPlayer().bukkit().stopSound(sound, category);
    }

    public void stopSound(@Nonnull String sound, @Nonnull SoundCategory category) {
        getPlayer().bukkit().stopSound(sound, category);
    }

    public void playPufferfishSting() {
        GameStateChangePacket.create(GameStateChangePacket.PUFFER_FISH_STING).send(getPlayer());
    }
}
