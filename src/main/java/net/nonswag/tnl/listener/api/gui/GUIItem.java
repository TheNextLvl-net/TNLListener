package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import net.nonswag.tnl.listener.api.gui.iterators.InteractionIterator;
import net.nonswag.tnl.listener.api.item.TNLItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class GUIItem implements Iterable<Interaction> {

    @Nonnull
    private final List<Interaction> interactions = new ArrayList<>();

    protected GUIItem() {
    }

    @Nonnull
    public abstract TNLItem getItem();

    @Nonnull
    public List<Interaction> interactions() {
        return ImmutableList.copyOf(interactions);
    }

    public boolean hasInteractions() {
        return !interactions.isEmpty();
    }

    @Nonnull
    public GUIItem addInteractions(@Nonnull Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!this.interactions.contains(interaction)) this.interactions.add(interaction);
        }
        return this;
    }

    @Nonnull
    public GUIItem removeInteractions(@Nonnull Interaction... interactions) {
        this.interactions.removeAll(Arrays.asList(interactions));
        return this;
    }

    @Nonnull
    public GUIItem setInteractions(@Nonnull Interaction... interactions) {
        this.interactions.clear();
        for (Interaction interaction : interactions) addInteractions(interactions);
        return this;
    }

    @Nonnull
    public List<Interaction> getInteractions(@Nonnull Interaction.Type type) {
        List<Interaction> interactions = new ArrayList<>();
        for (Interaction interaction : this) {
            for (@Nullable Interaction.Type interactionType : interaction) {
                if (!type.comparable(interactionType != null ? interactionType : Interaction.Type.LEFT)) continue;
                interactions.add(interaction);
            }
        }
        return interactions;
    }

    @Nonnull
    @Override
    public Iterator<Interaction> iterator() {
        return new InteractionIterator(this);
    }

    @Nonnull
    public ListIterator<Interaction> iterator(int index) {
        return new InteractionIterator(this, index);
    }
}
