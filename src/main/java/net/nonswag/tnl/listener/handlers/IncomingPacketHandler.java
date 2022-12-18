package net.nonswag.tnl.listener.handlers;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.core.api.language.Language;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.holograms.api.Hologram;
import net.nonswag.tnl.holograms.api.event.InteractEvent;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.data.Buffer;
import net.nonswag.tnl.listener.api.entity.EntityHelper;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.event.TNLEvent;
import net.nonswag.tnl.listener.api.gui.AnvilGUI;
import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import net.nonswag.tnl.listener.api.gui.Interaction;
import net.nonswag.tnl.listener.api.location.BlockPosition;
import net.nonswag.tnl.listener.api.location.Direction;
import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.packets.incoming.*;
import net.nonswag.tnl.listener.api.packets.outgoing.ContainerSetSlotPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.OpenScreenPacket;
import net.nonswag.tnl.listener.api.player.Hand;
import net.nonswag.tnl.listener.api.player.manager.PermissionManager;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.api.sign.SignMenu;
import net.nonswag.tnl.listener.events.*;
import net.nonswag.tnl.listener.events.mods.labymod.LabyPlayerMessageEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

class IncomingPacketHandler {

    static void init(EventManager manager) {
        manager.registerPacketReader(ClientCommandPacket.class, (player, packet, cancelled) -> {
            if (!packet.getAction().equals(ClientCommandPacket.Action.REQUEST_STATS)) return;
            cancelled.set(true);
        });
        manager.registerPacketReader(ClientInformationPacket.class, (player, packet, cancelled) -> {
            Language language = Language.fromLocale(packet.getLanguage());
            Language old = player.data().getLanguage();
            if (language.equals(Language.UNKNOWN) || language.equals(old)) return;
            player.data().setLanguage(language);
            new PlayerLanguageChangeEvent(player, old).call();
        });
        manager.registerPacketReader(InteractPacket.class, (player, packet, cancelled) -> {
            Entity entity = EntityHelper.getInstance().getEntity(packet.getEntityId());
            TNLEvent entityEvent = null;
            if (packet instanceof InteractPacket.Attack && entity != null) {
                entityEvent = new EntityDamageByPlayerEvent(player, entity);
            } else if (packet instanceof InteractPacket.InteractAt && entity != null) {
                entityEvent = new PlayerInteractAtEntityEvent(player, entity);
            } else if (entity == null) {
                FakePlayer fakePlayer = player.npcFactory().getFakePlayer(packet.getEntityId());
                Hand.Side side = packet instanceof InteractPacket.Interact interact && interact.getHand().isMainHand() ? Hand.Side.RIGHT : Hand.Side.LEFT;
                if (fakePlayer != null) {
                    fakePlayer.onInteract().accept(new FakePlayer.InteractEvent(player, fakePlayer, side));
                } else for (Hologram hologram : Hologram.getHolograms()) {
                    for (int i : player.hologramManager().getIds(hologram)) {
                        if (packet.getEntityId() != i) continue;
                        hologram.onInteract().accept(new InteractEvent(hologram, player, side));
                        return;
                    }
                }
            }
            if (entityEvent != null && !entityEvent.call()) cancelled.set(true);
        });
        manager.registerPacketReader(CommandSuggestionPacket.class, (player, packet, cancelled) -> {
            String[] args = packet.getCommand().split(" ");
            if (args.length > 0 && args[0].startsWith("/")) {
                PermissionManager permissionManager = player.permissionManager();
                if (permissionManager.hasPermission(Settings.TAB_COMPLETE_BYPASS_PERMISSION.getValue())) return;
                if (!Settings.TAB_COMPLETER.getValue()) cancelled.set(true);
            } else if (args.length == 0) cancelled.set(true);
        });
        manager.registerPacketReader(CustomPayloadPacket.class, (player, packet, cancelled) -> {
            cancelled.set(true);
            String namespace = packet.getChannel().getNamespace();
            try {
                if (!namespace.equals("labymod3")) return;
                DataInputStream stream = new DataInputStream(new ByteArrayInputStream(packet.getData()));
                String key = Buffer.readString(stream);
                JsonElement message = JsonHelper.parse(Buffer.readString(stream));
                ModMessage modMessage = new ModMessage(packet.getChannel().getKey(), key, message);
                player.labymod().handleMessage(modMessage);
                new LabyPlayerMessageEvent(player.labymod(), modMessage).call();
            } catch (Exception e) {
                Logger.error.println("An error occurred while reading a mod message from <'" + namespace + "'>", e);
            }
        });
        manager.registerPacketReader(SignUpdatePacket.class, (player, packet, cancelled) -> {
            SignMenu menu = player.interfaceManager().getSignMenu();
            if (menu == null) return;
            cancelled.set(true);
            if (menu.getResponse() != null) Bootstrap.getInstance().sync(() -> {
                boolean success = menu.getResponse().test(player, packet.getLines());
                if (!success && menu.isReopenOnFail()) player.interfaceManager().openVirtualSignEditor(menu);
            });
            if (menu.getLocation() != null) player.worldManager().sendBlockChange(menu.getLocation());
            player.interfaceManager().closeSignMenu();
        });
        manager.registerPacketReader(RenameItemPacket.class, (player, packet, cancelled) -> {
            GUI gui = player.interfaceManager().getGUI();
            if (!(gui instanceof AnvilGUI anvil)) return;
            cancelled.set(true);
            for (AnvilGUI.TextInputEvent textInputEvent : anvil.getTextInputEvents()) {
                textInputEvent.onTextInput(player, packet.getName());
            }
        });
        manager.registerPacketReader(ResourcePackPacket.class, (player, packet, cancelled) ->
                player.resourceManager().setAction(packet.getAction()));
        manager.registerPacketReader(ContainerClickPacket.class, (player, packet, cancelled) -> {
            GUI gui = player.interfaceManager().getGUI();
            if (gui == null) return;
            if (packet.getContainerId() == 1) {
                Interaction.Type type = packet.getInteractionType();
                gui.getClickListener().onClick(player, packet.getSlot(), type);
                GUIItem item = gui.getItem(packet.getSlot());
                if (item != null) for (Interaction interaction : item.interactions(type)) {
                    interaction.action().accept(player);
                }
            }
            cancelled.set(true);
            ContainerSetSlotPacket.create(-1, -1, null).send(player);
            player.inventoryManager().updateInventory();
            player.interfaceManager().updateGUI();
        });
        manager.registerPacketReader(ContainerClosePacket.class, (player, packet, cancelled) -> {
            GUI gui = player.interfaceManager().getGUI();
            if (gui == null) return;
            cancelled.set(true);
            if (!gui.getCloseListener().onClose(player, false)) {
                OpenScreenPacket.create(gui.getSize() / 9, Component.text(Message.format(gui.getTitle()))).send(player);
                player.interfaceManager().updateGUI(gui);
            } else {
                if (gui.getCloseSound() != null) player.soundManager().playSound(gui.getCloseSound());
                player.interfaceManager().closeGUI(false);
            }
        });
        manager.registerPacketReader(PickItemPacket.class, (player, packet, cancelled) -> {
            if (!new PlayerItemPickEvent(player, packet.getSlot()).call()) cancelled.set(true);
        });
        manager.registerPacketReader(UseItemOnPacket.class, (player, packet, cancelled) -> {
            BlockPosition position = packet.getTarget().getPosition();
            Block block = new Location(player.worldManager().getWorld(), position.getX(), position.getY(), position.getZ()).getBlock();
            if (block.getLocation().distance(player.worldManager().getLocation()) > 10) {
                cancelled.set(true);
                return;
            }
            Direction direction = packet.getTarget().getSide();
            ItemStack itemStack;
            if (packet.getHand().isMainHand()) itemStack = player.inventoryManager().getInventory().getItemInMainHand();
            else itemStack = player.inventoryManager().getInventory().getItemInOffHand();
            PlayerInteractEvent interactEvent = new PlayerInteractEvent(player, block, direction, itemStack);
            if (!interactEvent.call()) {
                cancelled.set(true);
                interactEvent.getPlayer().inventoryManager().updateInventory();
            }
            Bootstrap.getInstance().sync(() -> {
                for (BlockFace face : BlockFace.values()) {
                    player.worldManager().sendBlockChange(interactEvent.getClickedBlock().getRelative(face));
                }
            }, 1);
        });
        manager.registerPacketReader(ContainerClosePacket.class, (player, packet, cancelled) -> {
            GUI gui = player.interfaceManager().getGUI();
            if (gui == null) return;
            if (gui.getCloseSound() != null) player.soundManager().playSound(gui.getCloseSound());
            gui.getCloseListener().onClose(player, true);
        });
    }
}
