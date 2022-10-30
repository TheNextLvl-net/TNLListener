package net.nonswag.tnl.listener.api.player.npc;

import lombok.Getter;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.utils.StringUtil;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.event.FakePlayerEvent;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityAnimationPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityStatusPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.PlayerInfoPacket;
import net.nonswag.tnl.listener.api.player.GameProfile;
import net.nonswag.tnl.listener.api.player.Skin;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FakePlayer {

    @Getter
    @Nonnull
    private static final List<FakePlayer> fakePlayers = new ArrayList<>();

    @Getter
    @Nonnull
    private final TNLEntityPlayer player;
    @Getter
    @Nonnull
    private String name;
    @Getter
    @Nonnull
    private Location location;
    @Nonnull
    private Consumer<InteractEvent> onInteract = event -> {
    };
    @Nonnull
    private Predicate<TNLPlayer> canSee = player -> true;

    public FakePlayer(@Nonnull String name, @Nonnull Location location) {
        this(name, location, (Skin) null);
    }

    public FakePlayer(@Nonnull String name, @Nonnull Location location, @Nullable Skin skin) {
        this(name, location, skin, new UUID(new Random().nextLong(), 0));
    }

    public FakePlayer(@Nonnull String name, @Nonnull Location location, @Nonnull UUID uuid) {
        this(name, location, null, uuid);
    }

    public FakePlayer(@Nonnull String name, @Nonnull Location location, @Nullable Skin skin, @Nonnull UUID uuid) {
        this.location = location;
        this.name = name;
        String id = "[NPC] %s".formatted(StringUtil.random(10));
        this.player = TNLEntityPlayer.create(getLocation(), new GameProfile(uuid, id, skin));
        getPlayer().setPing(-42);
    }

    public FakePlayer(@Nonnull String name, @Nonnull Location location, @Nonnull String skin) {
        this(name, location, Skin.getSkin(skin));
        Logger.warn.println("Please use the properties <'" + getPlayer().getGameProfile().getSkin() + "'> instead of the name '" + player + "'>");
    }

    public void setName(@Nonnull String name) {
        this.name = name;
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().update(this, name));
    }

    @Nonnull
    public Consumer<InteractEvent> onInteract() {
        return onInteract;
    }

    @Nonnull
    public Predicate<TNLPlayer> canSee() {
        return canSee;
    }

    @Nonnull
    public FakePlayer onInteract(@Nonnull Consumer<InteractEvent> onInteract) {
        this.onInteract = onInteract;
        return this;
    }

    @Nonnull
    public FakePlayer canSee(@Nonnull Predicate<TNLPlayer> canSee) {
        this.canSee = canSee;
        return this;
    }

    @Nonnull
    public FakePlayer setLocation(@Nonnull Location location) {
        this.location = location;
        getPlayer().setLocation(location);
        return this;
    }

    @Nonnull
    public FakePlayer sendStatus(@Nonnull TNLPlayer receiver, @Nonnull EntityStatusPacket.Status status) {
        EntityStatusPacket.create(getPlayer().getEntityId(), status).send(receiver);
        return this;
    }

    @Nonnull
    public FakePlayer playAnimation(@Nonnull TNLPlayer receiver, @Nonnull EntityAnimationPacket.Animation animation) {
        EntityAnimationPacket.create(getPlayer().getEntityId(), animation).send(receiver);
        return this;
    }

    @Nonnull
    public FakePlayer hideTabListName(@Nonnull TNLPlayer receiver) {
        PlayerInfoPacket.create(getPlayer(), PlayerInfoPacket.Action.REMOVE_PLAYER).send(receiver);
        return this;
    }

    @Nonnull
    public FakePlayer showTabListName(@Nonnull TNLPlayer receiver) {
        PlayerInfoPacket.create(getPlayer(), PlayerInfoPacket.Action.ADD_PLAYER).send(receiver);
        return this;
    }

    @Nonnull
    public FakePlayer register() {
        if (fakePlayers.contains(this)) throw new IllegalStateException("NPC is already registered");
        fakePlayers.add(this);
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().spawn(this));
        return this;
    }

    @Nonnull
    public FakePlayer unregister() {
        if (!fakePlayers.contains(this)) throw new IllegalStateException("NPC is not registered");
        fakePlayers.remove(this);
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().deSpawn(this));
        return this;
    }

    @Getter
    public static class InteractEvent extends FakePlayerEvent {

        @Nonnull
        private final Type type;

        public InteractEvent(@Nonnull TNLPlayer player, @Nonnull FakePlayer fakePlayer, @Nonnull Type type) {
            super(fakePlayer, player);
            this.type = type;
        }

        public enum Type {
            LEFT_CLICK,
            RIGHT_CLICK
        }
    }
}
