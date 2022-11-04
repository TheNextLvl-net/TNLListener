package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.packets.outgoing.GameStateChangePacket;
import org.bukkit.*;

public abstract class SoundManager extends Manager {

    public void playNote(Location location, Instrument instrument, Note note) {
        getPlayer().bukkit().playNote(location, instrument, note);
    }

    public void playNote(Instrument instrument, Note note) {
        playNote(getPlayer().bukkit().getLocation(), instrument, note);
    }

    public void playSound(Sound sound) {
        playSound(sound, 0.6f, 1);
    }

    public void playSound(String sound) {
        playSound(sound, 0.6f, 1);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, volume, pitch);
    }

    public void playSound(String sound, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, volume, pitch);
    }

    public void playSound(Location location, Sound sound, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, volume, pitch);
    }

    public void playSound(Location location, String sound, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, volume, pitch);
    }

    public void playSound(Sound sound, SoundCategory category) {
        playSound(sound, category, 0.6f, 1);
    }

    public void playSound(String sound, SoundCategory category) {
        playSound(sound, category, 0.6f, 1);
    }

    public void playSound(Sound sound, SoundCategory category, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, category, volume, pitch);
    }

    public void playSound(String sound, SoundCategory category, float volume, float pitch) {
        playSound(getPlayer().bukkit().getLocation(), sound, category, volume, pitch);
    }

    public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, category, volume, pitch);
    }

    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch) {
        getPlayer().bukkit().playSound(location, sound, category, volume, pitch);
    }

    public void stopSound(Sound sound) {
        getPlayer().bukkit().stopSound(sound, SoundCategory.MASTER);
    }

    public void stopSound(String sound) {
        getPlayer().bukkit().stopSound(sound, SoundCategory.MASTER);
    }

    public void stopSound(Sound sound, SoundCategory category) {
        getPlayer().bukkit().stopSound(sound, category);
    }

    public void stopSound(String sound, SoundCategory category) {
        getPlayer().bukkit().stopSound(sound, category);
    }

    public void playPufferfishSting() {
        GameStateChangePacket.create(GameStateChangePacket.PUFFER_FISH_STING).send(getPlayer());
    }
}
