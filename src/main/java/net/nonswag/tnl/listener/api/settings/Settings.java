package net.nonswag.tnl.listener.api.settings;

import lombok.Getter;
import net.nonswag.core.api.file.formats.PropertyFile;
import net.nonswag.core.api.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Settings {
    public static final Setting<Boolean> DEBUG = new Setting<>("debug", true);
    public static final Setting<Boolean> TIPS = new Setting<>("tips", true);
    public static final Setting<Boolean> DELETE_OLD_LOGS = new Setting<>("delete-old-logs", true);
    public static final Setting<Boolean> PLUGIN_MANAGER = new Setting<>("enable-plugin-manager", true);
    public static final Setting<Boolean> BETTER_COMMANDS = new Setting<>("better-commands", true);
    public static final Setting<Boolean> BETTER_CHAT = new Setting<>("better-chat", true);
    public static final Setting<Boolean> LOG_CHAT = new Setting<>("log-chat", false);
    public static final Setting<Boolean> CHAT_MENTIONS = new Setting<>("chat-mentions", true);
    public static final Setting<Boolean> BETTER_TNT = new Setting<>("better-tnt", true);
    public static final Setting<Boolean> BETTER_FALLING_BLOCKS = new Setting<>("better-falling-blocks", true);
    public static final Setting<Boolean> PUNISH_SPAMMING = new Setting<>("punish-spamming", true);
    public static final Setting<Boolean> AUTO_UPDATER = new Setting<>("auto-updater", true);
    public static final Setting<Boolean> TAB_COMPLETER = new Setting<>("tab-completer", true);
    public static final Setting<Boolean> FIRST_JOIN_MESSAGE = new Setting<>("first-join-message", true);
    public static final Setting<Boolean> JOIN_MESSAGE = new Setting<>("join-message", true);
    public static final Setting<Boolean> QUIT_MESSAGE = new Setting<>("quit-message", true);
    public static final Setting<Boolean> CUSTOM_ITEM_NAMES = new Setting<>("custom-item-names", true);
    public static final Setting<Integer> SERVER_UPDATE_TIMEOUT = new Setting<>("server-update-timeout", 3000);
    public static final Setting<Integer> SERVER_UPDATE_TIME = new Setting<>("server-update-time", 5000);
    public static final Setting<String> TAB_COMPLETE_BYPASS_PERMISSION = new Setting<>("tab-complete-bypass-permission", "tnl.tab");
    public static final Setting<List<String>> SERVERS = new Setting<>("servers", new ArrayList<>());

    @Getter
    private static final PropertyFile config = new PropertyFile("plugins/Listener", "settings.properties");

    static {
        for (Setting<?> setting : Setting.getList()) {
            if (config.has(setting.getKey())) {
                if (setting.getValue() instanceof String) {
                    String value = config.get(setting.getKey());
                    if (value != null) ((Setting<String>) setting).setValue(value);
                } else if (setting.getValue() instanceof Boolean) {
                    Boolean value = config.getBoolean(setting.getKey());
                    ((Setting<Boolean>) setting).setValue(value);
                } else if (setting.getValue() instanceof Byte) {
                    Byte value = config.getByte(setting.getKey());
                    ((Setting<Byte>) setting).setValue(value);
                } else if (setting.getValue() instanceof List) {
                    List<String> value = config.getStringList(setting.getKey());
                    ((Setting<List<String>>) setting).setValue(value);
                } else if (setting.getValue() instanceof Integer) {
                    int value = config.getInt(setting.getKey());
                    ((Setting<Integer>) setting).setValue(value);
                } else {
                    Logger.warn.println("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
                }
            } else if (setting.getValue() instanceof String value) config.set(setting.getKey(), value);
            else if (setting.getValue() instanceof Boolean value) config.set(setting.getKey(), value);
            else if (setting.getValue() instanceof Byte value) config.set(setting.getKey(), value);
            else if (setting.getValue() instanceof Integer value) config.set(setting.getKey(), value);
            else if (setting.getValue() instanceof List) {
                List<String> value = (List<String>) setting.getValue();
                config.set(setting.getKey(), value);
            } else {
                Logger.error.println("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
            }
        }
        config.save();
    }
}
