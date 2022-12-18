package net.nonswag.tnl.listener.api.packets.outgoing;

import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.advancement.Advancement;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.chat.ChatType;
import net.nonswag.tnl.listener.api.chat.Filter;
import net.nonswag.tnl.listener.api.chat.MessageSignature;
import net.nonswag.tnl.listener.api.chat.SignedMessageBody;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.nbt.NBTTag;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface Outgoing {

    SetSimulationDistancePacket setSimulationDistancePacket(int simulationDistance);

    SetCarriedItemPacket setCarriedItemPacket(int slot);

    SetDisplayObjectivePacket setDisplayObjectivePacket(int slot, @Nullable String objectiveName);

    BlockDestructionPacket blockDestructionPacket(int id, BlockPosition position, int state);

    SetExperiencePacket setExperiencePacket(float experienceProgress, int totalExperience, int experienceLevel);

    StopSoundPacket stopSoundPacket(@Nullable NamespacedKey sound, @Nullable SoundCategory category);

    BossEventPacket bossEventPacket(BossEventPacket.Action action, BossBar bossBar);

    SetCameraPacket setCameraPacket(int targetId);

    SystemChatPacket systemChatPacket(Component message, boolean overlay);

    ContainerClosePacket containerClosePacket(int windowId);

    CooldownPacket cooldownPacket(Material item, int cooldown);

    CustomPayloadPacket customPayloadPacket(NamespacedKey channel, byte[]... bytes);

    AnimatePacket animatePacket(int entityId, AnimatePacket.Animation animation);

    EntityAttachPacket entityAttachPacket(int holderId, int leashedId);

    RemoveEntitiesPacket removeEntitiesPacket(int... entityIds);

    SetEquipmentPacket setEquipmentPacket(int entityId, HashMap<SlotType, TNLItem> equipment);

    GameEventPacket gameEventPacket(GameEventPacket.Identifier identifier, float state);

    EntityStatusPacket entityStatusPacket(int entityId, EntityStatusPacket.Status status);

    AddEntityPacket addEntityPacket(int entityId, UUID uniqueId, Position position, EntityType entityType, int entityData, Vector velocity, double headYaw);

    <M> EntityMetadataPacket<M> entityMetadataPacket(int entityId, M dataWatcher, boolean updateAll);

    <M> EntityMetadataPacket<M> entityMetadataPacket(Entity entity, boolean updateAll);

    EntityHeadRotationPacket entityHeadRotationPacket(int entityId, float yaw);

    EntityBodyRotationPacket entityBodyRotationPacket(int entityId, float rotation);

    TeleportEntityPacket teleportEntityPacket(int entityId, Position position, boolean onGround);

    SetEntityMotionPacket setEntityMotionPacket(int entityId, Vector velocity);

    LivingEntitySpawnPacket livingEntitySpawnPacket(LivingEntity entity);

    MapChunkPacket mapChunkPacket(Chunk chunk, int section);

    SetPassengersPacket setPassengersPacket(int holderId, int[] passengers);

    AddPlayerPacket addPlayerPacket(int entityId, UUID uniqueId, Position position);

    OpenSignEditorPacket openSignEditorPacket(BlockPosition position);

    OpenBookPacket openBookPacket(Hand hand);

    MoveVehiclePacket moveVehiclePacket(Position position);

    OpenScreenPacket openScreenPacket(int containerId, OpenScreenPacket.Type type, Component title);

    PlayerInfoUpdatePacket playerInfoUpdatePacket(List<PlayerInfoUpdatePacket.Action> actions, List<PlayerInfoUpdatePacket.Entry> entries);

    PlayerInfoRemovePacket playerInfoRemovePacket(List<UUID> profileIds);

    ContainerSetSlotPacket containerSetSlotPacket(int containerId, int stateId, int slot, @Nullable ItemStack itemStack);

    SetTimePacket setTimePacket(long age, long timestamp, boolean cycle);

    ContainerSetDataPacket containerSetDataPacket(int containerId, int propertyId, int value);

    ContainerSetContentPacket containerSetContentPacket(int containerId, int stateId, List<TNLItem> content, TNLItem cursor);

    InitializeBorderPacket initializeBorderPacket(VirtualBorder virtualBorder);

    SetBorderSizePacket setBorderSizePacket(double size);

    SetBorderLerpSizePacket setBorderLerpSizePacket(double oldSize, double newSize, long lerpTime);

    SetBorderCenterPacket setBorderCenterPacket(VirtualBorder.Center center);

    SetBorderWarningDelayPacket setBorderWarningDelayPacket(int warningDelay);

    SetBorderWarningDistancePacket setBorderWarningDistancePacket(int warningDistance);

    SelectAdvancementsTabPacket selectAdvancementsTabPacket(@Nullable NamespacedKey tab);

    HorseScreenOpenPacket horseScreenOpenPacket(int containerId, int size, int entityId);

    CommandSuggestionsPacket commandSuggestionsPacket(int id, CommandSuggestionsPacket.Suggestions suggestions);

    ResourcePackPacket resourcePackPacket(String url, @Nullable String hash, @Nullable Component prompt, boolean required);

    SetPlayerTeamPacket setPlayerTeamPacket(String name, SetPlayerTeamPacket.Option option, @Nullable SetPlayerTeamPacket.Parameters parameters, List<String> entries);

    TagQueryPacket tagQueryPacket(int transactionId, @Nullable NBTTag tag);

    SetChunkCacheRadiusPacket setChunkCacheRadiusPacket(int radius);

    RotateHeadPacket rotateHeadPacket(int entityId, byte yaw);

    TakeItemEntityPacket takeItemEntityPacket(int entityId, int playerId, int amount);

    SetChunkCacheCenterPacket setChunkCacheCenterPacket(int x, int z);

    ChangeDifficultyPacket changeDifficultyPacket(Difficulty difficulty, boolean locked);

    KeepAlivePacket keepAlivePacket(long id);

    SetActionBarTextPacket setActionBarTextPacket(Component text);

    DisconnectPacket disconnectPacket(Component reason);

    ForgetLevelChunkPacket forgetLevelChunkPacket(int x, int z);

    TabListPacket tabListPacket(Component header, Component footer);

    PingPacket pingPacket(int id);

    BlockChangedAckPacket blockChangedAckPacket(int sequence);

    TitlePacket.SetTitlesAnimation setTitlesAnimation(int timeIn, int timeStay, int timeOut);

    TitlePacket.SetTitleText setTitleText(Component text);

    TitlePacket.SetSubtitleText setSubtitleText(Component text);

    TitlePacket.ClearTitles clearTitles(boolean resetTimes);

    SetEntityLinkPacket setEntityLinkPacket(int leashHolderId, int leashedEntityId);

    BlockEventPacket blockEventPacket(BlockPosition position, Material blockType, int type, int data);

    SetDefaultSpawnPositionPacket setDefaultSpawnPositionPacket(BlockPosition position, float angle);

    SetScorePacket setScorePacket(SetScorePacket.Method method, @Nullable String objectiveName, String owner, int score);

    UpdateAdvancementsPacket updateAdvancementsPacket(boolean reset, HashMap<NamespacedKey, Advancement.Builder> added, List<NamespacedKey> removed, HashMap<NamespacedKey, Advancement.Progress> progress);

    LevelEventPacket levelEventPacket(int eventId, BlockPosition position, int data, boolean global);

    PlayerChatPacket playerChatPacket(UUID sender, int index, @Nullable MessageSignature signature, SignedMessageBody body, @Nullable Component unsignedContent, Filter filter, ChatType chatType);

    SetHealthPacket setHealthPacket(float health, int food, float saturation);

    ServerDataPacket serverDataPacket(@Nullable Component motd, @Nullable String serverIcon, boolean chatPreview);

    SectionBlocksUpdatePacket sectionBlocksUpdatePacket(long section, short[] positions, int[] states, boolean suppressLightUpdates);

    PlayerLookAtPacket playerLookAtPacket(PlayerLookAtPacket.Anchor self, Position position, int entityId, @Nullable PlayerLookAtPacket.Anchor target);

    PlayerCombatKillPacket playerCombatKillPacket(int victimId, int killerId, Component message);

    PlayerCombatEndPacket playerCombatEndPacket(int durationSinceLastAttack, int killerId);

    PlayerCombatEnterPacket playerCombatEnterPacket();

    MerchantOffersPacket merchantOffersPacket(int containerId, List<MerchantOffersPacket.Offer> offers, int level, int experience, boolean showProgress, boolean canRestock);

    AddExperienceOrbPacket addExperienceOrbPacket(int entityId, Position position, int value);

    ExplodePacket explodePacket(Position position, float radius, List<BlockPosition> affectedBlocks, Vector knockback);

    PlaceGhostRecipePacket placeGhostRecipePacket(int containerId, NamespacedKey recipe);

    BlockUpdatePacket blockUpdatePacket(BlockPosition position, int blockState);

    CustomChatCompletionsPacket customChatCompletionsPacket(CustomChatCompletionsPacket.Action action, List<String> entries);

    EntityEventPacket entityEventPacket(int entityId, byte eventId);

    MoveEntityPacket.PositionRotation moveEntityPacket(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround);

    MoveEntityPacket.Rotation moveEntityPacket(int entityId, float yaw, float pitch, boolean onGround);

    MoveEntityPacket.Position moveEntityPacket(int entityId, double x, double y, double z, boolean onGround);

    DisguisedChatPacket disguisedChatPacket(Component message, ChatType chatType);

    <P> Class<? extends OutgoingPacket> map(Class<P> clazz);

    <P> PacketBuilder map(P packet);
}
