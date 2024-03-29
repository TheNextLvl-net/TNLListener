package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.entity.TNLEntity;
import net.nonswag.tnl.listener.api.player.Skin;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class SkinManager extends Manager {

    protected boolean cape = true;

    public abstract Skin getSkin();

    public void setSkin(@Nullable Skin skin) {
        setSkin(skin, Listener.getOnlinePlayers());
    }

    public void setSkin(@Nullable Skin skin, TNLPlayer receiver) {
        FakePlayer fakePlayer = new FakePlayer(getPlayer().getName(), getPlayer().worldManager().getLocation(), skin, getPlayer().getUniqueId());
        disguise(fakePlayer.getPlayer(), receiver);
    }

    public void setSkin(@Nullable Skin skin, TNLPlayer... receivers) {
        setSkin(skin, Arrays.asList(receivers));
    }

    public void setSkin(@Nullable Skin skin, List<TNLPlayer> receivers) {
        for (TNLPlayer receiver : receivers) setSkin(skin, receiver);
    }

    public abstract void disguise(TNLEntity entity, TNLPlayer receiver);

    public void disguise(TNLEntity entity, List<TNLPlayer> receivers) {
        for (TNLPlayer receiver : receivers) disguise(entity, receiver);
    }

    public void disguise(TNLEntity entity) {
        disguise(entity, Listener.getOnlinePlayers());
    }

    public abstract void setCapeVisibility(boolean visible);

    public boolean getCapeVisibility() {
        return cape;
    }
}
