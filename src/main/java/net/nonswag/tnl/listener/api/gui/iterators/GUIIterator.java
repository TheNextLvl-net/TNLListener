package net.nonswag.tnl.listener.api.gui.iterators;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.gui.GUIItem;

import javax.annotation.Nullable;
import java.util.ListIterator;
import java.util.Objects;

@Getter
@Setter
public class GUIIterator implements ListIterator<GUIItem> {

    @Nullable
    private Boolean lastDirection;
    private final GUI gui;
    private int nextIndex;

    public GUIIterator(GUI gui) {
        this(gui, 0);
    }

    public GUIIterator(GUI gui, int index) {
        this.gui = gui;
        this.nextIndex = index;
    }

    @Override
    public boolean hasNext() {
        return this.nextIndex < getGui().getSize();
    }

    @Override
    public GUIItem next() {
        setLastDirection(true);
        return Objects.requireNonNull(getGui().getItem(this.nextIndex++));
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
    public GUIItem previous() {
        setLastDirection(false);
        return Objects.requireNonNull(getGui().getItem(this.nextIndex--));
    }

    public int previousIndex() {
        return getNextIndex() - 1;
    }

    @Override
    public void set(GUIItem item) {
        if (getLastDirection() == null) throw new IllegalStateException();
        getGui().setItem(getLastDirection() ? previousIndex() : getNextIndex(), item);
    }

    @Override
    public void add(GUIItem item) {
        getGui().addItem(item);
    }

    @Override
    public void remove() {
        if (getLastDirection() == null) throw new IllegalStateException();
        getGui().remove(getLastDirection() ? previousIndex() : getNextIndex());
    }
}
