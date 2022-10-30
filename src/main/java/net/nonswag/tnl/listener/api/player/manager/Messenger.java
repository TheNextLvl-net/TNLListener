package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.nonswag.core.api.logger.Color;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.message.key.Key;
import net.nonswag.core.api.message.key.MessageKey;
import net.nonswag.core.api.message.key.SystemMessageKey;
import net.nonswag.core.api.platform.PlatformPlayer;
import net.nonswag.core.utils.StringUtil;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.chat.Conversation;
import net.nonswag.tnl.listener.api.packets.outgoing.ChatPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.CustomPayloadPacket;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.events.ChatMentionEvent;
import net.nonswag.tnl.listener.events.PlayerChatEvent;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.plugin.messaging.ChannelNameTooLongException;
import org.spigotmc.SpigotConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public abstract class Messenger extends Manager {

    @Getter
    @Nullable
    private Conversation conversation = null;

    public void sendMessage(@Nonnull String message) {
        sendMessage(message, true);
    }

    public void sendMessage(@Nonnull String message, boolean validate) {
        sendMessage((UUID) null, message, validate);
    }

    public void sendMessage(@Nullable UUID sender, @Nonnull String message) {
        sendMessage(sender, message, true);
    }

    public void sendMessage(@Nonnull TNLPlayer sender, @Nonnull String message) {
        sendMessage(sender.getUniqueId(), message);
    }

    public void sendMessage(@Nullable UUID sender, @Nonnull String message, boolean validate) {
        sendMessage(ChatPacket.Type.SYSTEM, sender, message, validate);
    }

    public void sendMessage(@Nonnull TNLPlayer sender, @Nonnull String message, boolean validate) {
        sendMessage(sender.getUniqueId(), message, validate);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull String message) {
        sendMessage(type, message, true);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull String message, boolean validate) {
        sendMessage(type, (UUID) null, message, validate);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull TNLPlayer sender, @Nonnull String message, boolean validate) {
        sendMessage(type, sender.getUniqueId(), message, validate);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull String message, @Nonnull Placeholder... placeholders) {
        sendMessage(type, message, getPlayer(), placeholders);
    }

    public void sendMessage(@Nonnull String message, @Nonnull Placeholder... placeholders) {
        sendMessage(message, getPlayer(), placeholders);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull String message, @Nonnull TNLPlayer player, @Nonnull Placeholder... placeholders) {
        sendMessage(type, player, Message.format(message, player, placeholders), false);
    }

    public void sendMessage(@Nonnull String message, @Nonnull TNLPlayer player, @Nonnull Placeholder... placeholders) {
        sendMessage(player, Message.format(message, player, placeholders), false);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull Key key, @Nonnull PlatformPlayer platformPlayer, @Nonnull Placeholder... placeholders) {
        if (platformPlayer instanceof TNLPlayer player) sendMessage(key, player, placeholders);
        else sendMessage(key, placeholders);
    }

    public void sendMessage(@Nonnull Key key, @Nonnull PlatformPlayer platformPlayer, @Nonnull Placeholder... placeholders) {
        sendMessage(ChatPacket.Type.SYSTEM, key, platformPlayer, placeholders);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nonnull Key key, @Nonnull Placeholder... placeholders) {
        sendMessage(key, getPlayer(), placeholders);
    }

    public void sendMessage(@Nonnull Key key, @Nonnull Placeholder... placeholders) {
        sendMessage(ChatPacket.Type.SYSTEM, key, placeholders);
    }

    public void sendMessage(@Nonnull ChatPacket.Type type, @Nullable UUID sender, @Nonnull String message, boolean validate) {
        if (validate) message = Message.format(message, getPlayer());
        ChatPacket chatPacket = ChatPacket.create(message, type);
        if (sender != null) chatPacket.setSender(sender);
        chatPacket.send(getPlayer());
    }

    public void sendMessage(@Nonnull Key key, @Nonnull TNLPlayer player, @Nonnull Placeholder... placeholders) {
        String message;
        if (key instanceof SystemMessageKey systemKey) message = systemKey.message();
        else if (key instanceof MessageKey messageKey) {
            message = Message.get(messageKey, getPlayer().data().getLanguage());
        }
        else message = key.message();
        sendMessage(player, Message.format(message, player, placeholders), false);
    }

    public void chat(@Nonnull String message) {
        chat(new PlayerChatEvent(getPlayer(), message));
    }

    public void chat(@Nonnull PlayerChatEvent event) {
        String message = Color.Minecraft.unColorize(event.getMessage(), '§');
        if (Color.unColorize(message.replace(" ", "")).isEmpty()) return;
        if (Conversation.test(event, event.getPlayer(), event.getMessage())) return;
        if (event.isCommand()) getPlayer().bukkit().performCommand(event.getMessage().substring(1));
        if (!event.call()) return;
        message = Color.Minecraft.unColorize(event.getMessage(), '§');
        String[] strings = message.split(" ");
        if (event.getFormat() == null) event.setFormat(Messages.CHAT_FORMAT.message());
        Placeholder color = new Placeholder("color", event.getPlayer().scoreboardManager().getTeam().getColor().toString());
        for (TNLPlayer all : Listener.getOnlinePlayers()) {
            if (message.toLowerCase().contains(all.getName().toLowerCase())) {
                for (String string : strings) {
                    if (string.equalsIgnoreCase("@" + all.getName())) {
                        String replacement = Message.format(Messages.CHAT_MENTION.message(), new Placeholder("player", all.getName()), color);
                        message = message.replace(string + " ", replacement);
                        message = message.replace(string, replacement);
                        if (string.substring(1).equalsIgnoreCase(event.getPlayer().getName())) continue;
                        ChatMentionEvent mentionEvent = new ChatMentionEvent(event.getPlayer(), all);
                        if (!mentionEvent.call()) continue;
                        all.soundManager().playSound(mentionEvent.getSound(), 1f, 1f);
                    }
                }
            }
        }
        Placeholder coloredMessage = new Placeholder("colored_message", Color.colorize(message, '&'));
        Placeholder text = new Placeholder("message", Color.unColorize(message, '&'));
        for (TNLPlayer all : Listener.getOnlinePlayers()) {
            all.messenger().sendMessage(ChatPacket.Type.CHAT, event.getFormat(), event.getPlayer(), color, text, coloredMessage);
        }
    }

    public void sendPluginMessage(@Nonnull String channel, @Nonnull String... message) {
        if (SpigotConfig.bungee) sendLegacyPluginMessage(channel, message);
        sendModernPluginMessage(channel, message);
    }

    public void sendLegacyPluginMessage(@Nonnull String channel, @Nonnull String... message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            for (String s : message) dataOutputStream.writeUTF(s);
        } catch (IOException e) {
            Logger.error.println(e);
        }
        sendPluginMessage(channel, byteArrayOutputStream.toByteArray());
    }

    public void sendModernPluginMessage(@Nonnull String channel, @Nonnull String... message) {
        sendPluginMessage(channel, StringUtil.toByteArray(message));
    }

    public void sendPluginMessage(@Nonnull String channel, @Nonnull byte[]... bytes) {
        CustomPayloadPacket.create(Messenger.validateChannel(channel), bytes).send(getPlayer());
    }

    public void startConversation(@Nonnull Conversation conversation) {
        this.conversation = conversation;
    }

    public void stopConversation() {
        conversation = null;
    }

    public boolean isInConversation() {
        return conversation != null;
    }

    @Nonnull
    public static String validateChannel(@Nonnull String channel) {
        if (channel.equals("BungeeCord")) return "bungeecord:main";
        else if (channel.equals("bungeecord:main")) return "BungeeCord";
        else if (channel.length() > 64) throw new ChannelNameTooLongException(channel);
        else if (channel.indexOf(':') == -1) {
            throw new IllegalArgumentException("Channel must contain : separator (attempted to use " + channel + ")");
        } else return channel.toLowerCase(Locale.ROOT);
    }
}
