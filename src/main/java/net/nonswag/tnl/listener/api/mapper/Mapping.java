package net.nonswag.tnl.listener.api.mapper;

import lombok.Getter;
import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.utils.LinuxUtil;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.enchantment.Enchant;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.entity.TNLFallingBlock;
import net.nonswag.tnl.listener.api.item.ItemHelper;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.logger.LogManager;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;
import net.nonswag.tnl.listener.api.packets.incoming.Incoming;
import net.nonswag.tnl.listener.api.packets.outgoing.Outgoing;
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
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URLClassLoader;

public abstract class Mapping extends PluginBuilder implements Updatable {

    @Nullable
    private static Mapping instance = null;
    @Nullable
    private Info info = null;
    @Getter
    private final File file;
    @Nullable
    protected PacketManager packetManager = null;
    @Nullable
    protected PluginHelper pluginHelper = null;
    @Nullable
    protected ItemHelper itemHelper = null;
    @Nullable
    protected PluginUpdate updater = null;
    @Nullable
    protected LogManager logManager = null;

    public Mapping(File file) {
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
            public void onDisable(MappingDisableEvent event) {
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

    @Override
    @Deprecated
    public final Mapping register() {
        return this;
    }

    @Override
    @Deprecated
    public final Mapping unregister() {
        return this;
    }

    @Override
    public final boolean isRegistered() {
        return true;
    }

    @Override
    public final PluginUpdate getUpdater() {
        return updater == null ? updater = new PluginUpdate(this) : updater;
    }

    @net.nonswag.core.api.annotation.Info("The version this mapping is made for")
    public abstract Version getVersion();

    public abstract TNLPlayer createPlayer(Player player);

    public abstract TNLItem createItem(ItemStack itemStack);

    public abstract TNLBossBar createBossBar(String id, String text, BarColor color, BarStyle style, double progress, BarFlag... barFlags);

    public abstract TNLFallingBlock createFallingBlock(Location location, Material type);

    public abstract TNLArmorStand createArmorStand(World world, double x, double y, double z, float yaw, float pitch);

    public abstract TNLEntityPlayer createEntityPlayer(World world, double x, double y, double z, float yaw, float pitch, GameProfile profile);

    public abstract Enchant createEnchant(NamespacedKey key, String name, EnchantmentTarget target);

    public abstract ItemHelper itemHelper();

    public abstract PluginHelper pluginHelper();

    public abstract WorldHelper worldHelper();

    public abstract LogManager logManager();

    public abstract PacketManager packetManager();

    public interface PacketManager {

        Outgoing outgoing();

        Incoming incoming();
    }

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

    public File getUpdateFolder() {
        return net.nonswag.tnl.listener.api.mapper.Loader.MAPPINGS_UPDATE_FOLDER;
    }

    public static Mapping get() {
        if (instance == null) throw new MappingError("No mapping found, make sure to install one");
        return instance;
    }

    static void setInstance(Mapping instance) {
        if (Mapping.instance != null) throw new MappingError("Can't load multiple mappings at once");
        Mapping.instance = instance;
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {

        String id();

        String name();

        String[] authors() default {};

        String version() default "1.0";

        String website() default "https://www.thenextlvl.net";

        String description() default "";
    }
}
