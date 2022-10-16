package net.nonswag.tnl.listener.api.packets;

import javax.annotation.Nullable;

public interface PacketSendListener {

    void onSuccess();

    @Nullable
    default <P> P onFailure() {
        return null;
    }
}
