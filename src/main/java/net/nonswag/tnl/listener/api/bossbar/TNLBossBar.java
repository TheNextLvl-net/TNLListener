package net.nonswag.tnl.listener.api.bossbar;

import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class TNLBossBar {

    public abstract String getId();

    public abstract String getText();

    public abstract BarColor getColor();

    public abstract BarStyle getStyle();

    public abstract BarFlag[] getBarFlags();

    public abstract double getProgress();

    public abstract TNLBossBar setText(String text);

    public abstract TNLBossBar setColor(BarColor color);

    public abstract TNLBossBar setStyle(BarStyle style);

    public abstract TNLBossBar setBarFlags(BarFlag... barFlags);

    public abstract TNLBossBar setProgress(double progress);

    public abstract BossBar getBossBar();

    public static TNLBossBar create(String id, String text, BarColor color, BarStyle style, double progress, BarFlag... barFlags) {
        return Mapping.get().createBossBar(id, text, color, style, progress, barFlags);
    }
}
