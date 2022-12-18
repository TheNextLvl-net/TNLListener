package net.nonswag.tnl.listener.utils;

import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.MessageKey;
import net.nonswag.core.api.message.key.SystemMessageKey;

import javax.annotation.Nonnull;

public final class Messages {

    @Nonnull
    public static final SystemMessageKey CHAT_FORMAT = new SystemMessageKey("chat-format").register();
    @Nonnull
    public static final SystemMessageKey CHAT_MENTION = new SystemMessageKey("chat-mention").register();

    @Nonnull
    public static final MessageKey NO_PERMISSION = new MessageKey("no-permission").register();
    @Nonnull
    public static final MessageKey COMMAND_ERROR = new MessageKey("command-error").register();
    @Nonnull
    public static final MessageKey TAB_COMPLETE_ERROR = new MessageKey("tab-complete-error").register();
    @Nonnull
    public static final MessageKey UNKNOWN_COMMAND = new MessageKey("unknown-command").register();
    @Nonnull
    public static final MessageKey PLAYER_COMMAND = new MessageKey("player-command").register();
    @Nonnull
    public static final MessageKey CONSOLE_COMMAND = new MessageKey("console-command").register();
    @Nonnull
    public static final MessageKey CANT_EXECUTE_COMMAND = new MessageKey("cant-execute-command").register();
    @Nonnull
    public static final MessageKey INVALID_COMMAND_USAGE = new MessageKey("invalid-command-usage").register();
    @Nonnull
    public static final MessageKey FIRST_JOIN_MESSAGE = new MessageKey("first-join-message").register();
    @Nonnull
    public static final MessageKey JOIN_MESSAGE = new MessageKey("join-message").register();
    @Nonnull
    public static final MessageKey QUIT_MESSAGE = new MessageKey("quit-message").register();
    @Nonnull
    public static final MessageKey PLAYER_NOT_ONLINE = new MessageKey("player-not-online").register();
    @Nonnull
    public static final MessageKey UNKNOWN_PLAYER = new MessageKey("unknown-player").register();
    @Nonnull
    public static final MessageKey CONNECTING_TO_SERVER = new MessageKey("connecting-to-server").register();
    @Nonnull
    public static final MessageKey UNKNOWN_SERVER = new MessageKey("unknown-server").register();
    @Nonnull
    public static final MessageKey FAILED_TO_CONNECT_TO_SERVER = new MessageKey("failed-to-connect-to-server").register();
    @Nonnull
    public static final MessageKey SERVER_IS_OFFLINE = new MessageKey("server-is-offline").register();

    private Messages() {
    }

    public static void loadAll() {
        loadRoot();
        loadEnglish();
        loadGerman();
    }

    private static void loadRoot() {
        Message.getRoot().setDefault(CHAT_FORMAT, "§8[%color%%world%§8] §f%display_name% §8» %color%%message%");
        Message.getRoot().setDefault(CHAT_MENTION, "§8(§3%player%§8)%color%");
        Message.getRoot().save();
    }

