package net.nonswag.tnl.listener.api.gui;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@ToString
@EqualsAndHashCode
public class Interaction implements Cloneable {
    @Getter
    @Accessors(fluent = true)
    private final Consumer<TNLPlayer> action;
    private final List<Type> types = new ArrayList<>();

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

    public List<Type> types() {
        return ImmutableList.copyOf(types);
    }

    @Override
    public Interaction clone() {
        return new Interaction(action(), types());
    }

    @Getter
    public enum Type {
        GENERAL,
        RIGHT, LEFT, DOUBLE_CLICK,
        SHIFT_RIGHT, SHIFT_LEFT,
        NUMBER_KEY_GENERAL,
        NUMBER_KEY_1,
        NUMBER_KEY_2,
        NUMBER_KEY_3,
        NUMBER_KEY_4,
        NUMBER_KEY_5,
        NUMBER_KEY_6,
        NUMBER_KEY_7,
        NUMBER_KEY_8,
        NUMBER_KEY_9,
        OFF_HAND, MIDDLE,
        DROP, DROP_ALL;

        public boolean isKeyboardClick() {
            return isNumberClick() || equals(DROP) || equals(DROP_ALL) || equals(OFF_HAND);
        }

        public boolean isNumberClick() {
            return equals(NUMBER_KEY_GENERAL) || equals(NUMBER_KEY_1) || equals(NUMBER_KEY_2)
                    || equals(NUMBER_KEY_3) || equals(NUMBER_KEY_4) || equals(NUMBER_KEY_5)
                    || equals(NUMBER_KEY_6) || equals(NUMBER_KEY_7) || equals(NUMBER_KEY_8)
                    || equals(NUMBER_KEY_9) || equals(GENERAL);
        }

        public boolean isRightClick() {
            return equals(RIGHT) || equals(SHIFT_RIGHT) || equals(GENERAL);
        }

        public boolean isLeftClick() {
            return equals(LEFT) || equals(SHIFT_LEFT) || equals(DOUBLE_CLICK) || equals(GENERAL);
        }

        public boolean isShiftClick() {
            return equals(SHIFT_LEFT) || equals(SHIFT_RIGHT) || equals(DROP_ALL) || equals(GENERAL);
        }

        public boolean comparable(Type type) {
            if (equals(GENERAL) || type.equals(GENERAL)) return true;
            return equals(type);
        }
    }
}
