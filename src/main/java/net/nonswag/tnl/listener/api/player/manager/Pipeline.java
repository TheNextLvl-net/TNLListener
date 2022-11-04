package net.nonswag.tnl.listener.api.player.manager;

import net.kyori.adventure.text.Component;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.message.key.Key;
import net.nonswag.core.api.message.key.MessageKey;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.PacketSendListener;

import javax.annotation.Nullable;

public abstract class Pipeline extends Manager {

    public <P> void sendPacket(P packet) {
        sendPacket(packet, null);
    }

    public abstract <P> void sendPacket(P packet, @Nullable PacketSendListener listener);

    public void disconnect() {
        disconnect("%prefix%\n§cDisconnected");
    }

    public void disconnect(String reason, Placeholder... placeholders) {
        disconnect(reason, true, placeholders);
    }

    public void disconnect(String reason, boolean validate, Placeholder... placeholders) {
        String message = (validate ? Message.format(reason, getPlayer(), placeholders) : reason);
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().kick(Component.text(message)));
    }

    public void disconnect(MessageKey messageKey, Placeholder... placeholders) {
        disconnect(messageKey, "", placeholders);
    }

    public void disconnect(Key key, String append, Placeholder... placeholders) {
        disconnect(key instanceof MessageKey m ? m.message(getPlayer().data().getLanguage()) : key.message() + append, placeholders);
    }

    public void updateCommands() {
        getPlayer().bukkit().updateCommands();
    }

    public abstract boolean isInjected();

    public abstract void uninject();

    public abstract void inject();
}
