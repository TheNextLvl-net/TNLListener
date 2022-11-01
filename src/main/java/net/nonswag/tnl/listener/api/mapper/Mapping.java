package net.nonswag.tnl.listener.api.mapper;

import lombok.Getter;
import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.utils.LinuxUtil;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.enchantment.Enchant;
import net.nonswag.tnl.listener.api.entity.TNLArmorStand;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.entity.TNLFallingBlock;
import net.nonswag.tnl.listener.api.item.ItemHelper;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.logger.LogManager;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;
import net.nonswag.tnl.listener.api.packets.incoming.PacketBuilder;
import net.nonswag.tnl.listener.api.packets.incoming.*;
import net.nonswag.tnl.listener.api.packets.outgoing.ChatPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.CustomPayloadPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.MoveVehiclePacket;
import net.nonswag.tnl.listener.api.packets.outgoing.ResourcePackPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import net.nonswag.tnl.listener.api.player.GameProfile;
import net.nonswag.tnl.listener.api.player.Hand;
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
import org.bukkit.block.structure.Mirror;
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
import java.time.Instant;
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
    protected PacketManager packetManager = null;
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
    public abstract LogManager logManager();

    @Nonnull
    public abstract PacketManager packetManager();

    public abstract static class PacketManager {

        @Nonnull
        public abstract Outgoing outgoing();

        @Nonnull
        public abstract Incoming incoming();

        public abstract static class Incoming {

            @Nonnull
            public abstract AcceptTeleportationPacket acceptTeleportationPacket(int id);

            @Nonnull
            public abstract BlockEntityTagQueryPacket blockEntityTagQueryPacket(int transactionId, @Nonnull BlockPosition position);

            @Nonnull
            public abstract ChangeDifficultyPacket changeDifficultyPacket(@Nonnull Difficulty difficulty);

            @Nonnull
            public abstract ChatAckPacket chatAckPacket(@Nonnull LastSeenMessages.Update lastSeenMessages);

            @Nonnull
            public abstract ChatCommandPacket chatCommandPacket(@Nonnull String command, @Nonnull Instant timeStamp, long salt, @Nonnull ChatCommandPacket.Entry[] argumentSignatures, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages);

            @Nonnull
            public abstract net.nonswag.tnl.listener.api.packets.incoming.ChatPacket chatPacket(@Nonnull String message, @Nonnull Instant timeStamp, long salt, @Nonnull byte[] signature, boolean signedPreview, @Nonnull LastSeenMessages.Update lastSeenMessages);

            @Nonnull
            public abstract ChatPreviewPacket chatPreviewPacket(int queryId, @Nonnull String query);

            @Nonnull
            public abstract ClientCommandPacket clientCommandPacket(@Nonnull ClientCommandPacket.Action action);

            @Nonnull
            public abstract ClientInformationPacket clientInformationPacket(@Nonnull String language, int viewDistance, @Nonnull ClientInformationPacket.ChatVisibility chatVisibility, boolean chatColors, int modelCustomisation, @Nonnull ClientInformationPacket.HandSide mainHand, boolean textFiltering, boolean listingAllowed);

            @Nonnull
            public abstract CommandSuggestionPacket commandSuggestionPacket(int id, @Nonnull String partialCommand);

            @Nonnull
            public abstract net.nonswag.tnl.listener.api.packets.incoming.CustomPayloadPacket customPayloadPacket(@Nonnull NamespacedKey channel, @Nonnull byte[] data);

            @Nonnull
            public abstract EditBookPacket editBookPacket(@Nullable String title, @Nonnull List<String> pages, int slot);

            @Nonnull
            public abstract EntityTagQueryPacket entityTagQueryPacket(int transactionId, int entityId);

            @Nonnull
            public abstract InteractPacket.Attack attack(int entityId, boolean sneaking);

            @Nonnull
            public abstract InteractPacket.Interact interactPacket(int entityId, boolean sneaking, @Nonnull Hand hand);

            @Nonnull
            public abstract InteractPacket.InteractAt interactAtPacket(int entityId, boolean sneaking, @Nonnull Hand hand, @Nonnull Vector location);

            @Nonnull
            public abstract JigsawGeneratePacket jigsawGeneratePacket(@Nonnull BlockPosition position, int levels, boolean keepJigsaws);

            @Nonnull
            public abstract KeepAlivePacket keepAlivePacket(long id);

            @Nonnull
            public abstract LockDifficultyPacket lockDifficultyPacket(boolean locked);

            @Nonnull
            public abstract MovePlayerPacket.Position movePlayerPacket(double x, double y, double z, boolean onGround);

            @Nonnull
            public abstract MovePlayerPacket.PositionRotation movePlayerPacket(double x, double y, double z, float yaw, float pitch, boolean onGround);

            @Nonnull
            public abstract MovePlayerPacket.Rotation movePlayerPacket(float yaw, float pitch, boolean onGround);

            @Nonnull
            public abstract MovePlayerPacket.Status movePlayerPacket(boolean onGround);

            @Nonnull
            public abstract net.nonswag.tnl.listener.api.packets.incoming.MoveVehiclePacket moveVehiclePacket(@Nonnull Position position);

            @Nonnull
            public abstract PaddleBoatPacket paddleBoatPacket(boolean left, boolean right);

            @Nonnull
            public abstract PickItemPacket pickItemPacket(int slot);

            @Nonnull
            public abstract PlaceRecipePacket placeRecipePacket(int containerId, @Nonnull NamespacedKey recipe, boolean shift);

            @Nonnull
            public abstract PlayerAbilitiesPacket playerAbilitiesPacket(boolean flying);

            @Nonnull
            public abstract PlayerActionPacket playerActionPacket(@Nonnull PlayerActionPacket.Action action, @Nonnull BlockPosition position, @Nonnull Direction direction, int sequence);

            @Nonnull
            public abstract PlayerCommandPacket playerCommandPacket(int entityId, @Nonnull PlayerCommandPacket.Action action, int data);

            @Nonnull
            public abstract PlayerInputPacket playerInputPacket(float sideways, float forward, boolean jumping, boolean sneaking);

            @Nonnull
            public abstract PongPacket pongPacket(int id);

            @Nonnull
            public abstract RecipeBookChangeSettingsPacket recipeBookChangeSettingsPacket(@Nonnull RecipeBookChangeSettingsPacket.RecipeBookType category, boolean guiOpen, boolean filteringCraftable);

            @Nonnull
            public abstract RecipeBookSeenRecipePacket recipeBookSeenRecipePacket(@Nonnull NamespacedKey recipe);

            @Nonnull
            public abstract RenameItemPacket renameItemPacket(@Nonnull String name);

            @Nonnull
            public abstract net.nonswag.tnl.listener.api.packets.incoming.ResourcePackPacket resourcePackPacket(@Nonnull net.nonswag.tnl.listener.api.packets.incoming.ResourcePackPacket.Action action);

            @Nonnull
            public abstract SeenAdvancementsPacket seenAdvancementsPacket(@Nonnull SeenAdvancementsPacket.Action action, @Nullable NamespacedKey tab);

            @Nonnull
            public abstract SelectTradePacket selectTradePacket(int trade);

            @Nonnull
            public abstract SetBeaconPacket setBeaconPacket(@Nullable SetBeaconPacket.Effect primary, @Nullable SetBeaconPacket.Effect secondary);

            @Nonnull
            public abstract SetCarriedItemPacket setCarriedItemPacket(int slot);

            @Nonnull
            public abstract SetCommandBlockPacket setCommandBlockPacket(@Nonnull BlockPosition position, @Nonnull String command, @Nonnull SetCommandBlockPacket.Mode mode, boolean trackOutput, boolean conditional, boolean alwaysActive);

            @Nonnull
            public abstract SetCommandMinecartPacket setCommandMinecartPacket(int entityId, @Nonnull String command, boolean trackOutput);

            @Nonnull
            public abstract SetCreativeModeSlotPacket setCreativeModeSlotPacket(int slot, @Nonnull TNLItem item);

            @Nonnull
            public abstract SetJigsawBlockPacket setJigsawBlockPacket(@Nonnull BlockPosition position, @Nonnull NamespacedKey name, @Nonnull NamespacedKey target, @Nonnull NamespacedKey pool, @Nonnull String finalState, @Nonnull SetJigsawBlockPacket.JointType joint);

            @Nonnull
            public abstract SetStructureBlockPacket setStructureBlockPacket(@Nonnull BlockPosition position, @Nonnull SetStructureBlockPacket.Type type, @Nonnull SetStructureBlockPacket.Mode mode, @Nonnull String name, @Nonnull BlockPosition offset, @Nonnull Vector size, @Nonnull Mirror mirror, @Nonnull Rotation rotation, @Nonnull String metadata, boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity, long seed);

            @Nonnull
            public abstract SignUpdatePacket signUpdatePacket(@Nonnull BlockPosition position, @Nonnull String[] lines);

            @Nonnull
            public abstract SwingPacket swingPacket(@Nonnull Hand hand);

            @Nonnull
            public abstract TeleportToEntityPacket teleportToEntityPacket(@Nonnull UUID target);

            @Nonnull
            public abstract UseItemOnPacket useItemOnPacket(@Nonnull Hand hand, @Nonnull UseItemOnPacket.BlockTargetResult target, int sequence);

            @Nonnull
            public abstract UseItemPacket useItemPacket(@Nonnull Hand hand, int sequence);

            @Nonnull
            public abstract WindowButtonClickPacket windowButtonClickPacket(int containerId, int buttonId);

            @Nonnull
            public abstract WindowClickPacket windowClickPacket(int containerId, int stateId, int slot, int buttonId, @Nonnull WindowClickPacket.ClickType clickType, @Nonnull TNLItem item, @Nonnull HashMap<Integer, TNLItem> changedSlots);

            @Nonnull
            public abstract WindowClosePacket windowClosePacket(int containerId);

            @Nonnull
            public abstract <P> PacketBuilder map(@Nonnull P packet);
        }

        public abstract static class Outgoing {

            @Nonnull
            public abstract BlockBreakAnimationPacket blockBreakAnimationPacket(@Nonnull BlockLocation location, int state);

            @Nonnull
            public abstract SetExperiencePacket setExperiencePacket(float experienceProgress, int totalExperience, int experienceLevel);

            @Nonnull
            public abstract StopSoundPacket stopSoundPacket(@Nullable NamespacedKey sound, @Nullable SoundCategory category);

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
            public abstract AnimationPacket animationPacket(int entityId, @Nonnull AnimationPacket.Animation animation);

            @Nonnull
            public abstract EntityAttachPacket entityAttachPacket(int holderId, int leashedId);

            @Nonnull
            public abstract EntityDestroyPacket entityDestroyPacket(int... destroyIds);

            @Nonnull
            public abstract EntityEquipmentPacket entityEquipmentPacket(int entityId, @Nonnull HashMap<SlotType, TNLItem> equipment);

            @Nonnull
            public abstract GameStateChangePacket gameStateChangePacket(@Nonnull GameStateChangePacket.Identifier identifier, float state);

            @Nonnull
            public abstract EntityStatusPacket entityStatusPacket(int entityId, @Nonnull EntityStatusPacket.Status status);

            @Nonnull
            public abstract EntitySpawnPacket entitySpawnPacket(@Nonnull Entity entity);

            @Nonnull
            public abstract <M> EntityMetadataPacket<M> entityMetadataPacket(int entityId, @Nonnull M dataWatcher, boolean updateAll);

            @Nonnull
            public abstract <M> EntityMetadataPacket<M> entityMetadataPacket(@Nonnull Entity entity, boolean updateAll);

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
            public abstract OpenBookPacket openBookPacket(@Nonnull Hand hand);

            @Nonnull
            public abstract MoveVehiclePacket moveVehiclePacket(@Nonnull Position position);

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
            public abstract InitializeBorderPacket initializeBorderPacket(@Nonnull VirtualBorder virtualBorder);

            @Nonnull
            public abstract SetBorderSizePacket setBorderSizePacket(double size);

            @Nonnull
            public abstract SetBorderLerpSizePacket setBorderLerpSizePacket(double oldSize, double newSize, long lerpTime);

            @Nonnull
            public abstract SetBorderCenterPacket setBorderCenterPacket(@Nonnull VirtualBorder.Center center);

            @Nonnull
            public abstract SetBorderWarningDelayPacket setBorderWarningDelayPacket(int warningDelay);

            @Nonnull
            public abstract SetBorderWarningDistancePacket setBorderWarningDistancePacket(int warningDistance);

            @Nonnull
            public abstract SelectAdvancementsTabPacket selectAdvancementsTabPacket(@Nullable NamespacedKey tab);

            @Nonnull
            public abstract SetDisplayChatPreviewPacket setDisplayChatPreviewPacket(boolean enabled);

            @Nonnull
            public abstract ResourcePackPacket resourcePackPacket(@Nonnull String url, @Nullable String hash, @Nullable String prompt, boolean required);

            @Nonnull
            public abstract ItemTakePacket itemTakePacket(int itemId, int collectorId, int amount);

            @Nonnull
            public abstract <P> net.nonswag.tnl.listener.api.packets.outgoing.PacketBuilder map(@Nonnull P packet);
        }
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
