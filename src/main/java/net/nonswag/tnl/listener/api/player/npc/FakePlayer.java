package net.nonswag.tnl.listener.api.player.npc;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.utils.StringUtil;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.event.FakePlayerEvent;
import net.nonswag.tnl.listener.api.packets.outgoing.AnimatePacket;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityStatusPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.PlayerInfoRemovePacket;
import net.nonswag.tnl.listener.api.packets.outgoing.PlayerInfoUpdatePacket;
import net.nonswag.tnl.listener.api.player.GameProfile;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.Skin;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@Setter
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FakePlayer {
    private static final Logger logger = LoggerFactory.getLogger(FakePlayer.class);

    @Getter
    private static final List<FakePlayer> fakePlayers = new ArrayList<>();

    private final TNLEntityPlayer player;
    private String name;
    private Location location;
    @Accessors(fluent = true)
    private Consumer<InteractEvent> onInteract = event -> {
    };
    @Accessors(fluent = true)
    private Predicate<TNLPlayer> canSee = player -> true;

    public FakePlayer(String name, Location location) {
        this(name, location, (Skin) null);
    }

    public FakePlayer(String name, Location location, @Nullable Skin skin) {
        this(name, location, skin, new UUID(new Random().nextLong(), 0));
    }

    public FakePlayer(String name, Location location, UUID uuid) {
        this(name, location, null, uuid);
    }

    public FakePlayer(String name, Location location, @Nullable Skin skin, UUID uuid) {
        this.location = location;
        this.name = name;
        String id = "[NPC] %s".formatted(StringUtil.random(10));
        this.player = TNLEntityPlayer.create(getLocation(), new GameProfile(uuid, id, skin));
        getPlayer().setPing(-42);
    }

    public FakePlayer(String name, Location location, String skin) {
        this(name, location, Skin.getSkin(skin));
        logger.warn("Please use the properties <'" + getPlayer().getGameProfile().getSkin() + "'> instead of the name '" + player + "'>");
    }

    public void setName(String name) {
        this.name = name;
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().update(this, name));
    }

    public FakePlayer setLocation(Location location) {
        this.location = location;
        getPlayer().setLocation(location);
        return this;
    }

    public FakePlayer sendStatus(TNLPlayer receiver, EntityStatusPacket.Status status) {
        EntityStatusPacket.create(getPlayer().getEntityId(), status).send(receiver);
        return this;
    }

    public FakePlayer playAnimation(TNLPlayer receiver, AnimatePacket.Animation animation) {
        AnimatePacket.create(getPlayer().getEntityId(), animation).send(receiver);
        return this;
    }

    public FakePlayer hideTabListName(TNLPlayer receiver) {
        PlayerInfoRemovePacket.create(getPlayer().getGameProfile().getUniqueId()).send(receiver);
        return this;
    }

    public FakePlayer showTabListName(TNLPlayer receiver) {
        PlayerInfoUpdatePacket.Entry entry = new PlayerInfoUpdatePacket.Entry(getPlayer());
        PlayerInfoUpdatePacket.create(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry).send(receiver);
        return this;
    }

    public FakePlayer register() {
        if (fakePlayers.contains(this)) throw new IllegalStateException("NPC is already registered");
        fakePlayers.add(this);
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().spawn(this));
        return this;
    }

    public FakePlayer unregister() {
        if (!fakePlayers.contains(this)) throw new IllegalStateException("NPC is not registered");
        fakePlayers.remove(this);
        Listener.getOnlinePlayers().forEach(all -> all.npcFactory().deSpawn(this));
        return this;
    }

    @Getter
    public static class InteractEvent extends FakePlayerEvent {

        private final Hand.Side handSide;

        public InteractEvent(TNLPlayer player, FakePlayer fakePlayer, Hand.Side handSide) {
            super(fakePlayer, player);
            this.handSide = handSide;
        }
    }
}
