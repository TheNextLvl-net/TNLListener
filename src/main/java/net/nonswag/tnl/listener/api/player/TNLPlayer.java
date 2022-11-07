package net.nonswag.tnl.listener.api.player;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.message.key.Key;
import net.nonswag.core.api.platform.PlatformPlayer;
import net.nonswag.core.api.storage.VirtualStorage;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.gamemode.Gamemode;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.mods.ModPlayer;
import net.nonswag.tnl.listener.api.mods.labymod.LabyPlayer;
import net.nonswag.tnl.listener.api.packets.injection.Injection;
import net.nonswag.tnl.listener.api.packets.outgoing.GameStateChangePacket;
import net.nonswag.tnl.listener.api.packets.outgoing.InitializeBorderPacket;
import net.nonswag.tnl.listener.api.player.manager.*;
import net.nonswag.tnl.listener.api.registrations.RegistrationManager;
import net.nonswag.tnl.listener.api.version.Version;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Setter
public abstract class TNLPlayer implements CommandSource, PlatformPlayer, TNLEntityPlayer {

    protected static final HashMap<Player, TNLPlayer> players = new HashMap<>();

    private final VirtualStorage virtualStorage = new VirtualStorage();
    @Getter
    protected Version version = Listener.getVersion();
    @Getter
    private final String realName;
    private Player player;
    @Getter
    @Deprecated
    private final List<Injection<?>> injections = new ArrayList<>();
    private final HashMap<Class<? extends Manager>, Manager> managers = new HashMap<>();
    private final HashMap<Class<? extends ModPlayer>, ModPlayer> modHandlers = new HashMap<>();

    protected TNLPlayer(Player player) {
        this.player = player;
        this.realName = player.getName();
    }

    public Player bukkit() {
        return player;
    }

