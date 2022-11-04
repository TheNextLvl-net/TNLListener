package net.nonswag.tnl.listener.api.gui.iterators;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.gui.Interaction;

import javax.annotation.Nullable;
import java.util.ListIterator;

@Getter
@Setter
public class TypeIterator implements ListIterator<Interaction.Type> {

    @Nullable
    private Boolean lastDirection;
    private final Interaction interaction;
    private int nextIndex;

    public TypeIterator(Interaction interaction) {
        this(interaction, 0);
    }

    public TypeIterator(Interaction interaction, int index) {
        this.interaction = interaction;
        this.nextIndex = index;
    }

    @Override
    public boolean hasNext() {
        return this.nextIndex < getInteraction().getTypes().size();
    }

    @Override
    public Interaction.Type next() {
        setLastDirection(true);
        return getInteraction().getTypes().get(this.nextIndex++);
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
    public Interaction.Type previous() {
        setLastDirection(false);
        return getInteraction().getTypes().get(this.nextIndex--);
    }

    public int previousIndex() {
        return getNextIndex() - 1;
    }

    @Override
    public void set(Interaction.Type type) {
        if (getLastDirection() == null) throw new IllegalStateException();
        getInteraction().getTypes().set(getLastDirection() ? previousIndex() : getNextIndex(), type);
    }

    @Override
    public void add(Interaction.Type type) {
        getInteraction().getTypes().add(type);
    }

    @Override
    public void remove() {
        if (getLastDirection() == null) throw new IllegalStateException();
        getInteraction().getTypes().remove(getLastDirection() ? previousIndex() : getNextIndex());
    }
}
