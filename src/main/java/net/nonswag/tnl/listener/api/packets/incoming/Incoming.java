package net.nonswag.tnl.listener.api.packets.incoming;

import net.nonswag.tnl.listener.api.chat.LastSeenMessages;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.location.Position;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.manager.ResourceManager;
import org.bukkit.Difficulty;
import org.bukkit.NamespacedKey;
import org.bukkit.Rotation;
import org.bukkit.block.structure.Mirror;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface Incoming {

    AcceptTeleportationPacket acceptTeleportationPacket(int id);

    BlockEntityTagQueryPacket blockEntityTagQueryPacket(int transactionId, BlockPosition position);

    ChangeDifficultyPacket changeDifficultyPacket(Difficulty difficulty);

    ChatAckPacket chatAckPacket(LastSeenMessages.Update lastSeenMessages);

    ChatCommandPacket chatCommandPacket(String command, Instant timeStamp, long salt, ChatCommandPacket.Entry[] argumentSignatures, boolean signedPreview, LastSeenMessages.Update lastSeenMessages);

    ChatPacket chatPacket(String message, Instant timeStamp, long salt, byte[] signature, boolean signedPreview, LastSeenMessages.Update lastSeenMessages);

    ChatPreviewPacket chatPreviewPacket(int queryId, String query);

    ClientCommandPacket clientCommandPacket(ClientCommandPacket.Action action);

    ClientInformationPacket clientInformationPacket(String language, int viewDistance, ClientInformationPacket.ChatVisibility chatVisibility, boolean chatColors, int modelCustomisation, Hand.Side mainHand, boolean textFiltering, boolean listingAllowed);

    CommandSuggestionPacket commandSuggestionPacket(int id, String command);

    CustomPayloadPacket customPayloadPacket(NamespacedKey channel, byte[] data);

    EditBookPacket editBookPacket(@Nullable String title, List<String> pages, int slot);

    EntityTagQueryPacket entityTagQueryPacket(int transactionId, int entityId);

    InteractPacket.Attack attack(int entityId, boolean sneaking);

    InteractPacket.Interact interactPacket(int entityId, boolean sneaking, Hand hand);

    InteractPacket.InteractAt interactAtPacket(int entityId, boolean sneaking, Hand hand, Vector location);

    JigsawGeneratePacket jigsawGeneratePacket(BlockPosition position, int levels, boolean keepJigsaws);

    KeepAlivePacket keepAlivePacket(long id);

    LockDifficultyPacket lockDifficultyPacket(boolean locked);

    MovePlayerPacket.Position movePlayerPacket(double x, double y, double z, boolean onGround);

    MovePlayerPacket.PositionRotation movePlayerPacket(double x, double y, double z, float yaw, float pitch, boolean onGround);

    MovePlayerPacket.Rotation movePlayerPacket(float yaw, float pitch, boolean onGround);

    MovePlayerPacket.Status movePlayerPacket(boolean onGround);

    MoveVehiclePacket moveVehiclePacket(Position position);

    PaddleBoatPacket paddleBoatPacket(boolean left, boolean right);

    PickItemPacket pickItemPacket(int slot);

    PlaceRecipePacket placeRecipePacket(int containerId, NamespacedKey recipe, boolean shift);

    PlayerAbilitiesPacket playerAbilitiesPacket(boolean flying);

    PlayerActionPacket playerActionPacket(PlayerActionPacket.Action action, BlockPosition position, Direction direction, int sequence);

    PlayerCommandPacket playerCommandPacket(int entityId, PlayerCommandPacket.Action action, int data);

    PlayerInputPacket playerInputPacket(float sideways, float forward, boolean jumping, boolean sneaking);

    PongPacket pongPacket(int id);

    RecipeBookChangeSettingsPacket recipeBookChangeSettingsPacket(RecipeBookChangeSettingsPacket.RecipeBookType category, boolean guiOpen, boolean filteringCraftable);

    RecipeBookSeenRecipePacket recipeBookSeenRecipePacket(NamespacedKey recipe);

    RenameItemPacket renameItemPacket(String name);

    ResourcePackPacket resourcePackPacket(ResourceManager.Action action);

    SeenAdvancementsPacket seenAdvancementsPacket(SeenAdvancementsPacket.Action action, @Nullable NamespacedKey tab);

    SelectTradePacket selectTradePacket(int trade);

    SetBeaconPacket setBeaconPacket(@Nullable SetBeaconPacket.Effect primary, @Nullable SetBeaconPacket.Effect secondary);

    SetCarriedItemPacket setCarriedItemPacket(int slot);

    SetCommandBlockPacket setCommandBlockPacket(BlockPosition position, String command, SetCommandBlockPacket.Mode mode, boolean trackOutput, boolean conditional, boolean alwaysActive);

    SetCommandMinecartPacket setCommandMinecartPacket(int entityId, String command, boolean trackOutput);

    SetCreativeModeSlotPacket setCreativeModeSlotPacket(int slot, TNLItem item);

    SetJigsawBlockPacket setJigsawBlockPacket(BlockPosition position, NamespacedKey name, NamespacedKey target, NamespacedKey pool, String finalState, SetJigsawBlockPacket.JointType joint);

    SetStructureBlockPacket setStructureBlockPacket(BlockPosition position, SetStructureBlockPacket.Type type, SetStructureBlockPacket.Mode mode, String name, BlockPosition offset, Vector size, Mirror mirror, Rotation rotation, String metadata, boolean ignoreEntities, boolean showAir, boolean showBoundingBox, float integrity, long seed);

    SignUpdatePacket signUpdatePacket(BlockPosition position, String[] lines);

    SwingPacket swingPacket(Hand hand);

    TeleportToEntityPacket teleportToEntityPacket(UUID target);

    UseItemOnPacket useItemOnPacket(Hand hand, UseItemOnPacket.BlockTargetResult target, int sequence, long timestamp);

    UseItemPacket useItemPacket(Hand hand, int sequence);

    ContainerButtonClickPacket containerButtonClickPacket(int containerId, int buttonId);

    ContainerClickPacket containerClickPacket(int containerId, int stateId, int slot, int buttonId, ContainerClickPacket.ClickType clickType, TNLItem item, HashMap<Integer, TNLItem> changedSlots);

    ContainerClosePacket containerClosePacket(int containerId);

    <P> Class<? extends IncomingPacket> map(Class<P> clazz);

    <P> PacketBuilder map(P packet);
}
