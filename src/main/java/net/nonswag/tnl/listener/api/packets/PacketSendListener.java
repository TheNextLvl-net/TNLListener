package net.nonswag.tnl.listener.api.packets;

import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PacketSendListener {

    void onSuccess(@Nonnull TNLPlayer player);

    @Nullable
    default <P> P onFailure(@Nonnull TNLPlayer player) {
        return null;
    }
}