    public TNLPlayer bukkit(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public String getName() {
        return bukkit().getName();
    }

    public abstract void setName(Plugin plugin, String name);

    public UUID getUniqueId() {
        return bukkit().getUniqueId();
    }

    public abstract GameProfile getGameProfile();

    @Override
    public int getEntityId() {
        return bukkit().getEntityId();
    }

    public boolean isOnline() {
        return bukkit().isOnline();
    }

    public boolean hasPlayedBefore() {
        return bukkit().hasPlayedBefore();
    }

    public synchronized VirtualStorage getVirtualStorage() {
        return virtualStorage;
    }

    public Gamemode getGamemode() {
        return Gamemode.cast(bukkit().getGameMode());
    }

    public void setGamemode(Gamemode gamemode) {
        Bootstrap.getInstance().sync(() -> {
            bukkit().setGameMode(gamemode.bukkit());
            if (!gamemode.isUnknown()) return;
            GameStateChangePacket.create(GameStateChangePacket.CHANGE_GAMEMODE, gamemode.getId()).send(this);
        });
    }

    @Override
    public abstract int getPing();

    @Override
    public abstract void setPing(int ping);

    @Deprecated
    public <P> void inject(Injection<P> injection) {
        getInjections().add(injection);
    }

    @Deprecated
    public <P> void uninject(Injection<P> injection) {
        getInjections().remove(injection);
    }

    @Deprecated
    @SuppressWarnings("WhileLoopReplaceableByForEach")
    protected boolean handleInjections(Object packet) {
        boolean success = true;
        Iterator<Injection<?>> iterator = getInjections().iterator();
        while (iterator.hasNext()) {
            Injection<?> injection = iterator.next();
            if (!injection.getPacketClass().equals(packet.getClass())) continue;
            Injection.After after = injection.getAfter();
            try {
                if (((Injection<Object>) injection).run(this, packet) && after != null) after.run(this);
            } catch (Throwable t) {
                injection.handle(t);
            } finally {
                if (injection.isCancelled()) success = false;
            }
        }
        return success;
    }

    public boolean delay(String id, long millis) {
        String key = UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)).toString();
        Long delay = getVirtualStorage().get(key);
        if (delay != null && delay >= System.currentTimeMillis()) return false;
        getVirtualStorage().put(key, System.currentTimeMillis() + millis);
        return true;
    }

    public void setClientBorder(VirtualBorder border) {
        InitializeBorderPacket.create(border).send(this);
    }

    public abstract PermissionManager permissionManager();

    public abstract DataManager data();

    public abstract LabyPlayer labymod();

    public abstract SoundManager soundManager();

    public abstract NPCFactory npcFactory();

    public abstract HologramManager hologramManager();

    public abstract Messenger messenger();

    public abstract ScoreboardManager scoreboardManager();

    public abstract InterfaceManager interfaceManager();

    public abstract WorldManager worldManager();

    public abstract EnvironmentManager environmentManager();

    public abstract HealthManager healthManager();

    public abstract CombatManager combatManager();

    public abstract SkinManager skinManager();

    public abstract InventoryManager inventoryManager();

    public abstract DebugManager debugManager();

    public abstract AttributeManager attributeManager();

    public abstract MetaManager metaManager();

    public abstract EffectManager effectManager();

    public abstract AbilityManager abilityManager();

    public abstract ServerManager serverManager();

    public abstract CinematicManger cinematicManger();

    public abstract ParticleManager particleManager();

    public abstract TitleManager titleManager();

    public abstract BossBarManager bossBarManager();

    public abstract CooldownManager cooldownManager();

    public abstract ResourceManager resourceManager();

    public abstract Pipeline pipeline();

    public <M extends Manager> M getManager(Class<M> clazz) {
        if (managers.containsKey(clazz)) return (M) managers.get(clazz);
        var manager = RegistrationManager.getManager(clazz);
        if (manager == null) throw new NullPointerException("Manager not found: " + clazz.getName());
        managers.put(clazz, manager.get(this));
        return getManager(clazz);
    }

    public <M extends ModPlayer> M getModHandler(Class<M> clazz) {
        if (modHandlers.containsKey(clazz)) return (M) modHandlers.get(clazz);
        var modHandler = RegistrationManager.getModHandler(clazz);
        if (modHandler == null) throw new NullPointerException("Mod Handler not found: " + clazz.getName());
        modHandlers.put(clazz, modHandler.get(this));
        return getModHandler(clazz);
    }

    @Deprecated
    @Override
    public void setLocation(Location location) {
        worldManager().teleport(location);
    }

    @Deprecated
    @Override
    public void setLocation(double x, double y, double z) {
        setLocation(x, y, z, 0, 0);
    }

    @Deprecated
    @Override
    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        worldManager().teleport(new Location(worldManager().getWorld(), x, y, z, yaw, pitch));
    }

    @Deprecated
    @Override
    public void setItem(SlotType slot, TNLItem item) {
        inventoryManager().getInventory().setItem(slot.bukkit(), item);
    }

    @Deprecated
    @Override
    public void setGlowing(boolean glowing) {
        abilityManager().setGlowing(glowing);
    }

    @Deprecated
    @Override
    public void setCapeVisibility(boolean visible) {
        skinManager().setCapeVisibility(visible);
    }

    @Deprecated
    @Override
    public boolean getCapeVisibility() {
        return skinManager().getCapeVisibility();
    }

    @Deprecated
    @Override
    public boolean hasPermission(String permission) {
        return permissionManager().hasPermission(permission);
    }

    @Deprecated
    @Override
    public void sendMessage(String... strings) {
        for (String string : strings) messenger().sendMessage(string);
    }

    @Deprecated
    @Override
    public void sendMessage(Key key, Placeholder... placeholders) {
        messenger().sendMessage(key, placeholders);
    }

    @Deprecated
    @Override
    public void sendMessage(Key key, PlatformPlayer platformPlayer, Placeholder... placeholders) {
        messenger().sendMessage(key, platformPlayer, placeholders);
    }

    @Override
    public int hashCode() {
        return bukkit().hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Nullable
    public static TNLPlayer cast(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) return cast(player);
        return null;
    }

    @Nullable
    public static TNLPlayer cast(UUID uniqueId) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) return cast(player);
        return null;
    }

    public static TNLPlayer cast(HumanEntity entity) {
        return cast((Player) entity);
    }

    @Nullable
    public static TNLPlayer cast(@Nullable Entity entity) {
        if (entity instanceof Player player) return cast(player);
        return null;
    }

    @Nullable
    public static TNLPlayer cast(@Nullable CommandSender sender) {
        if (sender instanceof Player player) return cast(player);
        return null;
    }

    public static TNLPlayer cast(Player player, boolean inject) throws IllegalStateException {
        TNLPlayer tnlPlayer = null;
        if (!players.containsKey(player)) players.put(player, tnlPlayer = Mapping.get().createPlayer(player));
        if (!inject && !players.get(player).pipeline().isInjected()) {
            if (tnlPlayer != null) tnlPlayer.pipeline().disconnect("§cAn internal error occurred");
            else Logger.error.println("Failed to inject player, this could cause severe problems");
        } else if (inject && players.get(player).pipeline().isInjected()) {
            throw new IllegalStateException("Player is already injected");
        }
        return players.get(player);
    }

    public static TNLPlayer cast(Player player) {
        return cast(player, false);
    }

    @Nullable
    public static TNLPlayer nullable(@Nullable Player player) {
        return player == null ? null : cast(player);
    }
}
