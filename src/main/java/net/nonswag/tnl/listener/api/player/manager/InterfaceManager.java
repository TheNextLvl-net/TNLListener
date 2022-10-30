package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import net.nonswag.tnl.listener.api.sign.SignMenu;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class InterfaceManager extends Manager {

    @Nullable
    protected GUI GUI = null;
    @Nullable
    protected SignMenu signMenu = null;

    public void openGUI(@Nonnull GUI gui) {
        GUI current = getGUI();
        if (gui.equals(current)) {
            openPackets(gui);
            return;
        }
        if (!gui.getOpenListener().onOpen(getPlayer())) return;
        if (current != null) {
            if (!current.getCloseListener().onClose(getPlayer(), true)) return;
            if (current.getCloseSound() != null) getPlayer().soundManager().playSound(current.getCloseSound());
            current.removeViewer(getPlayer());
        }
        this.GUI = gui;
        if (!gui.getViewers().contains(getPlayer())) gui.addViewer(getPlayer());
        if (gui.getOpenSound() != null && current == null) getPlayer().soundManager().playSound(gui.getOpenSound());
        openPackets(gui);
    }

    private void openPackets(@Nonnull GUI gui) {
        String title = Message.format(gui.getTitle(), getPlayer());
        if (!gui.getType().equals(InventoryType.CHEST)) {
            OpenWindowPacket.create(OpenWindowPacket.Type.valueOf(gui.getType()), title).send(getPlayer());
        } else OpenWindowPacket.create(gui.getSize() / 9, title).send(getPlayer());
        EntityStatusPacket.create(getPlayer().getEntityId(), EntityStatusPacket.Status.STOP_EATING).send(getPlayer());
        updateGUI(gui);
    }

    public void updateGUI() {
        GUI gui = getGUI();
        if (gui != null) updateGUI(gui);
    }

    public void updateGUI(@Nonnull GUI gui) {
        WindowItemsPacket.create(gui.items()).send(getPlayer());
    }

    public void closeGUI(boolean update) {
        if (getGUI() != null) getGUI().removeViewer(getPlayer());
        GUI = null;
        if (getSignMenu() == null && update) CloseWindowPacket.create().send(getPlayer());
    }

    public void closeGUI() {
        closeGUI(true);
    }

    public void openBook(@Nonnull ItemStack item) {
        closeGUI(false);
        getPlayer().bukkit().openBook(item);
    }

    public abstract void openVirtualSignEditor(@Nonnull SignMenu signMenu);

    public void openSignEditor(@Nonnull BlockLocation location) {
        OpenSignPacket.create(location).send(getPlayer());
    }

    public void closeSignMenu() {
        signMenu = null;
        if (GUI == null) CloseWindowPacket.create().send(getPlayer());
    }

    public void demo(@Nonnull MutualGetter<GameStateChangePacket.Demo, Float> demo) {
        GameStateChangePacket.create(GameStateChangePacket.DEMO, demo).send(getPlayer());
    }
}
