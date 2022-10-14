package net.nonswag.tnl.listener.api.bossbar;

import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import javax.annotation.Nonnull;

public abstract class TNLBossBar {

    @Nonnull
    public abstract String getId();

    @Nonnull
    public abstract String getText();

    @Nonnull
    public abstract BarColor getColor();

    @Nonnull
    public abstract BarStyle getStyle();

    @Nonnull
    public abstract BarFlag[] getBarFlags();

    public abstract double getProgress();

    @Nonnull
    public abstract TNLBossBar setText(@Nonnull String text);

    @Nonnull
    public abstract TNLBossBar setColor(@Nonnull BarColor color);

    @Nonnull
    public abstract TNLBossBar setStyle(@Nonnull BarStyle style);

    @Nonnull
    public abstract TNLBossBar setBarFlags(@Nonnull BarFlag... barFlags);

    @Nonnull
    public abstract TNLBossBar setProgress(double progress);

    @Nonnull
    public abstract BossBar getBossBar();

    @Nonnull
    public static TNLBossBar create(@Nonnull String id, @Nonnull String text, @Nonnull BarColor color, @Nonnull BarStyle style, double progress, @Nonnull BarFlag... barFlags) {
        return Mapping.get().createBossBar(id, text, color, style, progress, barFlags);
    }
}
