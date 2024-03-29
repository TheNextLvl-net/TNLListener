package net.nonswag.tnl.listener.api.settings;

import lombok.Getter;
import net.nonswag.core.api.file.formats.PropertiesFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Settings {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class);
    public static final Setting<Boolean> DEBUG = new Setting<>("debug", true);
    public static final Setting<Boolean> DELETE_OLD_LOGS = new Setting<>("delete-old-logs", true);
    public static final Setting<Boolean> PLUGIN_MANAGEMENT = new Setting<>("enable-plugin-management", false);
    public static final Setting<Boolean> BETTER_COMMANDS = new Setting<>("better-commands", true);
    public static final Setting<Boolean> BETTER_CHAT = new Setting<>("better-chat", true);
    public static final Setting<Boolean> LOG_CHAT = new Setting<>("log-chat", false);
    public static final Setting<Boolean> CHAT_MENTIONS = new Setting<>("chat-mentions", true);
    public static final Setting<Boolean> BETTER_TNT = new Setting<>("better-tnt", true);
    public static final Setting<Boolean> BETTER_FALLING_BLOCKS = new Setting<>("better-falling-blocks", true);
    public static final Setting<Boolean> PUNISH_SPAMMING = new Setting<>("punish-spamming", true);
    public static final Setting<Boolean> TAB_COMPLETER = new Setting<>("tab-completer", true);
    public static final Setting<Boolean> FIRST_JOIN_MESSAGE = new Setting<>("first-join-message", true);
    public static final Setting<Boolean> JOIN_MESSAGE = new Setting<>("join-message", true);
    public static final Setting<Boolean> QUIT_MESSAGE = new Setting<>("quit-message", true);
    public static final Setting<Integer> SERVER_UPDATE_TIMEOUT = new Setting<>("server-update-timeout", 3000);
    public static final Setting<Integer> SERVER_UPDATE_TIME = new Setting<>("server-update-time", 5000);
    public static final Setting<String> TAB_COMPLETE_BYPASS_PERMISSION = new Setting<>("tab-complete-bypass-permission", "tnl.tab");
    public static final Setting<List<String>> SERVERS = new Setting<>("servers", new ArrayList<>());

    @Getter
    private static final PropertiesFile config = new PropertiesFile("plugins/Listener", "settings.properties");

    static {
        for (Setting<?> setting : Setting.getList()) {
            if (config.getRoot().has(setting.getKey())) {
                if (setting.getValue() instanceof String) {
                    String value = config.getRoot().getString(setting.getKey());
                    if (value != null) ((Setting<String>) setting).setValue(value);
                } else if (setting.getValue() instanceof Boolean) {
                    Boolean value = config.getRoot().getBoolean(setting.getKey());
                    ((Setting<Boolean>) setting).setValue(value);
                } else if (setting.getValue() instanceof Byte) {
                    Byte value = config.getRoot().getByte(setting.getKey());
                    ((Setting<Byte>) setting).setValue(value);
                } else if (setting.getValue() instanceof List) {
                    List<String> value = config.getRoot().getStringList(setting.getKey());
                    ((Setting<List<String>>) setting).setValue(value);
                } else if (setting.getValue() instanceof Integer) {
                    int value = config.getRoot().getInt(setting.getKey());
                    ((Setting<Integer>) setting).setValue(value);
                } else {
                    logger.warn("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
                }
            } else if (setting.getValue() instanceof String value) config.getRoot().set(setting.getKey(), value);
            else if (setting.getValue() instanceof Boolean value) config.getRoot().set(setting.getKey(), value);
            else if (setting.getValue() instanceof Byte value) config.getRoot().set(setting.getKey(), value);
            else if (setting.getValue() instanceof Integer value) config.getRoot().set(setting.getKey(), value);
            else if (setting.getValue() instanceof List) {
                List<String> value = (List<String>) setting.getValue();
                config.getRoot().set(setting.getKey(), value);
            } else {
                logger.error("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
            }
        }
        config.save();
    }
}
