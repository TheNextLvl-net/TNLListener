package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.packets.outgoing.BossEventPacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BossBarManager extends Manager {

    private final HashMap<String, TNLBossBar> bossBars = new HashMap<>();
    private final HashMap<String, Thread> bossBarTimers = new HashMap<>();

    public List<TNLBossBar> getBossBars() {
        return new ArrayList<>(bossBars.values());
    }

    @Nullable
    public TNLBossBar getBossBar(String id) {
        return bossBars.get(id);
    }

    public void sendBossBar(TNLBossBar bossBar) {
        bossBars.put(bossBar.getId(), bossBar);
        BossEventPacket.create(BossEventPacket.Action.ADD, bossBar).send(getPlayer());
    }

    public void sendBossBar(TNLBossBar bossBar, long millis) {
        sendBossBar(bossBar);
        bossBarTimers.put(bossBar.getId(), Bootstrap.getInstance().async(() -> hideBossBar(bossBar), millis));
    }

    public void updateBossBar(TNLBossBar bossBar) {
        BossEventPacket.create(BossEventPacket.Action.UPDATE_NAME, bossBar).send(getPlayer());
        BossEventPacket.create(BossEventPacket.Action.UPDATE_PCT, bossBar).send(getPlayer());
        BossEventPacket.create(BossEventPacket.Action.UPDATE_PROPERTIES, bossBar).send(getPlayer());
        BossEventPacket.create(BossEventPacket.Action.UPDATE_STYLE, bossBar).send(getPlayer());
    }

    public void updateBossBar(TNLBossBar bossBar, long millis) {
        Thread thread = bossBarTimers.get(bossBar.getId());
        if (thread != null) {
            thread.interrupt();
            bossBarTimers.put(bossBar.getId(), Bootstrap.getInstance().async(() -> hideBossBar(bossBar), millis));
        }
        updateBossBar(bossBar);
    }

    public void hideBossBar(TNLBossBar bossBar) {
        bossBar = bossBars.getOrDefault(bossBar.getId(), bossBar);
        bossBars.remove(bossBar.getId());
        Thread thread = bossBarTimers.get(bossBar.getId());
        if (thread != null) {
            thread.interrupt();
            bossBarTimers.remove(bossBar.getId());
        }
        BossEventPacket.create(BossEventPacket.Action.REMOVE, bossBar).send(getPlayer());
    }
}
