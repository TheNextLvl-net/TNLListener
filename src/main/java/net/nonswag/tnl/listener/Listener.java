package net.nonswag.tnl.listener;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.core.api.message.key.Key;
import net.nonswag.core.api.sql.SQL;
import net.nonswag.tnl.listener.api.command.CommandManager;
import net.nonswag.tnl.listener.api.config.ServerProperties;
import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.logger.LogManager;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginUpdate;
import net.nonswag.tnl.listener.api.server.Server;
import net.nonswag.tnl.listener.api.server.ServerInfo;
import net.nonswag.tnl.listener.api.server.UpdateRunnable;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.api.version.Version;
import net.nonswag.tnl.listener.handlers.GlobalPacketHandler;
import net.nonswag.tnl.listener.listeners.*;
import net.nonswag.tnl.listener.utils.Messages;
import net.nonswag.tnl.manager.Manager;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class Listener extends PluginBuilder {

    @Getter
    public static final Listener instance = new Listener();

    @Setter
    private static String serverName = ServerProperties.getInstance().getString("server-name", "");
    @Getter
    private static Version version = Version.UNKNOWN;
    @Getter
    @Setter
    @Nullable
    private static Team.OptionStatus collision = null;
    @Getter
    @Setter
    @Nullable
    private static Team.OptionStatus nametagVisibility = null;

    static void initialize() {
        for (Version version : Version.values()) {
            if (version.equals(Version.UNKNOWN)) continue;
            for (String v : version.getVersions()) {
                if (!Bukkit.getVersion().toLowerCase().contains(v)) continue;
                Listener.version = version;
                break;
            }
        }
        if (version.equals(Version.UNKNOWN)) {
            Logger.error.printf("Your server version (%s) is unknown", Bukkit.getVersion()).println();
            Bukkit.getPluginManager().disablePlugin(Bootstrap.getInstance());
            return;
        }
        if (!net.nonswag.tnl.listener.api.mapper.Loader.load()) {
            throw new MappingError("Found no Mapping for your version (" + getVersion() + ")");
        }
        Mapping.get().onLoad();
        Mapping.Info info = Mapping.get().info();
        Logger.debug.printf("Using <'%s'> made by <'%s'>", info.name(), String.join(", ", info.authors())).println();
        if (!info.website().isEmpty()) Logger.debug.println("Website: " + info.website());
        if (!info.description().isEmpty()) Logger.debug.println("Description: " + info.description());
        LogManager.getInstance().initialize();
        Messages.loadAll();
    }

    private Listener() {
        super(Listener.class, Bootstrap.getInstance());
    }

    @Override
    public void load() {
        Mapping.get().setEnabled(true);
        registerPlaceholders();
    }

    @Override
    public void enable() {
        for (String server : Settings.SERVERS.getValue()) {
            if (Settings.getConfig().has("server-" + server)) {
                String value = Settings.getConfig().get("server-" + server);
                if (value != null && !value.isEmpty()) {
                    if (!value.equalsIgnoreCase("host:port")) {
                        try {
                            Server s = Server.wrap(new ServerInfo(server, new InetSocketAddress(value.split(":")[0], Integer.parseInt(value.split(":")[1]))));
                            Logger.debug.printf("Initialized new server <'%s'> (%s:%s)", s.getName(), s.getInetSocketAddress().getHostString(), s.getInetSocketAddress().getPort()).println();
                        } catch (Exception e) {
                            Logger.error.println("Failed to load server <'" + server + "'>", "The ip-address format is 'host:port' (example localhost:25565)", e.getMessage());
                        }
                    } else Logger.warn.printf("The server <'%s'> is not setup yet", server).println();
                } else {
                    Settings.getConfig().set("server-" + server, "host:port");
                    Logger.debug.printf("Found new server <'%s'>", server).println();
                }
            } else {
                Settings.getConfig().set("server-" + server, "host:port");
                Logger.debug.printf("Found new server <'%s'>", server).println();
            }
        }
        if (Settings.DELETE_OLD_LOGS.getValue()) deleteOldLogs();
        Settings.getConfig().save();
        Bootstrap.getInstance().async(() -> {
            if (Settings.AUTO_UPDATER.getValue()) {
                PluginUpdate updater = Bootstrap.getInstance().getUpdater();
                if (updater != null) updater.downloadUpdate();
            }
        });
        if (Settings.PLUGIN_MANAGER.getValue()) Manager.getInstance().setEnabled(true);
        EventManager manager = Bootstrap.getInstance().getEventManager();
        if (Settings.BETTER_COMMANDS.getValue()) manager.registerListener(new CommandListener());
        if (Settings.BETTER_CHAT.getValue()) manager.registerListener(new ChatListener());
        manager.registerListener(new ConnectionListener());
        manager.registerListener(new InventoryListener());
        manager.registerListener(new InteractListener());
        manager.registerListener(new ServerListener());
        manager.registerListener(new WorldListener());
        GlobalPacketHandler.init();
        getOnlinePlayers(true).forEach(all -> all.pipeline().inject());
        updateTeams();
        UpdateRunnable.start();
    }

    @Override
    public void disable() {
        UpdateRunnable.stop();
        SQL.disconnect();
        getOnlinePlayers().forEach(all -> {
            all.interfaceManager().closeSignMenu();
            all.interfaceManager().closeGUI();
            all.messenger().stopConversation();
            all.npcFactory().deSpawnAll();
            all.pipeline().uninject();
        });
        Mapping.get().setEnabled(false);
    }

    @Override
    public void startupFinished() {
        if (Settings.BETTER_COMMANDS.getValue()) CommandManager.flushUnregistration();
    }

    @SuppressWarnings("deprecation")
    private static void registerPlaceholders() {
        Placeholder.Registry.register(new Placeholder("server", Listener::getServerName));
        Placeholder.Registry.register(new Placeholder("online", () -> Bukkit.getOnlinePlayers().size()));
        Placeholder.Registry.register(new Placeholder("offline", () -> Bukkit.getOfflinePlayers().length));
        Placeholder.Registry.register(new Placeholder("max_online", Bukkit::getMaxPlayers));
        Placeholder.Registry.register(new Placeholder("display_name", player -> ((TNLPlayer) player).bukkit().getDisplayName()));
        Placeholder.Registry.register(new Placeholder("language", player -> ((TNLPlayer) player).data().getLanguage().name()));
        Placeholder.Registry.register(new Placeholder("world", player -> ((TNLPlayer) player).worldManager().getWorld().getName()));
        Placeholder.Registry.register(new Placeholder("health", player -> ((TNLPlayer) player).bukkit().getHealth()));
        Placeholder.Registry.register(new Placeholder("max_health", player -> ((TNLPlayer) player).attributeManager().getMaxHealth()));
        Placeholder.Registry.register(new Placeholder("food_level", player -> ((TNLPlayer) player).bukkit().getFoodLevel()));
        Placeholder.Registry.register(new Placeholder("version", player -> ((TNLPlayer) player).getVersion().getRecentVersion()));
        Placeholder.Registry.register(new Placeholder("gamemode", player -> ((TNLPlayer) player).getGamemode().getName()));
    }

    public static List<TNLPlayer> getOnlinePlayers() {
        return getOnlinePlayers(false);
    }

    private static List<TNLPlayer> getOnlinePlayers(boolean inject) {
        List<TNLPlayer> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(all -> players.add(TNLPlayer.cast(all, inject)));
        return players;
    }

    public static void broadcast(String message) {
        getOnlinePlayers().forEach(all -> all.messenger().sendMessage(message));
    }

    public static void broadcast(Key key, TNLPlayer player, Placeholder... placeholders) {
        getOnlinePlayers().forEach(all -> all.messenger().sendMessage(key, player, placeholders));
    }

    public static void broadcast(Key key, Placeholder... placeholders) {
        getOnlinePlayers().forEach(all -> all.messenger().sendMessage(key, placeholders));
    }

    public static String getServerName() {
        if (serverName.equals("Unknown Server") || serverName.replace(" ", "").isEmpty()) {
            setServerName(new File("").getAbsoluteFile().getName());
        }
        return serverName;
    }

    public static void deleteOldLogs() {
        File file = new File("logs/");
        if (!file.exists() || !file.isDirectory()) return;
        File[] files = file.listFiles((f, n) -> n.endsWith(".log.gz"));
        if (files == null) return;
        for (File all : files) {
            if (!all.delete()) Logger.error.println("Failed to delete file <'" + all.getAbsolutePath() + "§8'>");
        }
    }

    public static void updateTeams() {
        getOnlinePlayers().forEach(all -> all.scoreboardManager().updateTeam());
    }
}
