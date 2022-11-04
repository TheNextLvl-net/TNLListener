package net.nonswag.tnl.listener.api.gui;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.nonswag.tnl.listener.api.gui.iterators.TypeIterator;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.event.inventory.ClickType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

@Getter
@ToString
@EqualsAndHashCode
public class Interaction implements Iterable<Interaction.Type>, Cloneable {

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

    @Override
    public Interaction clone() {
        return new Interaction(getAction(), getTypes());
    }

    @Getter
    public enum Type {
        GENERAL,
        RIGHT(ClickType.RIGHT),
        LEFT(ClickType.LEFT),
        MIDDLE(ClickType.MIDDLE),
        SHIFT_RIGHT(ClickType.SHIFT_RIGHT),
        SHIFT_LEFT(ClickType.SHIFT_LEFT),
        NUMBER_KEY_GENERAL(ClickType.NUMBER_KEY),
        NUMBER_KEY_1(ClickType.NUMBER_KEY),
        NUMBER_KEY_2(ClickType.NUMBER_KEY),
        NUMBER_KEY_3(ClickType.NUMBER_KEY),
        NUMBER_KEY_4(ClickType.NUMBER_KEY),
        NUMBER_KEY_5(ClickType.NUMBER_KEY),
        NUMBER_KEY_6(ClickType.NUMBER_KEY),
        NUMBER_KEY_7(ClickType.NUMBER_KEY),
        NUMBER_KEY_8(ClickType.NUMBER_KEY),
        NUMBER_KEY_9(ClickType.NUMBER_KEY),
        OFF_HAND(ClickType.NUMBER_KEY),
        DOUBLE_CLICK(ClickType.DOUBLE_CLICK),
        DROP(ClickType.DROP),
        DROP_ALL(ClickType.CONTROL_DROP);

        private final ClickType type;

        Type(ClickType type) {
            this.type = type;
        }

        Type() {
            this.type = ClickType.UNKNOWN;
        }

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

        public boolean equals(ClickType type) {
            if (equals(GENERAL)) return true;
            return getType().equals(type);
        }

        public boolean comparable(Type type) {
            if (equals(GENERAL) || type.equals(GENERAL)) return true;
            return equals(type);
        }
    }

    @Override
    public Iterator<Type> iterator() {
        return new TypeIterator(this);
    }

    public ListIterator<Type> iterator(int i) {
        return new TypeIterator(this, i);
    }
}
