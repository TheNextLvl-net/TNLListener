package net.nonswag.tnl.listener.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.nonswag.core.api.logger.Color;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.chat.Conversation;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.events.ChatMentionEvent;
import net.nonswag.tnl.listener.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        TNLPlayer player = TNLPlayer.cast(event.getPlayer());
        if (!Settings.LOG_CHAT.getValue()) event.viewers().remove(Bukkit.getConsoleSender());
        if (Conversation.test(event, player, ((TextComponent) event.message()).content())) return;
        event.renderer(ChatRenderer.viewerUnaware((source, displayName, message) -> {
            String content = ((TextComponent) message).content();
            Placeholder color = new Placeholder("color", player.scoreboardManager().getTeam().getColor().toString());
            if (Settings.CHAT_MENTIONS.getValue()) for (Audience audience : event.viewers()) {
                TNLPlayer all = TNLPlayer.cast(audience);
                if (all == null || !content.toLowerCase().contains(all.getName().toLowerCase())) continue;
                for (String string : content.split(" ")) {
                    if (!string.equalsIgnoreCase("@" + all.getName())) continue;
                    String replacement = Message.format(Messages.CHAT_MENTION.message(), all, color);
                    content = content.replace(string, replacement);
                    if (all.equals(player)) continue;
                    ChatMentionEvent mentionEvent = new ChatMentionEvent(player, all);
                    if (!mentionEvent.call()) continue;
                    all.soundManager().playSound(mentionEvent.getSound(), 1f, 1f);
                }
            }
            Placeholder coloredMessage = new Placeholder("colored_message", Color.colorize(content, '&'));
            Placeholder text = new Placeholder("message", Color.unColorize(content, '&'));
            return Component.text(Message.format(Messages.CHAT_FORMAT.message(), player, color, text, coloredMessage));
        }));
    }
}
