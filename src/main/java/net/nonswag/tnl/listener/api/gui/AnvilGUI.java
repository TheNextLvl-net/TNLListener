package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.nonswag.tnl.listener.api.packets.outgoing.ContainerSetDataPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AnvilGUI extends GUI {

    private final List<TextInputEvent> textInputEvents = new ArrayList<>();
    private String textInput = "";
    private int repairCosts = 0;

    public AnvilGUI(String title) {
        super(InventoryType.ANVIL, title);
        addTextInputEvent((player, text) -> this.textInput = text);
    }

    public void setRepairCosts(int repairCosts) {
        this.repairCosts = repairCosts;
        ContainerSetDataPacket.create(ContainerSetDataPacket.Anvil.REPAIR_COST, repairCosts).send(getViewers());
    }

    public List<TextInputEvent> getTextInputEvents() {
        return ImmutableList.copyOf(textInputEvents);
    }

    public AnvilGUI addTextInputEvent(TextInputEvent textInputEvent) {
        this.textInputEvents.add(textInputEvent);
        return this;
    }

    public AnvilGUI removeTextInputEvent(TextInputEvent textInputEvent) {
        this.textInputEvents.remove(textInputEvent);
        return this;
    }

    public AnvilGUI setTextInput(String textInput) {
        GUIItem item = getItem(0);
        if (item == null) return this;
        this.textInput = textInput;
        setItem(0, item.getItem().setName(textInput));
        return this;
    }

    public interface TextInputEvent {
        void onTextInput(TNLPlayer player, String text);
    }
}