    private static void loadEnglish() {
        Message.getEnglish().setDefault(NO_PERMISSION, "%prefix%§c You have no rights §8(§4%permission%§8)");
        Message.getEnglish().setDefault(COMMAND_ERROR, "%prefix%§c Failed to execute command §8(§4%command%§8)");
        Message.getEnglish().setDefault(TAB_COMPLETE_ERROR, "%prefix%§c Failed to create suggestions §8(§4%command%§8)");
        Message.getEnglish().setDefault(UNKNOWN_COMMAND, "%prefix%§c The command §8(§4%command%§8)§c doesn't exist");
        Message.getEnglish().setDefault(PLAYER_COMMAND, "%prefix%§c This is a player command");
        Message.getEnglish().setDefault(CONSOLE_COMMAND, "%prefix%§c This is a console command");
        Message.getEnglish().setDefault(CANT_EXECUTE_COMMAND, "%prefix%§c You can't execute this command");
        Message.getEnglish().setDefault(INVALID_COMMAND_USAGE, "%prefix%§c You are using the command wrong");
        Message.getEnglish().setDefault(FIRST_JOIN_MESSAGE, "%prefix%§6 %player%§a joined the game §8(§7the first time§8)");
        Message.getEnglish().setDefault(JOIN_MESSAGE, "%prefix%§6 %player%§a joined the game");
        Message.getEnglish().setDefault(QUIT_MESSAGE, "%prefix%§4 %player%§c left the game");
        Message.getEnglish().setDefault(PLAYER_NOT_ONLINE, "%prefix%§4 %player%§c is not online");
        Message.getEnglish().setDefault(UNKNOWN_PLAYER, "%prefix%§4 %player%§c is unknown to us");
        Message.getEnglish().setDefault(CONNECTING_TO_SERVER, "%prefix%§a Connecting you to server §6%server%");
        Message.getEnglish().setDefault(UNKNOWN_SERVER, "%prefix%§c The server §4%server%§c doesn't exist");
        Message.getEnglish().setDefault(FAILED_TO_CONNECT_TO_SERVER, "%prefix%§c Failed to connect you to server §4%server%");
        Message.getEnglish().setDefault(SERVER_IS_OFFLINE, "%prefix%§c The server §4%server%§c is offline");
        Message.getEnglish().save();
    }

    private static void loadGerman() {
        Message.getGerman().setDefault(NO_PERMISSION, "%prefix%§c Darauf hast du keine rechte §8(§4%permission%§8)");
        Message.getGerman().setDefault(COMMAND_ERROR, "%prefix%§c Fehler beim ausführen des commands §8(§4%command%§8)");
        Message.getGerman().setDefault(TAB_COMPLETE_ERROR, "%prefix%§c Fehler beim erstellen der vorschläge §8(§4%command%§8)");
        Message.getGerman().setDefault(UNKNOWN_COMMAND, "%prefix%§c Der command §8(§4%command%§8)§c existiert nicht");
        Message.getGerman().setDefault(PLAYER_COMMAND, "%prefix%§c Das ist ein spieler command");
        Message.getGerman().setDefault(CONSOLE_COMMAND, "%prefix%§c Das ist ein konsolen command");
        Message.getGerman().setDefault(CANT_EXECUTE_COMMAND, "%prefix%§c Du kannst diesen command nicht ausführen");
        Message.getGerman().setDefault(INVALID_COMMAND_USAGE, "%prefix%§c Du benutzt den command falsch");
        Message.getGerman().setDefault(FIRST_JOIN_MESSAGE, "%prefix%§6 %player%§a ist dem server beigetreten §8(§7zum ersten mal§8)");
        Message.getGerman().setDefault(JOIN_MESSAGE, "%prefix%§6 %player%§a ist dem server beigetreten");
        Message.getGerman().setDefault(QUIT_MESSAGE, "%prefix%§4 %player%§c hat den server verlassen");
        Message.getGerman().setDefault(PLAYER_NOT_ONLINE, "%prefix%§4 %player%§c ist nicht online");
        Message.getGerman().setDefault(UNKNOWN_PLAYER, "%prefix%§4 %player%§c ist uns nicht bekannt");
        Message.getGerman().setDefault(CONNECTING_TO_SERVER, "%prefix%§a Verbinde dich zum server §6%server%");
        Message.getGerman().setDefault(UNKNOWN_SERVER, "%prefix% §cDer server §4%server%§c existiert nicht");
        Message.getGerman().setDefault(FAILED_TO_CONNECT_TO_SERVER, "%prefix% §cDu konntest nicht zum server §4%server%§c verbunden werden");
        Message.getGerman().setDefault(SERVER_IS_OFFLINE, "%prefix% §cDer server §4%server%§c ist offline");
        Message.getGerman().save();
    }
}
