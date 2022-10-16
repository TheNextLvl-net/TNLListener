package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.nonswag.tnl.listener.api.packets.WindowDataPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.inventory.InventoryType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AnvilGUI extends GUI {

    @Nonnull
    private final List<TextInputEvent> textInputEvents = new ArrayList<>();
    @Nonnull
    private String textInput = "";
    private int repairCosts = 0;

    public AnvilGUI(@Nonnull String title) {
        super(InventoryType.ANVIL, title);
        addTextInputEvent((player, text) -> this.textInput = text);
    }

    public void setRepairCosts(int repairCosts) {
        this.repairCosts = repairCosts;
        WindowDataPacket.create(WindowDataPacket.Anvil.REPAIR_COST, repairCosts).send(getViewers());
    }

    @Nonnull
    public List<TextInputEvent> getTextInputEvents() {
        return ImmutableList.copyOf(textInputEvents);
    }

    @Nonnull
    public AnvilGUI addTextInputEvent(@Nonnull TextInputEvent textInputEvent) {
        this.textInputEvents.add(textInputEvent);
        return this;
    }

    @Nonnull
    public AnvilGUI removeTextInputEvent(@Nonnull TextInputEvent textInputEvent) {
        this.textInputEvents.remove(textInputEvent);
        return this;
    }

    @Nonnull
    public AnvilGUI setTextInput(@Nonnull String textInput) {
        GUIItem item = getItem(0);
        if (item == null) return this;
        this.textInput = textInput;
        setItem(0, item.getItem().setName(textInput));
        return this;
    }

    public interface TextInputEvent {
        void onTextInput(@Nonnull TNLPlayer player, @Nonnull String text);
    }
}
