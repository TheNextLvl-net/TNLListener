package net.nonswag.tnl.listener.api.mods.labymod.data.answer;

import net.nonswag.tnl.listener.api.mods.labymod.data.Addon;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public interface Answer {
    @FunctionalInterface
    interface AddonRecommendation extends Answer {
        boolean send(TNLPlayer player, boolean guiClosed, boolean allInstalled, List<Addon> missing);
    }

    @FunctionalInterface
    interface Cinematic extends Answer {
        void send(TNLPlayer player, boolean completed);
    }

    @FunctionalInterface
    interface ServerConnectRequest extends Answer {
        void send(TNLPlayer player, boolean accepted);
    }
}
