package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import net.nonswag.tnl.listener.api.item.TNLItem;

import java.util.*;

public abstract class GUIItem {

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
            if (interaction != null && !this.interactions.contains(interaction)) this.interactions.add(interaction);
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

    public List<Interaction> interactions(Interaction.Type type) {
        List<Interaction> interactions = new ArrayList<>();
        this.interactions.forEach(interaction -> {
            for (Interaction.Type interactionType : interaction.types()) {
                if (!interactionType.comparable(type)) continue;
                interactions.add(interaction);
                break;
            }
        });
        return interactions;
    }
}
