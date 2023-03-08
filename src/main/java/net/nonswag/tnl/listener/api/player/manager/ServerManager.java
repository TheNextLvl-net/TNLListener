package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.server.Server;
import net.nonswag.tnl.listener.utils.Messages;

public abstract class ServerManager extends Manager {

    public void connectUnchecked(String server) {
        getPlayer().messenger().sendMessage(Messages.CONNECTING_TO_SERVER, new Placeholder("server", server));
        getPlayer().messenger().sendLegacyPluginMessage("BungeeCord", "Connect", server);
    }

    public void connect(String server) {
        Server wrap = Server.wrap(server);
        if (wrap != null) connect(wrap);
        else getPlayer().messenger().sendMessage(Messages.UNKNOWN_SERVER, new Placeholder("server", server));
    }

    public void connect(Server server) {
        try {
            if (!server.getStatus().isOffline()) connectUnchecked(server.getName());
            else {
                getPlayer().messenger().sendMessage(Messages.SERVER_IS_OFFLINE, new Placeholder("server", server.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            getPlayer().messenger().sendMessage(Messages.FAILED_TO_CONNECT_TO_SERVER, new Placeholder("server", server.getName()));
        }
    }
}
