package net.nonswag.tnl.listener.api.item.interactive;

import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Interaction implements Cloneable {

    @Nonnull
    public static final Interaction EMPTY = new Interaction(player -> {
    });

    @Nonnull
    private final List<Type> types = new ArrayList<>();
    @Nonnull
    private final Consumer<TNLPlayer> action;

    public Interaction(@Nonnull Consumer<TNLPlayer> action, @Nonnull List<Type> types) {
        this.action = action;
        for (@Nullable Type type : types.size() == 0 ? Collections.singletonList(Type.GENERAL) : types) {
            if (type != null && !this.types.contains(type)) this.types.add(type);
        }
    }

    public Interaction(@Nonnull Consumer<TNLPlayer> action, @Nonnull Type... types) {
        this(action, Arrays.asList(types));
    }

    public Interaction(@Nonnull Type type, @Nonnull Consumer<TNLPlayer> action) {
        this(action, type);
    }

    public Interaction(@Nonnull Type[] types, @Nonnull Consumer<TNLPlayer> action) {
        this(action, types);
    }

    public Interaction(@Nonnull Consumer<TNLPlayer> action) {
        this(Type.GENERAL, action);
    }

    @Nonnull
    public List<Type> getTypes() {
        return types;
    }

    @Nonnull
    public Consumer<TNLPlayer> getAction() {
        return action;
    }

    @Nonnull
    @Override
    public Interaction clone() {
        return new Interaction(getAction(), getTypes());
    }

    public enum Type {
        GENERAL,
        LEFT_CLICK,
        RIGHT_CLICK,

        /*
        SWAP_HANDS,
        DROP,
        DROP_ALL,
        SELECT_HOTBAR_SLOT,
        DESELECT_HOTBAR_SLOT,
         */
        ;

        Type() {
        }

        public boolean comparable(@Nonnull Type type) {
            return equals(type) || type.equals(GENERAL);
        }
    }
}
