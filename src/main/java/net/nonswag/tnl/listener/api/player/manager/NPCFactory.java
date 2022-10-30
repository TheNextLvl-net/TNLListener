package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.listener.api.scheduler.Task;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class NPCFactory extends Manager {

    @Nonnull
    private final HashMap<Integer, FakePlayer> fakePlayers = new HashMap<>();
    @Nonnull
    private final HashMap<FakePlayer, Hologram> nameTags = new HashMap<>();
    @Nonnull
    private final List<Integer> loadedFakePlayers = new ArrayList<>();
    public static int LOADING_RANGE = 48;

    public boolean isLoaded(@Nonnull FakePlayer fakePlayer) {
        return loadedFakePlayers.contains(fakePlayer.getPlayer().getEntityId());
    }

    public void setLoaded(@Nonnull FakePlayer fakePlayer, boolean loaded) {
        loadedFakePlayers.remove((Object) fakePlayer.getPlayer().getEntityId());
        if (loaded) loadedFakePlayers.add(fakePlayer.getPlayer().getEntityId());
    }

    public void registerFakePlayer(@Nonnull FakePlayer fakePlayer) {
        fakePlayers.put(fakePlayer.getPlayer().getEntityId(), fakePlayer);
    }

    public void unregisterFakePlayer(@Nonnull FakePlayer fakePlayer) {
        fakePlayers.remove(fakePlayer.getPlayer().getEntityId());
        loadedFakePlayers.remove((Object) fakePlayer.getPlayer().getEntityId());
    }

    @Nullable
    public FakePlayer getFakePlayer(int id) {
        return fakePlayers.get(id);
    }

    @Nonnull
    public Collection<FakePlayer> getFakePlayers() {
        return fakePlayers.values();
    }

    public void spawn(@Nonnull FakePlayer fakePlayer) {
        if (show(fakePlayer, true)) registerFakePlayer(fakePlayer);
    }

    public void registerAll() {
        FakePlayer.getFakePlayers().forEach(fakePlayer -> {
            registerFakePlayer(fakePlayer);
            show(fakePlayer, true);
        });
    }

    public boolean show(@Nonnull FakePlayer fakePlayer, boolean checkDistance) {
        if (isLoaded(fakePlayer) || !fakePlayer.canSee().test(getPlayer())) return false;
        WorldManager manager = getPlayer().worldManager();
        if (!manager.getWorld().equals(fakePlayer.getLocation().getWorld())) return false;
        if (checkDistance && manager.getLocation().distance(fakePlayer.getLocation()) > LOADING_RANGE) return false;
        PlayerInfoPacket.create(fakePlayer.getPlayer(), PlayerInfoPacket.Action.ADD_PLAYER).send(getPlayer());
        NamedEntitySpawnPacket.create(fakePlayer.getPlayer()).send(getPlayer());
        EntityMetadataPacket.create(fakePlayer.getPlayer().bukkit()).send(getPlayer());
        EntityEquipmentPacket.create(fakePlayer.getPlayer().bukkit()).send(getPlayer());
        EntityHeadRotationPacket.create(fakePlayer.getPlayer().bukkit()).send(getPlayer());
        Bootstrap.getInstance().async(() -> {
            Task.sleep(1000);
            fakePlayer.playAnimation(getPlayer(), EntityAnimationPacket.Animation.SWING_HAND);
            Task.sleep(1000);
            fakePlayer.playAnimation(getPlayer(), EntityAnimationPacket.Animation.SWING_OFFHAND);
            Task.sleep(5000);
            fakePlayer.hideTabListName(getPlayer());
        });
        setLoaded(fakePlayer, true);
        getPlayer().scoreboardManager().updateTeam();
        update(fakePlayer, fakePlayer.getName());
        return true;
    }

    public void reSpawn(@Nonnull FakePlayer fakePlayer) {
        deSpawn(fakePlayer);
        spawn(fakePlayer);
    }

    public void deSpawn(@Nonnull FakePlayer fakePlayer) {
        hide(fakePlayer);
        unregisterFakePlayer(fakePlayer);
    }

    public void deSpawnAll() {
        new ArrayList<>(getFakePlayers()).forEach(this::deSpawn);
    }

    public void hide(@Nonnull FakePlayer fakePlayer) {
        if (!isLoaded(fakePlayer)) return;
        PlayerInfoPacket.create(fakePlayer.getPlayer(), PlayerInfoPacket.Action.REMOVE_PLAYER).send(getPlayer());
        EntityDestroyPacket.create(fakePlayer.getPlayer().getEntityId()).send(getPlayer());
        if (nameTags.containsKey(fakePlayer)) getPlayer().hologramManager().unload(nameTags.get(fakePlayer));
        nameTags.remove(fakePlayer);
        setLoaded(fakePlayer, false);
    }

    public void update(@Nonnull FakePlayer fakePlayer, @Nonnull Location location) {
        if (isLoaded(fakePlayer)) {
            if (!Objects.equals(location.getWorld(), fakePlayer.getLocation().getWorld())) hide(fakePlayer);
            else if (fakePlayer.getLocation().distance(location) > LOADING_RANGE) hide(fakePlayer);
        } else show(fakePlayer, true);
    }

    public void updateAll(@Nonnull Location location) {
        getFakePlayers().forEach(fakePlayer -> update(fakePlayer, location));
    }

    public void update(@Nonnull FakePlayer fakePlayer, @Nonnull String name) {
        if (name.isEmpty() || !isLoaded(fakePlayer) || !fakePlayer.canSee().test(getPlayer())) return;
        String id = fakePlayer.getPlayer().getGameProfile().getUniqueId().toString();
        Hologram hologram = new Hologram(id).canSee(player -> player.equals(getPlayer()));
        hologram.setLocation(fakePlayer.getLocation().clone().add(0, 1.78, 0));
        hologram.setLines(fakePlayer.getName());
        nameTags.put(fakePlayer, hologram);
        getPlayer().hologramManager().load(hologram);
    }
}
