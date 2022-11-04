package net.nonswag.tnl.listener.api.item.interactive;

import com.google.common.collect.ImmutableList;
import net.nonswag.tnl.listener.api.item.TNLItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class InteractiveItem {

    private final List<Interaction> interactions = new ArrayList<>();

    protected InteractiveItem() {
    }

    public abstract TNLItem getItem();

    public List<Interaction> interactions() {
        return ImmutableList.copyOf(interactions);
    }

    public InteractiveItem addInteractions(Interaction... interactions) {
        for (Interaction interaction : interactions) {
            if (!this.interactions.contains(interaction)) this.interactions.add(interaction);
        }
        return this;
    }

    public InteractiveItem removeInteractions(Interaction... interactions) {
        this.interactions.removeAll(Arrays.asList(interactions));
        return this;
    }

    public List<Interaction> getInteractions(Interaction.Type type) {
        List<Interaction> interactions = new ArrayList<>();
        for (Interaction interaction : this.interactions) {
            for (@Nullable Interaction.Type interactionType : interaction.getTypes()) {
                if (type.comparable(interactionType != null ? interactionType : Interaction.Type.LEFT_CLICK)) {
                    interactions.add(interaction);
                }
            }
        }
        return interactions;
    }
}
