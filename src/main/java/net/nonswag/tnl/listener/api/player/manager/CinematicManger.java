package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.nonswag.core.api.object.Pair;
import net.nonswag.tnl.listener.api.cinematic.CinematicPlayException;
import net.nonswag.tnl.listener.api.cinematic.CinematicRecordException;
import net.nonswag.tnl.listener.api.cinematic.Recording;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.gamemode.Gamemode;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;

import java.util.List;

@Getter
public abstract class CinematicManger extends Manager {

    private boolean recording = false;
    private boolean playing = false;

    public void stopRecording() {
        this.recording = false;
    }

    public void stopPlaying() {
        this.playing = false;
    }

    public void play(Recording recording) throws CinematicPlayException {
        play(recording, (player, record) -> {
        });
    }

    public void play(Recording recording, Finished finished) throws CinematicPlayException {
        if (isPlaying()) throw new CinematicPlayException(recording, this, "Already playing a cinematic");
        if (isRecording()) throw new CinematicPlayException(recording, this, "Can't play a cinematic while recording");
        new Thread(() -> {
            List<Pair<Location, Long>> steps = recording.getSteps();
            if (!steps.isEmpty()) {
                Pair<Location, Long> step = steps.get(0);
                TNLArmorStand armorStand = TNLArmorStand.create(step.getKey());
                armorStand.setBasePlate(false);
                armorStand.setVisible(false);
                GameStateChangePacket.create(GameStateChangePacket.CHANGE_GAMEMODE, Gamemode.SPECTATOR.getId()).send(getPlayer());
                AddEntityPacket.create(armorStand.bukkit()).send(getPlayer());
                EntityMetadataPacket.create(armorStand.bukkit()).send(getPlayer());
                EntityHeadRotationPacket.create(armorStand.bukkit(), step.getKey().getYaw()).send(getPlayer());
                try {
                    this.playing = true;
                    Thread.sleep(step.getValue() != null ? step.getValue() : 50);
                    CameraPacket.create(armorStand.getEntityId()).send(getPlayer());
                    for (Pair<Location, Long> pair : steps) {
                        if (!getPlayer().isOnline() || !isPlaying()) break;
                        step = pair;
                        Thread.sleep(step.getValue() != null ? step.getValue() : 50);
                        TeleportEntityPacket.create(armorStand.bukkit(), new Position(step.getKey())).send(getPlayer());
                        EntityHeadRotationPacket.create(armorStand.getEntityId(), step.getKey().getYaw()).send(getPlayer());
                    }
                    Thread.sleep(step.getValue() != null ? step.getValue() : 50);
                    finished.success(getPlayer(), recording);
                } catch (Exception ignored) {
                    finished.failure(getPlayer(), recording);
                } finally {
                    RemoveEntitiesPacket.create(armorStand.getEntityId()).send(getPlayer());
                    CameraPacket.create(getPlayer().bukkit()).send(getPlayer());
                    GameStateChangePacket.create(GameStateChangePacket.CHANGE_GAMEMODE, getPlayer().getGamemode().getId()).send(getPlayer());
                    TeleportEntityPacket.create(getPlayer().bukkit()).send(getPlayer());
                    getPlayer().abilityManager().updateAbilities();
                    this.playing = false;
                    finished.general(getPlayer(), recording);
                }
            }
        }).start();
    }

    public void record(Recording recording) throws CinematicRecordException {
        record(recording, p -> true);
    }

    public void record(Recording recording, Circumstance circumstance) throws CinematicRecordException {
        record(recording, circumstance, (player, record) -> {
        });
    }

    public void record(Recording recording, Finished finished) throws CinematicRecordException {
        record(recording, p -> true, finished);
    }

    public void record(Recording recording, Circumstance circumstance, Finished finished) throws CinematicRecordException {
        if (isPlaying()) throw new CinematicRecordException(recording, this, "Can't record a cinematic while playing");
        if (isRecording()) throw new CinematicRecordException(recording, this, "Already recording a cinematic");
        new Thread(() -> {
            try {
                this.recording = true;
                while (getPlayer().isOnline() && isRecording() && circumstance.check(getPlayer())) {
                    if (!recording.getSteps().isEmpty()) {
                        Pair<Location, Long> last = recording.getSteps().get(recording.getSteps().size() - 1);
                        if (last.getKey().equals(getPlayer().worldManager().getLocation())) continue;
                    }
                    recording.addStep(getPlayer().worldManager().getLocation());
                    Thread.sleep(50);
                }
                finished.success(getPlayer(), recording);
            } catch (Exception ignored) {
                finished.failure(getPlayer(), recording);
            } finally {
                finished.general(getPlayer(), recording);
                this.recording = false;
            }
        }).start();
    }

    @FunctionalInterface
    public interface Finished {
        void general(TNLPlayer player, Recording recording);

        default void success(TNLPlayer player, Recording recording) {
        }

        default void failure(TNLPlayer player, Recording recording) {
        }
    }

    public interface Circumstance {
        boolean check(TNLPlayer player);
    }
}
