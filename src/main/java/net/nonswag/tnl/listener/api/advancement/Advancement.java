package net.nonswag.tnl.listener.api.advancement;

import com.google.gson.JsonElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
@Builder
@AllArgsConstructor
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Advancement {
    private NamespacedKey id;
    @Nullable
    private Advancement parent;
    @Nullable
    private DisplayInfo display;
    private Rewards rewards;
    private HashMap<String, Criterion> criteria;
    private String[][] requirements;
    private List<Advancement> children;
    private Component title;

    public int getMaxCriteriaRequired() {
        return this.requirements.length;
    }

    @Getter
    @Setter
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
    @AllArgsConstructor
    public static abstract class Criterion {
        private NamespacedKey id;

        public abstract JsonElement serialize();

        @Getter
        @Setter
        @AllArgsConstructor
        public static class Progress {
            @Nullable
            private Date dateObtained;
        }
    }

    @Getter
    @Setter
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
    @AllArgsConstructor
    public static class Progress {
        private HashMap<String, Criterion.Progress> progress;
        private String[][] requirements;
    }
}
