package net.nonswag.tnl.listener.api.player;

import lombok.Getter;
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
import net.nonswag.tnl.listener.api.packets.GameStateChangePacket;
import net.nonswag.tnl.listener.api.packets.WorldBorderPacket;
import net.nonswag.tnl.listener.api.packets.injection.Injection;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class TNLPlayer implements CommandSource, PlatformPlayer, TNLEntityPlayer {

    @Nonnull
    protected static final HashMap<Player, TNLPlayer> players = new HashMap<>();

    @Nullable
    protected PermissionManager permissionManager;
    @Nullable
    protected DataManager data;
    @Nullable
    protected LabyPlayer labymod;
    @Nullable
    protected SoundManager soundManager;
    @Nullable
    protected NPCFactory npcFactory;
    @Nullable
    protected HologramManager hologramManager;
    @Nullable
    protected Messenger messenger;
    @Nullable
    protected ScoreboardManager scoreboardManager;
    @Nullable
    protected InterfaceManager interfaceManager;
    @Nullable
    protected WorldManager worldManager;
    @Nullable
    protected EnvironmentManager environmentManager;
    @Nullable
    protected HealthManager healthManager;
    @Nullable
    protected CombatManager combatManager;
    @Nullable
    protected SkinManager skinManager;
    @Nullable
    protected InventoryManager inventoryManager;
    @Nullable
    protected DebugManager debugManager;
    @Nullable
    protected AttributeManager attributeManager;
    @Nullable
    protected MetaManager metaManager;
    @Nullable
    protected EffectManager effectManager;
    @Nullable
    protected AbilityManager abilityManager;
    @Nullable
    protected ServerManager serverManager;
    @Nullable
    protected CinematicManger cinematicManger;
    @Nullable
    protected ParticleManager particleManager;
    @Nullable
    protected TitleManager titleManager;
    @Nullable
    protected BossBarManager bossBarManager;
    @Nullable
    protected CooldownManager cooldownManager;
    @Nullable
    protected ResourceManager resourceManager;
    @Nullable
    protected Pipeline pipeline;

    @Nonnull
    private final VirtualStorage virtualStorage = new VirtualStorage();
    @Getter
    @Nonnull
    protected Version version = Listener.getVersion();
    @Getter
    @Nonnull
    protected GameProfile gameProfile;
    @Getter
    @Nonnull
    private final String realName;
    @Nonnull
    private Player player;
    @Getter
    @Nonnull
    private final List<Injection<?>> injections = new ArrayList<>();
    @Nonnull
    private final HashMap<Class<? extends Manager>, Manager> managers = new HashMap<>();
    @Nonnull
    private final HashMap<Class<? extends ModPlayer>, ModPlayer> modHandlers = new HashMap<>();

    protected TNLPlayer(@Nonnull Player player) {
        this.player = player;
        this.gameProfile = new GameProfile(player);
        this.realName = player.getName();
    }

    @Nonnull
    public Player bukkit() {
        return player;
    }

    @Nonnull
    public TNLPlayer bukkit(@Nonnull Player player) {
        this.player = player;
        return this;
    }

    @Nonnull
    @Override
    public String getName() {
        return bukkit().getName();
    }

    public abstract void setName(@Nonnull Plugin plugin, @Nonnull String name);

    @Nonnull
    public UUID getUniqueId() {
        return bukkit().getUniqueId();
    }

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

    @Nonnull
    public synchronized VirtualStorage getVirtualStorage() {
        return virtualStorage;
    }

    @Nonnull
    public Gamemode getGamemode() {
        return Gamemode.cast(bukkit().getGameMode());
    }

    public void setGamemode(@Nonnull Gamemode gamemode) {
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

    public <P> void inject(@Nonnull Injection<P> injection) {
        getInjections().add(injection);
    }

    public <P> void uninject(@Nonnull Injection<P> injection) {
        getInjections().remove(injection);
    }

    @SuppressWarnings("WhileLoopReplaceableByForEach")
    protected boolean handleInjections(@Nonnull Object packet) {
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

    public boolean delay(@Nonnull String id, long millis) {
        String key = UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)).toString();
        Long delay = getVirtualStorage().get(key);
        if (delay != null && delay >= System.currentTimeMillis()) return false;
        getVirtualStorage().put(key, System.currentTimeMillis() + millis);
        return true;
    }

    public void setClientBorder(@Nonnull VirtualBorder border) {
        Bootstrap.getInstance().sync(() -> {
            for (WorldBorderPacket.Action action : WorldBorderPacket.Action.values()) {
                WorldBorderPacket.create(border, action).send(this);
            }
        });
    }

    @Nonnull
    public abstract PermissionManager permissionManager();

    @Nonnull
    public abstract DataManager data();

    @Nonnull
    public abstract LabyPlayer labymod();

    @Nonnull
    public abstract SoundManager soundManager();

    @Nonnull
    public abstract NPCFactory npcFactory();

    @Nonnull
    public abstract HologramManager hologramManager();

    @Nonnull
    public abstract Messenger messenger();

    @Nonnull
    public abstract ScoreboardManager scoreboardManager();

    @Nonnull
    public abstract InterfaceManager interfaceManager();

    @Nonnull
    public abstract WorldManager worldManager();

    @Nonnull
    public abstract EnvironmentManager environmentManager();

    @Nonnull
    public abstract HealthManager healthManager();

    @Nonnull
    public abstract CombatManager combatManager();

    @Nonnull
    public abstract SkinManager skinManager();

    @Nonnull
    public abstract InventoryManager inventoryManager();

    @Nonnull
    public abstract DebugManager debugManager();

    @Nonnull
    public abstract AttributeManager attributeManager();

    @Nonnull
    public abstract MetaManager metaManager();

    @Nonnull
    public abstract EffectManager effectManager();

    @Nonnull
    public abstract AbilityManager abilityManager();

    @Nonnull
    public abstract ServerManager serverManager();

    @Nonnull
    public abstract CinematicManger cinematicManger();

    @Nonnull
    public abstract ParticleManager particleManager();

    @Nonnull
    public abstract TitleManager titleManager();

    @Nonnull
    public abstract BossBarManager bossBarManager();

    @Nonnull
    public abstract CooldownManager cooldownManager();

    @Nonnull
    public abstract ResourceManager resourceManager();

    @Nonnull
    public abstract Pipeline pipeline();

    public void setPermissionManager(@Nullable PermissionManager permissionManager) {
        if (this.permissionManager != null) this.permissionManager.override();
        this.permissionManager = permissionManager;
    }

    public void setData(@Nullable DataManager data) {
        if (this.data != null) this.data.override();
        this.data = data;
    }

    public void setLabymod(@Nullable LabyPlayer labymod) {
        if (this.labymod != null) this.labymod.override();
        this.labymod = labymod;
    }

    public void setSoundManager(@Nullable SoundManager soundManager) {
        if (this.soundManager != null) this.soundManager.override();
        this.soundManager = soundManager;
    }

    public void setNpcFactory(@Nullable NPCFactory npcFactory) {
        if (this.npcFactory != null) this.npcFactory.override();
        this.npcFactory = npcFactory;
    }

    public void setHologramManager(@Nullable HologramManager hologramManager) {
        if (this.hologramManager != null) this.hologramManager.override();
        this.hologramManager = hologramManager;
    }

    public void setMessenger(@Nullable Messenger messenger) {
        if (this.messenger != null) this.messenger.override();
        this.messenger = messenger;
    }

    public void setScoreboardManager(@Nullable ScoreboardManager scoreboardManager) {
        if (this.scoreboardManager != null) this.scoreboardManager.override();
        this.scoreboardManager = scoreboardManager;
    }

    public void setInterfaceManager(@Nullable InterfaceManager interfaceManager) {
        if (this.interfaceManager != null) this.interfaceManager.override();
        this.interfaceManager = interfaceManager;
    }

    public void setWorldManager(@Nullable WorldManager worldManager) {
        if (this.worldManager != null) this.worldManager.override();
        this.worldManager = worldManager;
    }

    public void setCombatManager(@Nullable CombatManager combatManager) {
        if (this.combatManager != null) this.combatManager.override();
        this.combatManager = combatManager;
    }

    public void setSkinManager(@Nullable SkinManager skinManager) {
        if (this.skinManager != null) this.skinManager.override();
        this.skinManager = skinManager;
    }

    public void setInventoryManager(@Nullable InventoryManager inventoryManager) {
        if (this.inventoryManager != null) this.inventoryManager.override();
        this.inventoryManager = inventoryManager;
    }

    public void setDebugManager(@Nullable DebugManager debugManager) {
        if (this.debugManager != null) this.debugManager.override();
        this.debugManager = debugManager;
    }

    public void setAttributeManager(@Nullable AttributeManager attributeManager) {
        if (this.attributeManager != null) this.attributeManager.override();
        this.attributeManager = attributeManager;
    }

    public void setMetaManager(@Nullable MetaManager metaManager) {
        if (this.metaManager != null) this.metaManager.override();
        this.metaManager = metaManager;
    }

    public void setEffectManager(@Nullable EffectManager effectManager) {
        if (this.effectManager != null) this.effectManager.override();
        this.effectManager = effectManager;
    }

    public void setAbilityManager(@Nullable AbilityManager abilityManager) {
        if (this.abilityManager != null) this.abilityManager.override();
        this.abilityManager = abilityManager;
    }

    public void setServerManager(@Nullable ServerManager serverManager) {
        if (this.serverManager != null) this.serverManager.override();
        this.serverManager = serverManager;
    }

    public void setCinematicManger(@Nullable CinematicManger cinematicManger) {
        if (this.cinematicManger != null) this.cinematicManger.override();
        this.cinematicManger = cinematicManger;
    }

    public void setParticleManager(@Nullable ParticleManager particleManager) {
        if (this.particleManager != null) this.particleManager.override();
        this.particleManager = particleManager;
    }

    public void setTitleManager(@Nullable TitleManager titleManager) {
        if (this.titleManager != null) this.titleManager.override();
        this.titleManager = titleManager;
    }

    public void setBossBarManager(@Nullable BossBarManager bossBarManager) {
        if (this.bossBarManager != null) this.bossBarManager.override();
        this.bossBarManager = bossBarManager;
    }

    public void setCooldownManager(@Nullable CooldownManager cooldownManager) {
        if (this.cooldownManager != null) this.cooldownManager.override();
        this.cooldownManager = cooldownManager;
    }

    public void setResourceManager(@Nullable ResourceManager resourceManager) {
        if (this.resourceManager != null) this.resourceManager.override();
        this.resourceManager = resourceManager;
    }

    public void setEnvironmentManager(@Nullable EnvironmentManager environmentManager) {
        if (this.environmentManager != null) this.environmentManager.override();
        this.environmentManager = environmentManager;
    }

    public void setHealthManager(@Nullable HealthManager healthManager) {
        if (this.healthManager != null) this.healthManager.override();
        this.healthManager = healthManager;
    }

    public void setPipeline(@Nullable Pipeline pipeline) {
        if (this.pipeline != null) this.pipeline.override();
        this.pipeline = pipeline;
    }

    @Nonnull
    public <M extends Manager> M getManager(@Nonnull Class<M> clazz) {
        if (managers.containsKey(clazz)) return (M) managers.get(clazz);
        var manager = RegistrationManager.getManager(clazz);
        if (manager == null) throw new NullPointerException("Manager not found: " + clazz.getName());
        managers.put(clazz, manager.get(this));
        return getManager(clazz);
    }

    @Nonnull
    public <M extends ModPlayer> M getModHandler(@Nonnull Class<M> clazz) {
        if (modHandlers.containsKey(clazz)) return (M) modHandlers.get(clazz);
        var modHandler = RegistrationManager.getModHandler(clazz);
        if (modHandler == null) throw new NullPointerException("Mod Handler not found: " + clazz.getName());
        modHandlers.put(clazz, modHandler.get(this));
        return getModHandler(clazz);
    }

    @Deprecated
    @Override
    public void setLocation(@Nonnull Location location) {
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
    public void setItem(@Nonnull SlotType slot, @Nonnull TNLItem item) {
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
    public boolean hasPermission(@Nonnull String permission) {
        return permissionManager().hasPermission(permission);
    }

    @Deprecated
    @Override
    public void sendMessage(@Nonnull String... strings) {
        for (String string : strings) messenger().sendMessage(string);
    }

    @Deprecated
    @Override
    public void sendMessage(@Nonnull Key key, @Nonnull Placeholder... placeholders) {
        messenger().sendMessage(key, placeholders);
    }

    @Deprecated
    @Override
    public void sendMessage(@Nonnull Key key, @Nonnull PlatformPlayer platformPlayer, @Nonnull Placeholder... placeholders) {
        messenger().sendMessage(key, platformPlayer, placeholders);
    }

    @Override
    public int hashCode() {
        return bukkit().hashCode();
    }

    @Nonnull
    @Override
    public String toString() {
        return getName();
    }

    @Nullable
    public static TNLPlayer cast(@Nonnull String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) return cast(player);
        return null;
    }

    @Nullable
    public static TNLPlayer cast(@Nonnull UUID uniqueId) {
        Player player = Bukkit.getPlayer(uniqueId);
        if (player != null) return cast(player);
        return null;
    }

    @Nonnull
    public static TNLPlayer cast(@Nonnull HumanEntity entity) {
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

    @Nonnull
    public static TNLPlayer cast(@Nonnull Player player, boolean inject) throws IllegalStateException {
        if (!players.containsKey(player)) players.put(player, Mapping.get().createPlayer(player));
        if (!inject && !players.get(player).pipeline().isInjected()) {
            Logger.error.println(new Exception("not injected right now, this could cause sirius problems"));
        } else if (inject && players.get(player).pipeline().isInjected()) {
            throw new IllegalStateException("Player is already injected");
        }
        return players.get(player);
    }

    @Nonnull
    public static TNLPlayer cast(@Nonnull Player player) {
        return cast(player, false);
    }

    @Nullable
    public static TNLPlayer nullable(@Nullable Player player) {
        return player == null ? null : cast(player);
    }
}
