package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import net.nonswag.tnl.listener.api.gui.iterators.InteractionIterator;
import net.nonswag.tnl.listener.api.item.TNLItem;

import javax.annotation.Nullable;
import java.util.*;

public abstract class GUIItem implements Iterable<Interaction> {

    private final List<Interaction> interactions = new ArrayList<>();

    protected GUIItem() {
    }

    public abstract TNLItem getItem();

    public List<Interaction> interactions() {
        return ImmutableList.copyOf(interactions);
    }

    public boolean hasInteractions() {
        return !interactions.isEmpty();
    }

    public GUIItem addInteractions(Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!this.interactions.contains(interaction)) this.interactions.add(interaction);
        }
        return this;
    }

    public GUIItem removeInteractions(Interaction... interactions) {
        this.interactions.removeAll(Arrays.asList(interactions));
        return this;
    }

    public GUIItem setInteractions(Interaction... interactions) {
        this.interactions.clear();
        for (Interaction interaction : interactions) addInteractions(interactions);
        return this;
    }

    public List<Interaction> getInteractions(Interaction.Type type) {
        List<Interaction> interactions = new ArrayList<>();
        for (Interaction interaction : this) {
            for (@Nullable Interaction.Type interactionType : interaction) {
                if (!type.comparable(interactionType != null ? interactionType : Interaction.Type.LEFT)) continue;
                interactions.add(interaction);
            }
        }
        return interactions;
    }

    @Override
    public Iterator<Interaction> iterator() {
        return new InteractionIterator(this);
    }

    public ListIterator<Interaction> iterator(int index) {
        return new InteractionIterator(this, index);
    }
}
