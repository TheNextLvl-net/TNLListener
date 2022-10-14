package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.boss.BossBar;

import javax.annotation.Nonnull;

@Getter
public abstract class BossBarPacket extends PacketBuilder {

    @Nonnull
    private Action action;
    @Nonnull
    private BossBar bossBar;

    protected BossBarPacket(@Nonnull Action action, @Nonnull BossBar bossBar) {
        this.action = action;
        this.bossBar = bossBar;
    }

    @Nonnull
    public BossBarPacket setAction(@Nonnull Action action) {
        this.action = action;
        return this;
    }

    @Nonnull
    public BossBarPacket setBossBar(@Nonnull BossBar bossBar) {
        this.bossBar = bossBar;
        return this;
    }

    @Nonnull
    public static BossBarPacket create(@Nonnull Action action, @Nonnull BossBar bossBar) {
        return Mapping.get().packets().bossBarPacket(action, bossBar);
    }

    @Nonnull
    public static BossBarPacket create(@Nonnull Action action, @Nonnull TNLBossBar bossBar) {
        return create(action, bossBar.getBossBar());
    }

    public enum Action {
        ADD, REMOVE, UPDATE_PCT, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES
    }
}
