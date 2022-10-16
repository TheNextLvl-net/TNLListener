package net.nonswag.tnl.listener.api.gui;

import net.nonswag.core.api.math.Range;
import net.nonswag.tnl.listener.api.item.TNLItem;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FormattedGUI extends GUI {

    @Nonnull
    private final Formatter formatter;
    @Nonnull
    protected GUIItem close = TNLItem.create(Material.BARRIER).setName("§8* §cClose window").toGUIItem().
            addInteractions(new Interaction(player -> player.interfaceManager().closeGUI()));

    @Nonnull
    protected TNLItem previousPage = TNLItem.create(Material.ARROW).setName("§8» §3Previous page");
    @Nonnull
    protected TNLItem nextPage = TNLItem.create(Material.ARROW).setName("§8» §3Next page");
    @Nullable
    protected TNLItem emptySlot = TNLItem.create(Material.RED_STAINED_GLASS_PANE).setName("§7-§8/§7-");

    public FormattedGUI(@Range(from = 1, to = 6) int rows, @Nonnull String title) {
        this(rows, title, null);
    }

    public FormattedGUI(@Range(from = 1, to = 6) int rows, @Nonnull String title, @Nullable Formatter formatter) {
        this(rows, 64, title, formatter);
    }

    public FormattedGUI(@Range(from = 1, to = 6) int rows, int maxStackSize, @Nonnull String title) {
        this(rows, maxStackSize, title, null);
    }

    public FormattedGUI(@Range(from = 1, to = 6) int rows, int maxStackSize, @Nonnull String title, @Nullable Formatter formatter) {
        super(rows, maxStackSize, title);
        this.formatter = formatter != null ? formatter : new Formatter() {
            @Nonnull
            @Override
            public List<GUIItem> getItems() {
                return new ArrayList<>();
            }
        };
        update();
    }

    @Nonnull
    @Override
    public FormattedGUI formatDefault() {
        TNLItem placeholder1 = TNLItem.create(Material.WHITE_STAINED_GLASS_PANE).setName("§7-§8/§7-");
        TNLItem placeholder2 = TNLItem.create(Material.GRAY_STAINED_GLASS_PANE).setName("§7-§8/§7-");
        IntStream.of(0, 8, getSize() - 1, getSize() - 9).forEach(slot -> setItem(slot, placeholder1));
        setItem(9, placeholder2);
        if (getRows() >= 2) setItem(17, placeholder2);
        if (getRows() >= 3) setItem(18, placeholder2);
        if (getRows() >= 3) setItem(26, placeholder2);
        if (getRows() >= 4) setItem(27, placeholder2);
        if (getRows() >= 4) setItem(35, placeholder2);
        if (getRows() >= 5) setItem(36, placeholder2);
        if (getRows() >= 5) setItem(44, placeholder2);
        for (int i = 1; i < 8; i++) setItem(i, placeholder2).setItem(getSize() - (i + 1), placeholder2);
        return this;
    }

    @Nonnull
    private FormattedGUI update() {
        return update(formatter.getItems());
    }

    @Nonnull
    private FormattedGUI update(@Nonnull List<GUIItem> items) {
        formatter.format(this);
        try {
            addItems(items.toArray(new GUIItem[]{}));
            if (emptySlot != null) for (int i = 0; i < getSize(); i++) setItemIfAbsent(i, emptySlot);
        } catch (GUIOverflowException e) {
            setItem(getSize() - 4, nextPage.clone().toGUIItem().
                    addInteractions(new Interaction(player1 -> {
                        GUIItem[] contents = getContents();
                        update(e.getOverflow()).setItem(getSize() - 6, previousPage.clone().toGUIItem().
                                addInteractions(new Interaction(player2 -> update().setContents(contents))));
                    })));
        }
        return this;
    }

    public static abstract class Formatter {

        public void format(@Nonnull FormattedGUI gui) {
            gui.clear().formatDefault();
            if (gui.previousPage.toGUIItem().hasInteractions()) gui.setItem(gui.getSize() - 6, gui.previousPage);
            gui.setItem(gui.getSize() - 5, gui.close);
            if (gui.nextPage.toGUIItem().hasInteractions()) gui.setItem(gui.getSize() - 4, gui.nextPage);
        }

        @Nonnull
        public abstract List<GUIItem> getItems();
    }
}
