package net.nonswag.tnl.listener.api.title;

import lombok.Getter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@Getter
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

    @Nonnull
    public Title setTitle(@Nonnull String title) {
        this.title = title;
        return this;
    }

    @Nonnull
    public Title setSubtitle(@Nonnull String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    @Nonnull
    public Title setTimeIn(int timeIn) {
        this.timeIn = timeIn;
        return this;
    }

    @Nonnull
    public Title setTimeStay(int timeStay) {
        this.timeStay = timeStay;
        return this;
    }

    @Nonnull
    public Title setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public boolean hasTitle() {
        return getTitle() != null;
    }

    public boolean hasSubtitle() {
        return getSubtitle() != null;
    }

    @Nonnull
    public Animation animate(@Nonnull Design design) {
        return new Animation(this, design);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Title title1 = (Title) o;
        return timeIn == title1.timeIn && timeStay == title1.timeStay && timeOut == title1.timeOut && Objects.equals(title, title1.title) && Objects.equals(subtitle, title1.subtitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitle, timeIn, timeStay, timeOut);
    }

    @Override
    public String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", timeIn=" + timeIn +
                ", timeStay=" + timeStay +
                ", timeOut=" + timeOut +
                '}';
    }

    @Getter
    public static final class Animation extends Title {
        @Nonnull
        private final Design design;

        private Animation(@Nonnull Title title, @Nonnull Design design) {
            super(title.getTitle(), title.getSubtitle(), title.getTimeIn(), title.getTimeStay(), title.getTimeOut());
            this.design = design;
        }

        @Override
        public String toString() {
            return "Animation{" +
                    "title='" + getTitle() + '\'' +
                    ", subtitle='" + getSubtitle() + '\'' +
                    ", timeIn=" + getTimeIn() +
                    ", timeStay=" + getTimeStay() +
                    ", timeOut=" + getTimeOut() +
                    ", design=" + getDesign() +
                    '}';
        }

        public interface Finished {
            void finished(boolean success);
        }
    }

    public record Design(@Nonnull Color primaryColor, @Nonnull Color secondaryColor, @Nonnull Color extraColor) {
        @Nonnull
        public static final Design LIGHT = new Design(Color.GREEN, Color.GRAY, Color.GOLD);
        @Nonnull
        public static final Design BLOODY = new Design(Color.DARK_RED, Color.DARK_GRAY, Color.RED);
        @Nonnull
        public static final Design DARK = new Design(Color.RED, Color.DARK_GRAY, Color.GRAY);
        @Nonnull
        public static final Design OCEAN = new Design(Color.DARK_AQUA, Color.GRAY, Color.AQUA);
    }

    public record Color(@Nonnull String code) {

        @Nonnull
        public static Color BLACK = new Color("§0");
        @Nonnull
        public static Color DARK_BLUE = new Color("§1");
        @Nonnull
        public static Color DARK_GREEN = new Color("§2");
        @Nonnull
        public static Color DARK_AQUA = new Color("§3");
        @Nonnull
        public static Color DARK_RED = new Color("§4");
        @Nonnull
        public static Color DARK_PURPLE = new Color("§5");
        @Nonnull
        public static Color GOLD = new Color("§6");
        @Nonnull
        public static Color GRAY = new Color("§7");
        @Nonnull
        public static Color DARK_GRAY = new Color("§8");
        @Nonnull
        public static Color BLUE = new Color("§9");
        @Nonnull
        public static Color GREEN = new Color("§a");
        @Nonnull
        public static Color AQUA = new Color("§b");
        @Nonnull
        public static Color RED = new Color("§c");
        @Nonnull
        public static Color LIGHT_PURPLE = new Color("§d");
        @Nonnull
        public static Color YELLOW = new Color("§e");
        @Nonnull
        public static Color WHITE = new Color("§f");
        @Nonnull
        public static Color MATRIX = new Color("§k");
        @Nonnull
        public static Color BOLD = new Color("§l");
        @Nonnull
        public static Color STRIKETHROUGH = new Color("§m");
        @Nonnull
        public static Color UNDERLINE = new Color("§n");
        @Nonnull
        public static Color ITALIC = new Color("§o");
        @Nonnull
        public static Color RESET = new Color("§r");

        @Override
        public String toString() {
            return code();
        }
    }
}
