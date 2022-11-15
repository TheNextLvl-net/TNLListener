package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.location.BlockLocation;
import net.nonswag.tnl.listener.api.packets.outgoing.*;
import net.nonswag.tnl.listener.api.sign.SignMenu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Getter
public abstract class InterfaceManager extends Manager {

    @Nullable
    protected GUI GUI = null;
    @Nullable
    protected SignMenu signMenu = null;

    public void openGUI(GUI gui) {
        GUI current = getGUI();
        if (gui.equals(current)) {
            openPackets(gui);
            return;
        }
        if (!gui.getOpenListener().onOpen(getPlayer())) return;
        if (current != null && current.getCloseListener().onClose(getPlayer(), true)) current.removeViewer(getPlayer());
        this.GUI = gui;
        if (!gui.getViewers().contains(getPlayer())) gui.addViewer(getPlayer());
        if (gui.getOpenSound() != null && current == null) getPlayer().soundManager().playSound(gui.getOpenSound());
        openPackets(gui);
    }

    private void openPackets(GUI gui) {
        Component title = Component.text(Message.format(gui.getTitle(), getPlayer()));
        if (!gui.getType().equals(InventoryType.CHEST)) {
            OpenScreenPacket.create(OpenScreenPacket.Type.valueOf(gui.getType()), title).send(getPlayer());
        } else OpenScreenPacket.create(gui.getSize() / 9, title).send(getPlayer());
        EntityStatusPacket.create(getPlayer().getEntityId(), EntityStatusPacket.Status.STOP_EATING).send(getPlayer());
        updateGUI(gui);
    }

    public void updateGUI() {
        GUI gui = getGUI();
        if (gui != null) updateGUI(gui);
    }

    public void updateGUI(GUI gui) {
        ContainerSetContentPacket.create(gui.items(), TNLItem.create(Material.AIR)).send(getPlayer());
    }

    public void closeGUI(boolean update) {
        if (getGUI() != null) getGUI().removeViewer(getPlayer());
        GUI = null;
        if (getSignMenu() == null && update) ContainerClosePacket.create().send(getPlayer());
    }

    public void closeGUI() {
        closeGUI(true);
    }

    public void openBook(ItemStack item) {
        closeGUI(false);
        getPlayer().bukkit().openBook(item);
    }

    public abstract void openVirtualSignEditor(SignMenu signMenu);

    public void openSignEditor(BlockLocation location) {
        OpenSignEditorPacket.create(location).send(getPlayer());
    }

    public void closeSignMenu() {
        signMenu = null;
        if (GUI == null) ContainerClosePacket.create().send(getPlayer());
    }

    public void demo(MutualGetter<GameEventPacket.DemoEvent, Float> demo) {
        GameEventPacket.create(GameEventPacket.DEMO_EVENT, demo).send(getPlayer());
    }
}
