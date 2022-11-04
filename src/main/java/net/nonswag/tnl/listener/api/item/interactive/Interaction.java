package net.nonswag.tnl.listener.api.item.interactive;

import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Interaction implements Cloneable {

    public static final Interaction EMPTY = new Interaction(player -> {
    });

    private final List<Type> types = new ArrayList<>();
    private final Consumer<TNLPlayer> action;

    public Interaction(Consumer<TNLPlayer> action, List<Type> types) {
        this.action = action;
        for (@Nullable Type type : types.size() == 0 ? Collections.singletonList(Type.GENERAL) : types) {
            if (type != null && !this.types.contains(type)) this.types.add(type);
        }
    }

    public Interaction(Consumer<TNLPlayer> action, Type... types) {
        this(action, Arrays.asList(types));
    }

    public Interaction(Type type, Consumer<TNLPlayer> action) {
        this(action, type);
    }

    public Interaction(Type[] types, Consumer<TNLPlayer> action) {
        this(action, types);
    }

    public Interaction(Consumer<TNLPlayer> action) {
        this(Type.GENERAL, action);
    }

    public List<Type> getTypes() {
        return types;
    }

    public Consumer<TNLPlayer> getAction() {
        return action;
    }

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

        public boolean comparable(Type type) {
            return equals(type) || type.equals(GENERAL);
        }
    }
}
