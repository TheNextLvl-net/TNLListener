package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.message.key.Key;
import net.nonswag.core.api.message.key.MessageKey;
import net.nonswag.core.api.message.key.SystemMessageKey;
import net.nonswag.core.api.platform.PlatformPlayer;
import net.nonswag.core.utils.StringUtil;
import net.nonswag.tnl.listener.api.chat.Conversation;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.packets.outgoing.CustomPayloadPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.SystemChatPacket;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Locale;

@Getter
@FieldsAreNullableByDefault
public abstract class Messenger extends Manager {
    private Conversation conversation = null;

    public void sendMessage(String message) {
        sendMessage(message, true);
    }

    public void sendMessage(String message, boolean validate) {
        SystemChatPacket.create(Component.text(validate ? Message.format(message, getPlayer()) : message), false).send(getPlayer());
    }

    public void sendMessage(String message, Placeholder... placeholders) {
        sendMessage(message, getPlayer(), placeholders);
    }

    public void sendMessage(String message, PlatformPlayer player, Placeholder... placeholders) {
        sendMessage(Message.format(message, player, placeholders), false);
    }

    public void sendMessage(Key key, Placeholder... placeholders) {
        sendMessage(key, getPlayer(), placeholders);
    }

    public void sendMessage(Key key, PlatformPlayer player, Placeholder... placeholders) {
        String message;
        if (key instanceof SystemMessageKey systemKey) message = systemKey.message();
        else if (key instanceof MessageKey messageKey) {
            message = Message.get(messageKey, getPlayer().data().getLanguage());
        }
        else message = key.message();
        sendMessage(Message.format(message, player, placeholders), false);
    }

    public void sendPluginMessage(String channel, String... message) {
        sendPluginMessage(getKey(channel), message);
    }

    public void sendPluginMessage(String channel, byte[]... message) {
        sendPluginMessage(getKey(channel), message);
    }

    public void sendPluginMessage(NamespacedKey channel, String... message) {
        if (Mapping.get().bungeeCord()) sendLegacyPluginMessage(channel, message);
        sendModernPluginMessage(channel, message);
    }

    public void sendLegacyPluginMessage(String channel, String... message) {
        sendLegacyPluginMessage(getKey(channel), message);
    }

    public void sendLegacyPluginMessage(NamespacedKey channel, String... message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            for (String s : message) dataOutputStream.writeUTF(s);
        } catch (IOException e) {
            Logger.error.println(e);
        }
        sendPluginMessage(channel, byteArrayOutputStream.toByteArray());
    }

    public void sendModernPluginMessage(NamespacedKey channel, String... message) {
        sendPluginMessage(channel, StringUtil.toByteArray(message));
    }

    public void sendPluginMessage(NamespacedKey channel, byte[]... bytes) {
        CustomPayloadPacket.create(channel, bytes).send(getPlayer());
    }

    public void startConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void stopConversation() {
        conversation = null;
    }

    public boolean isInConversation() {
        return conversation != null;
    }

    public static NamespacedKey getKey(String channel) {
        String key = Messenger.validateChannel(channel);
        NamespacedKey namespacedKey = NamespacedKey.fromString(key);
        if (namespacedKey == null) throw new IllegalArgumentException(key);
        return namespacedKey;
    }

    private static String validateChannel(String channel) {
        if (channel.equals("BungeeCord")) return "bungeecord:main";
        else if (channel.equals("bungeecord:main")) return "BungeeCord";
        else if (channel.length() > 64) throw new ChannelNameTooLongException(channel);
        else if (channel.indexOf(':') == -1) {
            throw new IllegalArgumentException("Channel must contain : separator (attempted to use " + channel + ")");
        } else return channel.toLowerCase(Locale.ROOT);
    }
}
