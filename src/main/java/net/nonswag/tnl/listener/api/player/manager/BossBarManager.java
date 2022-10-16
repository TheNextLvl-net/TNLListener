package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.bossbar.TNLBossBar;
import net.nonswag.tnl.listener.api.packets.BossBarPacket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BossBarManager extends Manager {

    @Nonnull
    private final HashMap<String, TNLBossBar> bossBars = new HashMap<>();
    @Nonnull
    private final HashMap<String, Thread> bossBarTimers = new HashMap<>();

    @Nonnull
    public List<TNLBossBar> getBossBars() {
        return new ArrayList<>(bossBars.values());
    }

    @Nullable
    public TNLBossBar getBossBar(@Nonnull String id) {
        return bossBars.get(id);
    }

    public void sendBossBar(@Nonnull TNLBossBar bossBar) {
        bossBars.put(bossBar.getId(), bossBar);
        BossBarPacket.create(BossBarPacket.Action.ADD, bossBar).send(getPlayer());
    }

    public void sendBossBar(@Nonnull TNLBossBar bossBar, long millis) {
        sendBossBar(bossBar);
        bossBarTimers.put(bossBar.getId(), Bootstrap.getInstance().async(() -> hideBossBar(bossBar), millis));
    }

    public void updateBossBar(@Nonnull TNLBossBar bossBar) {
        BossBarPacket.create(BossBarPacket.Action.UPDATE_NAME, bossBar).send(getPlayer());
        BossBarPacket.create(BossBarPacket.Action.UPDATE_PCT, bossBar).send(getPlayer());
        BossBarPacket.create(BossBarPacket.Action.UPDATE_PROPERTIES, bossBar).send(getPlayer());
        BossBarPacket.create(BossBarPacket.Action.UPDATE_STYLE, bossBar).send(getPlayer());
    }

    public void updateBossBar(@Nonnull TNLBossBar bossBar, long millis) {
        Thread thread = bossBarTimers.get(bossBar.getId());
        if (thread != null) {
            thread.interrupt();
            bossBarTimers.put(bossBar.getId(), Bootstrap.getInstance().async(() -> hideBossBar(bossBar), millis));
        }
        updateBossBar(bossBar);
    }

    public void hideBossBar(@Nonnull TNLBossBar bossBar) {
        bossBar = bossBars.getOrDefault(bossBar.getId(), bossBar);
        bossBars.remove(bossBar.getId());
        Thread thread = bossBarTimers.get(bossBar.getId());
        if (thread != null) {
            thread.interrupt();
            bossBarTimers.remove(bossBar.getId());
        }
        BossBarPacket.create(BossBarPacket.Action.REMOVE, bossBar).send(getPlayer());
    }
}
