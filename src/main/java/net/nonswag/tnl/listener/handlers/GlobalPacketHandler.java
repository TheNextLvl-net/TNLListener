package net.nonswag.tnl.listener.handlers;

import net.nonswag.tnl.listener.Bootstrap;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GlobalPacketHandler {

    public static void init() {
        IncomingPacketHandler.init(Bootstrap.getInstance().getEventManager());
        OutgoingPacketHandler.init(Bootstrap.getInstance().getEventManager());
    }
}
