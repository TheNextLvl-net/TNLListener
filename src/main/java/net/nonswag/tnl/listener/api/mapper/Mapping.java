package net.nonswag.tnl.listener.api.mapper;

import lombok.Getter;
import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.utils.LinuxUtil;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.enchantment.Enchant;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.entity.TNLFallingBlock;
import net.nonswag.tnl.listener.api.item.ItemHelper;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.logger.LogManager;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;
import net.nonswag.tnl.listener.api.packets.*;
import net.nonswag.tnl.listener.api.packets.listener.PacketReader;
import net.nonswag.tnl.listener.api.packets.listener.PacketWriter;
import net.nonswag.tnl.listener.api.player.GameProfile;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginHelper;
import net.nonswag.tnl.listener.api.plugin.PluginUpdate;
import net.nonswag.tnl.listener.api.plugin.Updatable;
import net.nonswag.tnl.listener.api.version.Version;
import net.nonswag.tnl.listener.api.world.WorldHelper;
import net.nonswag.tnl.listener.events.mapping.MappingDisableEvent;
import net.nonswag.tnl.listener.events.mapping.MappingEnableEvent;
import net.nonswag.tnl.listener.events.mapping.MappingLoadEvent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Mapping extends PluginBuilder implements Updatable {

    @Nullable
    private static Mapping instance = null;
    @Nullable
    private Info info = null;
    @Getter
    @Nonnull
    private final File file;
    @Nullable
    protected Packets packets = null;
    @Nullable
    protected PluginHelper pluginHelper = null;
    @Nullable
    protected ItemHelper itemHelper = null;
    @Nullable
    protected PluginUpdate updater = null;
    @Nullable
    protected LogManager logManager = null;

    public Mapping(@Nonnull File file) {
        super(Mapping.class, net.nonswag.tnl.listener.Listener.getInstance());
        this.file = file;
        setName(info().name());
        setDescription(info().description());
        setWebsite(info().website());
        setAuthors(info().authors());
    }

    @Override
    public final void onLoad() {
        new MappingLoadEvent().call();
        super.onLoad();
    }

    @Override
    public final void onEnable() {
        new MappingEnableEvent().call();
        super.onEnable();
        getEventManager().registerListener(new Listener() {
            @EventHandler
            public void onDisable(@Nonnull MappingDisableEvent event) {
                File[] files = getUpdateFolder().listFiles((file, name) -> name.endsWith(".jar"));
                if (files == null) return;
                for (File file : files) {
                    LinuxUtil.Suppressed.runShellCommand("mv " + file.getAbsolutePath() + " .", getDataFolder());
                }
            }
        });
        File plugins = Bukkit.getUpdateFolderFile().getAbsoluteFile();
        File mappings = getUpdateFolder().getAbsoluteFile();
        FileHelper.create(plugins);
        FileHelper.createLink(new File(mappings, "plugins"), plugins);
        FileHelper.createLink(new File(plugins, "mappings"), mappings);
    }

    @Override
    public final void onDisable() {
        new MappingDisableEvent().call();
        super.onDisable();
        try {
            if (getClass().getClassLoader() instanceof URLClassLoader loader) loader.close();
        } catch (IOException e) {
            Logger.error.println("Couldn't close URLClassLoader of mapping <'" + getName() + "'>", e);
        }
    }

    @Nonnull
    @Override
    @Deprecated
    public final Mapping register() {
        return this;
    }

    @Nonnull
    @Override
    @Deprecated
    public final Mapping unregister() {
        return this;
    }

    @Override
    public final boolean isRegistered() {
        return true;
    }

    @Nonnull
    @Override
    public final PluginUpdate getUpdater() {
        return updater == null ? updater = new PluginUpdate(this) : updater;
    }

    @Nonnull
    @net.nonswag.core.api.annotation.Info("The version this mapping is made for")
    public abstract Version getVersion();

    @Nonnull
    public abstract TNLPlayer createPlayer(@Nonnull Player player);

    @Nonnull
    public abstract TNLItem createItem(@Nonnull ItemStack itemStack);

    @Nonnull
    public abstract TNLBossBar createBossBar(@Nonnull String id, @Nonnull String text, @Nonnull BarColor color, @Nonnull BarStyle style, double progress, @Nonnull BarFlag... barFlags);

    @Nonnull
    public abstract TNLFallingBlock createFallingBlock(@Nonnull Location location, @Nonnull Material type);

    @Nonnull
    public abstract TNLArmorStand createArmorStand(@Nonnull World world, double x, double y, double z, float yaw, float pitch);

    @Nonnull
    public abstract TNLEntityPlayer createEntityPlayer(@Nonnull World world, double x, double y, double z, float yaw, float pitch, @Nonnull GameProfile profile);

    @Nonnull
    public abstract Enchant createEnchant(@Nonnull NamespacedKey key, @Nonnull String name, @Nonnull EnchantmentTarget target);

    @Nonnull
    public abstract ItemHelper itemHelper();

    @Nonnull
    public abstract PluginHelper pluginHelper();

    @Nonnull
    public abstract WorldHelper worldHelper();

    @Nonnull
    public abstract Packets packets();

    @Nonnull
    public abstract LogManager logManager();

    public abstract static class Packets {

        @Nonnull
        public abstract BlockBreakAnimationPacket blockBreakAnimationPacket(@Nonnull BlockLocation location, int state);

        @Nonnull
        public abstract BossBarPacket bossBarPacket(@Nonnull BossBarPacket.Action action, @Nonnull BossBar bossBar);

        @Nonnull
        public abstract CameraPacket cameraPacket(int targetId);

        @Nonnull
        public abstract ChatPacket chatPacket(@Nonnull String message, @Nonnull ChatPacket.Type type, @Nonnull UUID sender);

        @Nonnull
        public abstract CloseWindowPacket closeWindowPacket(int windowId);

        @Nonnull
        public abstract CooldownPacket cooldownPacket(@Nonnull Material item, int cooldown);

        @Nonnull
        public abstract CustomPayloadPacket customPayloadPacket(@Nonnull String channel, @Nonnull byte[]... bytes);

        @Nonnull
        public abstract EntityAnimationPacket entityAnimationPacket(int entityId, @Nonnull EntityAnimationPacket.Animation animation);

        @Nonnull
        public abstract EntityAttachPacket entityAttachPacket(int holderId, int leashedId);

        @Nonnull
        public abstract EntityDestroyPacket entityDestroyPacket(int... destroyIds);

        @Nonnull
        public abstract EntityEquipmentPacket entityEquipmentPacket(int entityId, @Nonnull HashMap<SlotType, ItemStack> equipment);

        @Nonnull
        public abstract GameStateChangePacket gameStateChangePacket(@Nonnull GameStateChangePacket.Identifier identifier, float state);

        @Nonnull
        public abstract EntityStatusPacket entityStatusPacket(int entityId, @Nonnull EntityStatusPacket.Status status);

        @Nonnull
        public abstract EntitySpawnPacket entitySpawnPacket(@Nonnull Entity entity);

        @Nonnull
        public abstract <W> EntityMetadataPacket<W> entityMetadataPacket(int entityId, @Nonnull W dataWatcher, boolean updateAll);

        @Nonnull
        public abstract <W> EntityMetadataPacket<W> entityMetadataPacket(@Nonnull Entity entity, boolean updateAll);

        @Nonnull
        public abstract EntityHeadRotationPacket entityHeadRotationPacket(int entityId, float yaw);

        @Nonnull
        public abstract EntityBodyRotationPacket entityBodyRotationPacket(int entityId, float rotation);

        @Nonnull
        public abstract EntityTeleportPacket entityTeleportPacket(int entityId, @Nonnull Position position);

        @Nonnull
        public abstract EntityVelocityPacket entityVelocityPacket(int entityId, @Nonnull Vector vector);

        @Nonnull
        public abstract LivingEntitySpawnPacket livingEntitySpawnPacket(@Nonnull LivingEntity entity);

        @Nonnull
        public abstract MapChunkPacket mapChunkPacket(@Nonnull Chunk chunk, int section);

        @Nonnull
        public abstract MountPacket mountPacket(int holderId, int[] mounts);

        @Nonnull
        public abstract NamedEntitySpawnPacket namedEntitySpawnPacket(@Nonnull HumanEntity human);

        @Nonnull
        public abstract OpenSignPacket openSignPacket(@Nonnull BlockLocation location);

        @Nonnull
        public abstract OpenWindowPacket openWindowPacket(int windowId, @Nonnull OpenWindowPacket.Type type, @Nonnull String title);

        @Nonnull
        public abstract PlayerInfoPacket playerInfoPacket(@Nonnull Player player, @Nonnull PlayerInfoPacket.Action action);

        @Nonnull
        public abstract SetSlotPacket setSlotPacket(@Nonnull SetSlotPacket.Inventory inventory, int slot, @Nullable ItemStack itemStack);

        @Nonnull
        public abstract TitlePacket titlePacket(@Nonnull TitlePacket.Action action, @Nullable String text, int timeIn, int timeStay, int timeOut);

        @Nonnull
        public abstract UpdateTimePacket updateTimePacket(long age, long timestamp, boolean cycle);

        @Nonnull
        public abstract WindowDataPacket windowDataPacket(int windowId, int property, int value);

        @Nonnull
        public abstract WindowItemsPacket windowItemsPacket(int windowId, @Nonnull List<ItemStack> items);

        @Nonnull
        public abstract WorldBorderPacket worldBorderPacket(@Nonnull VirtualBorder virtualBorder, @Nonnull WorldBorderPacket.Action action);

        @Nonnull
        public abstract ResourcePackPacket resourcePackPacket(@Nonnull String url, @Nullable String hash, @Nullable String prompt, boolean required);

        public abstract void registerPacketReader(@Nonnull PacketReader reader);

        public abstract void registerPacketWriter(@Nonnull PacketWriter writer);
    }

    @Nonnull
    public final Info info() {
        if (info == null) info = getClass().getAnnotation(Info.class);
        if (info == null) throw new MappingError("Mappings must have an @Info annotation");
        return info;
    }

    @Nonnull
    @Override
    public File getDataFolder() {
        return net.nonswag.tnl.listener.api.mapper.Loader.MAPPINGS_FOLDER;
    }

    @Nonnull
    public File getUpdateFolder() {
        return net.nonswag.tnl.listener.api.mapper.Loader.MAPPINGS_UPDATE_FOLDER;
    }

    @Nonnull
    public static Mapping get() {
        if (instance == null) throw new MappingError("No mapping found, make sure to install one");
        return instance;
    }

    static void setInstance(@Nonnull Mapping instance) {
        if (Mapping.instance != null) throw new MappingError("Can't load multiple mappings at once");
        Mapping.instance = instance;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {

        @Nonnull
        String id();

        @Nonnull
        String name();

        @Nonnull
        String[] authors() default {};

        @Nonnull
        String version() default "1.0";

        @Nonnull
        String website() default "https://www.thenextlvl.net";

        @Nonnull
        String description() default "";
    }
}
