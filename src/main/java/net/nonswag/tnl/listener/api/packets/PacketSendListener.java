package net.nonswag.tnl.listener.api.packets;

import net.nonswag.core.api.annotation.MethodsReturnNullableByDefault;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNullableByDefault
public interface PacketSendListener {

    void onSuccess(TNLPlayer player);

    default <P> P onFailure(TNLPlayer player) {
        return null;
    }
}
