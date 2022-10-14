package net.nonswag.tnl.listener.api.settings;

import lombok.Getter;
import net.nonswag.core.api.file.formats.PropertyFile;
import net.nonswag.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Settings {

    @Nonnull
    public static final Setting<Boolean> DEBUG = new Setting<>("debug", true);
    @Nonnull
    public static final Setting<Boolean> TIPS = new Setting<>("tips", true);
    @Nonnull
    public static final Setting<Boolean> DELETE_OLD_LOGS = new Setting<>("delete-old-logs", true);
    @Nonnull
    public static final Setting<Boolean> PLUGIN_MANAGER = new Setting<>("enable-plugin-manager", true);
    @Nonnull
    public static final Setting<Boolean> BETTER_COMMANDS = new Setting<>("better-commands", true);
    @Nonnull
    public static final Setting<Boolean> BETTER_CHAT = new Setting<>("better-chat", true);
    @Nonnull
    public static final Setting<Boolean> BETTER_TNT = new Setting<>("better-tnt", true);
    @Nonnull
    public static final Setting<Boolean> BETTER_FALLING_BLOCKS = new Setting<>("better-falling-blocks", true);
    @Nonnull
    public static final Setting<Boolean> PUNISH_SPAMMING = new Setting<>("punish-spamming", true);
    @Nonnull
    public static final Setting<Boolean> AUTO_UPDATER = new Setting<>("auto-updater", true);
    @Nonnull
    public static final Setting<Boolean> TAB_COMPLETER = new Setting<>("tab-completer", true);
    @Nonnull
    public static final Setting<Boolean> FIRST_JOIN_MESSAGE = new Setting<>("first-join-message", true);
    @Nonnull
    public static final Setting<Boolean> JOIN_MESSAGE = new Setting<>("join-message", true);
    @Nonnull
    public static final Setting<Boolean> QUIT_MESSAGE = new Setting<>("quit-message", true);
    @Nonnull
    public static final Setting<Boolean> CUSTOM_ITEM_NAMES = new Setting<>("custom-item-names", true);
    @Nonnull
    public static final Setting<Integer> SERVER_UPDATE_TIMEOUT = new Setting<>("server-update-timeout", 3000);
    @Nonnull
    public static final Setting<Integer> SERVER_UPDATE_TIME = new Setting<>("server-update-time", 5000);
    @Nonnull
    public static final Setting<String> TAB_COMPLETE_BYPASS_PERMISSION = new Setting<>("tab-complete-bypass-permission", "tnl.tab");
    @Nonnull
    public static final Setting<List<String>> SERVERS = new Setting<>("servers", new ArrayList<>());

    @Getter
    @Nonnull
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
                    Integer value = config.getInteger(setting.getKey());
                    ((Setting<Integer>) setting).setValue(value);
                } else {
                    Logger.warn.println("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
                }
            } else if (setting.getValue() instanceof String value) config.setValue(setting.getKey(), value);
            else if (setting.getValue() instanceof Boolean value) config.setValue(setting.getKey(), value);
            else if (setting.getValue() instanceof Byte value) config.setValue(setting.getKey(), value);
            else if (setting.getValue() instanceof Integer value) config.setValue(setting.getKey(), value);
            else if (setting.getValue() instanceof List) {
                List<String> value = (List<String>) setting.getValue();
                config.setValue(setting.getKey(), value);
            } else {
                Logger.error.println("Unset Setting Type <'" + setting.getValue().getClass().getSimpleName() + "'>", new IOException("unset setting type"));
            }
        }
        config.save();
    }
}
