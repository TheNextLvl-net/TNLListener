package net.nonswag.tnl.listener.api.mods.labymod.data.answer;

import net.nonswag.tnl.listener.api.mods.labymod.data.Addon;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import java.util.List;

public interface Answer {
    interface AddonRecommendation extends Answer {
        boolean send(@Nonnull TNLPlayer player, boolean guiClosed, boolean allInstalled, @Nonnull List<Addon> missing);
    }

    interface Cinematic extends Answer {
        void send(@Nonnull TNLPlayer player, boolean completed);
    }

    interface ServerConnectRequest extends Answer {
        void send(@Nonnull TNLPlayer player, boolean accepted);
    }
}
