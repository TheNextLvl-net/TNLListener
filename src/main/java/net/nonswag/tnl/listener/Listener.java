package net.nonswag.tnl.listener;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);
    @Getter
    public static final Listener instance = new Listener();

    @Setter
    @Getter
    private static String serverName = ServerProperties.getInstance().getRoot().getString("server-name", new File("").getAbsoluteFile().getName());
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
            throw new IllegalStateException("Unknown server version: %s".concat(Bukkit.getVersion()));
        }
        if (!net.nonswag.tnl.listener.api.mapper.Loader.load()) {
            throw new MappingError("Found no Mapping for your version: ".concat(getVersion().getRecentVersion()));
        }
        Mapping.get().onLoad();
        Mapping.Info info = Mapping.get().info();
        logger.debug("Using <'{}'> made by <'{}'>", info.name(), String.join(", ", info.authors()));
        if (!info.website().isEmpty()) logger.debug("Website: " + info.website());
        if (!info.description().isEmpty()) logger.debug("Description: " + info.description());
        LogManager.getInstance().initialize();
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
            if (Settings.getConfig().getRoot().has("server-" + server)) {
                String value = Settings.getConfig().getRoot().getString("server-" + server);
                if (value != null && !value.isEmpty()) {
                    if (!value.equalsIgnoreCase("host:port")) {
                        try {
                            Server s = Server.wrap(new ServerInfo(server, new InetSocketAddress(value.split(":")[0], Integer.parseInt(value.split(":")[1]))));
                            logger.debug("Initialized new server <'{}'> ({}:{})", s.getName(), s.getInetSocketAddress().getHostString(), s.getInetSocketAddress().getPort());
                        } catch (Exception e) {
                            logger.error("Failed to load server <'" + server + "'>");
                            logger.error("The ip-address format is 'host:port' (example localhost:25565)");
                            logger.error(e.getMessage());
                        }
                    } else logger.warn("The server <'{}'> is not setup yet", server);
                } else {
                    Settings.getConfig().getRoot().set("server-" + server, "host:port");
                    logger.debug("Found new server <'{}'>", server);
                }
            } else {
                Settings.getConfig().getRoot().set("server-" + server, "host:port");
                logger.debug("Found new server <'{}'>", server);
            }
        }
        if (Settings.DELETE_OLD_LOGS.getValue()) deleteOldLogs();
        Settings.getConfig().save();
        Manager.getInstance().setEnabled(true);
        registerListeners();
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

    private void registerListeners() {
        EventManager manager = Bootstrap.getInstance().getEventManager();
        if (Settings.BETTER_COMMANDS.getValue()) manager.registerListener(new CommandListener());
        if (Settings.BETTER_CHAT.getValue()) manager.registerListener(new ChatListener());
        manager.registerListener(new ConnectionListener());
        manager.registerListener(new InventoryListener());
        manager.registerListener(new InteractListener());
        manager.registerListener(new ServerListener());
        manager.registerListener(new WorldListener());
    }

    @SuppressWarnings("deprecation")
    private void registerPlaceholders() {
        Placeholder.Registry.register(new Placeholder("prefix", Messages.PREFIX.message()));
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

    public static void deleteOldLogs() {
        File file = new File("logs/");
        if (!file.exists() || !file.isDirectory()) return;
        File[] files = file.listFiles((f, n) -> n.endsWith(".log.gz"));
        if (files == null) return;
        for (File all : files) {
            if (!all.delete()) logger.error("Failed to delete file <'" + all.getAbsolutePath() + "§8'>");
        }
    }

    public static void updateTeams() {
        getOnlinePlayers().forEach(all -> all.scoreboardManager().updateTeam());
    }
}
