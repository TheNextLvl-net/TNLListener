package net.nonswag.tnl.listener.api.advancement;

import com.google.gson.JsonObject;
import lombok.*;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.item.TNLItem;
import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Builder(builderClassName = "Builder")
public class Advancement {
    private NamespacedKey id;
    @Nullable
    private Advancement parent;
    @Nullable
    private DisplayInfo display;
    private Rewards rewards;
    private HashMap<String, Criterion<?>> criteria;
    private String[][] requirements;
    private final List<Advancement> children;
    private Component title;

    {
        setParent(parent);
    }

    public void setParent(@Nullable Advancement parent) {
        if ((this.parent = parent) != null) parent.children.add(this);
    }

    public void addChild(Advancement advancement) {
        advancement.setParent(this);
        children.add(advancement);
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Rewards {
        private final int experience;
        private final NamespacedKey[] loot;
        private final NamespacedKey[] recipes;
        @Nullable
        private final NamespacedKey function;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static abstract class Criterion<C> {
        private NamespacedKey id;

        public abstract JsonObject serialize(C context);

        @Getter
        @Setter
        @ToString
        @EqualsAndHashCode
        @AllArgsConstructor
        public static class Progress {
            @Nullable
            private Date dateObtained;
        }
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class DisplayInfo {
        private TNLItem icon;
        private Component title, description;
        @Nullable
        private NamespacedKey background;
        private FrameType frame;
        private boolean showToast, announceChat, hidden;
        private float x, y;
    }

    public enum FrameType {
        TASK, CHALLENGE, GOAL
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Progress {
        private HashMap<String, Criterion.Progress> progress;
        private String[][] requirements;
    }
}
