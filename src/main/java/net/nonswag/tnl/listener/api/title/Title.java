package net.nonswag.tnl.listener.api.title;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Title {

    @Nullable
    private String title, subtitle;
    private int timeIn, timeStay, timeOut;

    public Title(@Nullable Object title, @Nullable Object subtitle, int timeIn, int timeStay, int timeOut) {
        this.title = title == null ? null : title.toString();
        this.subtitle = subtitle == null ? null : subtitle.toString();
        this.timeIn = timeIn;
        this.timeStay = timeStay;
        this.timeOut = timeOut;
    }

    public Title(@Nullable Object title, @Nullable Object subtitle) {
        this(title, subtitle, 0, 70, 10);
    }

    public Title() {
        this(null, null);
    }

    public Animation animate(Design design) {
        return new Animation(this, design);
    }

    @Getter
    @ToString
    @EqualsAndHashCode(callSuper = true)
    public static final class Animation extends Title {
        private final Design design;

        private Animation(Title title, Design design) {
            super(title.getTitle(), title.getSubtitle(), title.getTimeIn(), title.getTimeStay(), title.getTimeOut());
            this.design = design;
        }

        public interface Finished {
            void finished(boolean success);
        }
    }

    public record Design(ChatColor primaryColor, ChatColor secondaryColor, ChatColor extraColor) {
        public static final Design LIGHT = new Design(ChatColor.GREEN, ChatColor.GRAY, ChatColor.GOLD);
        public static final Design BLOODY = new Design(ChatColor.DARK_RED, ChatColor.DARK_GRAY, ChatColor.RED);
        public static final Design DARK = new Design(ChatColor.RED, ChatColor.DARK_GRAY, ChatColor.GRAY);
        public static final Design OCEAN = new Design(ChatColor.DARK_AQUA, ChatColor.GRAY, ChatColor.AQUA);
    }
}
