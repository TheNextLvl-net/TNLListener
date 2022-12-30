package net.nonswag.tnl.listener.utils;

import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.file.formats.MessageFile;
import net.nonswag.core.api.language.Language;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.key.MessageKey;
import net.nonswag.core.api.message.key.SystemMessageKey;

@FieldsAreNonnullByDefault
public final class Messages {

    public static final SystemMessageKey CHAT_FORMAT = new SystemMessageKey("chat-format").register();
    public static final SystemMessageKey CHAT_MENTION = new SystemMessageKey("chat-mention").register();

    public static final MessageKey NO_PERMISSION = new MessageKey("no-permission").register();
    public static final MessageKey COMMAND_ERROR = new MessageKey("command-error").register();
    public static final MessageKey TAB_COMPLETE_ERROR = new MessageKey("tab-complete-error").register();
    public static final MessageKey UNKNOWN_COMMAND = new MessageKey("unknown-command").register();
    public static final MessageKey PLAYER_COMMAND = new MessageKey("player-command").register();
    public static final MessageKey CONSOLE_COMMAND = new MessageKey("console-command").register();
    public static final MessageKey CANT_EXECUTE_COMMAND = new MessageKey("cant-execute-command").register();
    public static final MessageKey INVALID_COMMAND_USAGE = new MessageKey("invalid-command-usage").register();
    public static final MessageKey FIRST_JOIN_MESSAGE = new MessageKey("first-join-message").register();
    public static final MessageKey JOIN_MESSAGE = new MessageKey("join-message").register();
    public static final MessageKey QUIT_MESSAGE = new MessageKey("quit-message").register();
    public static final MessageKey PLAYER_NOT_ONLINE = new MessageKey("player-not-online").register();
    public static final MessageKey UNKNOWN_PLAYER = new MessageKey("unknown-player").register();
    public static final MessageKey CONNECTING_TO_SERVER = new MessageKey("connecting-to-server").register();
    public static final MessageKey UNKNOWN_SERVER = new MessageKey("unknown-server").register();
    public static final MessageKey FAILED_TO_CONNECT_TO_SERVER = new MessageKey("failed-to-connect-to-server").register();
    public static final MessageKey SERVER_IS_OFFLINE = new MessageKey("server-is-offline").register();

    private Messages() {
    }

    public static void loadAll() {
        loadRoot();
        loadEnglish();
        loadGerman();
    }

    private static void loadRoot() {
        Message.ROOT.setDefault(CHAT_FORMAT, "§8[%color%%world%§8] §f%display_name% §8» %color%%message%");
        Message.ROOT.setDefault(CHAT_MENTION, "§8(§3%player%§8)%color%");
        Message.ROOT.save();
    }

    private static void loadEnglish() {
        MessageFile english = MessageFile.getOrCreate(Language.AMERICAN_ENGLISH);
        english.setDefault(NO_PERMISSION, "%prefix%§c You have no rights §8(§4%permission%§8)");
        english.setDefault(COMMAND_ERROR, "%prefix%§c Failed to execute command §8(§4%command%§8)");
        english.setDefault(TAB_COMPLETE_ERROR, "%prefix%§c Failed to create suggestions §8(§4%command%§8)");
        english.setDefault(UNKNOWN_COMMAND, "%prefix%§c The command §8(§4%command%§8)§c doesn't exist");
        english.setDefault(PLAYER_COMMAND, "%prefix%§c This is a player command");
        english.setDefault(CONSOLE_COMMAND, "%prefix%§c This is a console command");
        english.setDefault(CANT_EXECUTE_COMMAND, "%prefix%§c You can't execute this command");
        english.setDefault(INVALID_COMMAND_USAGE, "%prefix%§c You are using the command wrong");
        english.setDefault(FIRST_JOIN_MESSAGE, "%prefix%§6 %player%§a joined the game §8(§7the first time§8)");
        english.setDefault(JOIN_MESSAGE, "%prefix%§6 %player%§a joined the game");
        english.setDefault(QUIT_MESSAGE, "%prefix%§4 %player%§c left the game");
        english.setDefault(PLAYER_NOT_ONLINE, "%prefix%§4 %player%§c is not online");
        english.setDefault(UNKNOWN_PLAYER, "%prefix%§4 %player%§c is unknown to us");
        english.setDefault(CONNECTING_TO_SERVER, "%prefix%§a Connecting you to server §6%server%");
        english.setDefault(UNKNOWN_SERVER, "%prefix%§c The server §4%server%§c doesn't exist");
        english.setDefault(FAILED_TO_CONNECT_TO_SERVER, "%prefix%§c Failed to connect you to server §4%server%");
        english.setDefault(SERVER_IS_OFFLINE, "%prefix%§c The server §4%server%§c is offline");
        english.save();
    }

    private static void loadGerman() {
        MessageFile german = MessageFile.getOrCreate(Language.GERMAN);
        german.setDefault(NO_PERMISSION, "%prefix%§c Darauf hast du keine rechte §8(§4%permission%§8)");
        german.setDefault(COMMAND_ERROR, "%prefix%§c Fehler beim ausführen des commands §8(§4%command%§8)");
        german.setDefault(TAB_COMPLETE_ERROR, "%prefix%§c Fehler beim erstellen der vorschläge §8(§4%command%§8)");
        german.setDefault(UNKNOWN_COMMAND, "%prefix%§c Der command §8(§4%command%§8)§c existiert nicht");
        german.setDefault(PLAYER_COMMAND, "%prefix%§c Das ist ein spieler command");
        german.setDefault(CONSOLE_COMMAND, "%prefix%§c Das ist ein konsolen command");
        german.setDefault(CANT_EXECUTE_COMMAND, "%prefix%§c Du kannst diesen command nicht ausführen");
        german.setDefault(INVALID_COMMAND_USAGE, "%prefix%§c Du benutzt den command falsch");
        german.setDefault(FIRST_JOIN_MESSAGE, "%prefix%§6 %player%§a ist dem server beigetreten §8(§7zum ersten mal§8)");
        german.setDefault(JOIN_MESSAGE, "%prefix%§6 %player%§a ist dem server beigetreten");
        german.setDefault(QUIT_MESSAGE, "%prefix%§4 %player%§c hat den server verlassen");
        german.setDefault(PLAYER_NOT_ONLINE, "%prefix%§4 %player%§c ist nicht online");
        german.setDefault(UNKNOWN_PLAYER, "%prefix%§4 %player%§c ist uns nicht bekannt");
        german.setDefault(CONNECTING_TO_SERVER, "%prefix%§a Verbinde dich zum server §6%server%");
        german.setDefault(UNKNOWN_SERVER, "%prefix% §cDer server §4%server%§c existiert nicht");
        german.setDefault(FAILED_TO_CONNECT_TO_SERVER, "%prefix% §cDu konntest nicht zum server §4%server%§c verbunden werden");
        german.setDefault(SERVER_IS_OFFLINE, "%prefix% §cDer server §4%server%§c ist offline");
        german.save();
    }
}
