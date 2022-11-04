package net.nonswag.tnl.listener.api.gui.iterators;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import net.nonswag.tnl.listener.api.gui.Interaction;

import javax.annotation.Nullable;
import java.util.ListIterator;

@Getter
@Setter
public class InteractionIterator implements ListIterator<Interaction> {

    @Nullable
    private Boolean lastDirection;
    private final GUIItem guiItem;
    private int nextIndex;

    public InteractionIterator(GUIItem guiItem) {
        this(guiItem, 0);
    }

    public InteractionIterator(GUIItem guiItem, int index) {
        this.guiItem = guiItem;
        this.nextIndex = index;
    }

    @Override
    public boolean hasNext() {
        return this.nextIndex < getGuiItem().interactions().size();
    }

    @Override
    public Interaction next() {
        setLastDirection(true);
        return getGuiItem().interactions().get(this.nextIndex++);
    }

    @Override
    public int nextIndex() {
        return getNextIndex();
    }

    @Override
    public boolean hasPrevious() {
        return getNextIndex() > 0;
    }

    @Override
    public Interaction previous() {
        setLastDirection(false);
        return getGuiItem().interactions().get(this.nextIndex--);
    }

    public int previousIndex() {
        return getNextIndex() - 1;
    }

    @Override
    public void set(Interaction interaction) {
        if (getLastDirection() == null) throw new IllegalStateException();
        getGuiItem().interactions().set(getLastDirection() ? previousIndex() : getNextIndex(), interaction);
    }

    @Override
    public void add(Interaction interaction) {
        getGuiItem().addInteractions(interaction);
    }

    @Override
    public void remove() {
        if (getLastDirection() == null) throw new IllegalStateException();
        getGuiItem().interactions().remove(getLastDirection() ? previousIndex() : getNextIndex());
    }
}
