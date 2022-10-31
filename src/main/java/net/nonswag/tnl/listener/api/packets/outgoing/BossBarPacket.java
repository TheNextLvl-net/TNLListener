package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.boss.BossBar;

import javax.annotation.Nonnull;

@Getter
@Setter
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
    public static BossBarPacket create(@Nonnull Action action, @Nonnull BossBar bossBar) {
        return Mapping.get().packetManager().outgoing().bossBarPacket(action, bossBar);
    }

    @Nonnull
    public static BossBarPacket create(@Nonnull Action action, @Nonnull TNLBossBar bossBar) {
        return create(action, bossBar.getBossBar());
    }

    public enum Action {
        ADD, REMOVE, UPDATE_PCT, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES
    }
}
