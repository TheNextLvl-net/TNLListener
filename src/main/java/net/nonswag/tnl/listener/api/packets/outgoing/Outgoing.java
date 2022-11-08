package net.nonswag.tnl.listener.api.packets.outgoing;

import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.advancement.Advancement;
import net.nonswag.tnl.listener.api.border.VirtualBorder;
import net.nonswag.tnl.listener.api.item.SlotType;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.nbt.NBTTag;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface Outgoing {

    ChatPreviewPacket chatPreviewPacket(int queryId, @Nullable Component query);

    SetSimulationDistancePacket setSimulationDistancePacket(int simulationDistance);

    SetCarriedItemPacket setCarriedItemPacket(int slot);

    SetDisplayObjectivePacket setDisplayObjectivePacket(int slot, @Nullable String objectiveName);

    BlockDestructionPacket blockDestructionPacket(int id, BlockPosition position, int state);

    SetExperiencePacket setExperiencePacket(float experienceProgress, int totalExperience, int experienceLevel);

    StopSoundPacket stopSoundPacket(@Nullable NamespacedKey sound, @Nullable SoundCategory category);

    BossEventPacket bossEventPacket(BossEventPacket.Action action, BossBar bossBar);

    CameraPacket cameraPacket(int targetId);

    SystemChatPacket systemChatPacket(Component message, boolean overlay);

    ContainerClosePacket containerClosePacket(int windowId);

    CooldownPacket cooldownPacket(Material item, int cooldown);

    CustomPayloadPacket customPayloadPacket(NamespacedKey channel, byte[]... bytes);

    AnimationPacket animationPacket(int entityId, AnimationPacket.Animation animation);

    EntityAttachPacket entityAttachPacket(int holderId, int leashedId);

    RemoveEntitiesPacket removeEntitiesPacket(int... entityIds);

    EntityEquipmentPacket entityEquipmentPacket(int entityId, HashMap<SlotType, TNLItem> equipment);

    GameStateChangePacket gameStateChangePacket(GameStateChangePacket.Identifier identifier, float state);

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

    NamedEntitySpawnPacket namedEntitySpawnPacket(HumanEntity human);

    OpenSignEditorPacket openSignEditorPacket(BlockPosition position);

    OpenBookPacket openBookPacket(Hand hand);

    MoveVehiclePacket moveVehiclePacket(Position position);

    OpenScreenPacket openScreenPacket(int containerId, OpenScreenPacket.Type type, Component title);

    PlayerInfoPacket playerInfoPacket(Player player, PlayerInfoPacket.Action action);

    ContainerSetSlotPacket containerSetSlotPacket(int containerId, int stateId, int slot, @Nullable ItemStack itemStack);

    UpdateTimePacket updateTimePacket(long age, long timestamp, boolean cycle);

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

    CommandSuggestionsPacket commandSuggestionsPacket(int completionId, CommandSuggestionsPacket.Suggestions suggestions);

    SetDisplayChatPreviewPacket setDisplayChatPreviewPacket(boolean enabled);

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

    <P> PacketBuilder map(P packet);
}
