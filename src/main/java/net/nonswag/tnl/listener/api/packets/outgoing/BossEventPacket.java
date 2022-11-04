package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.boss.BossBar;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BossEventPacket extends PacketBuilder {

    private Action action;
    private BossBar bossBar;

    public static BossEventPacket create(Action action, BossBar bossBar) {
        return Mapping.get().packetManager().outgoing().bossEventPacket(action, bossBar);
    }

    public static BossEventPacket create(Action action, TNLBossBar bossBar) {
        return create(action, bossBar.getBossBar());
    }

    public enum Action {
        ADD, REMOVE, UPDATE_PCT, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES
    }
}
