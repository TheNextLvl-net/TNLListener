package net.nonswag.tnl.listener.api.command;

import com.google.gson.JsonArray;
import net.nonswag.core.api.file.formats.JsonFile;
import net.nonswag.core.api.object.Getter;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.plugin.PluginHelper;
import net.nonswag.tnl.listener.api.version.Version;
import net.nonswag.tnl.listener.events.CommandsUnregisterEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public record CommandManager(Plugin plugin) {

    private static final JsonFile configuration = new JsonFile("plugins/Listener", "removed-commands.json");
    private static final List<String> FOR_REMOVAL = new ArrayList<>();

    static {
        if (!configuration.getJsonElement().isJsonArray()) {
            JsonArray array = new JsonArray();
            FOR_REMOVAL.addAll(Arrays.asList("ver", "version", "reload", "rl", "spigot", "weather", "time", "paper",
                    "w", "me", "tm", "stop", "team", "restart", "stopsound", "save-on", "teammsg", "tellraw", "tell",
                    "experience", "msg", "clone", "scoreboard", "say", "debug", "spawnpoint", "title", "recipe", "bukkit",
                    "icanhasbukkit", "save-all", "setworldspawn", "mspt", "about", "help", "?", "save-off"));
            for (String[] aliases : Bukkit.getCommandAliases().values()) {
                for (String alias : aliases) {
                    String command = alias.split(" ")[0];
                    if (!FOR_REMOVAL.contains(command)) FOR_REMOVAL.add(command);
                }
            }
            FOR_REMOVAL.forEach(array::add);
            configuration.setJsonElement(array);
            configuration.save();
        } else {
            JsonArray array = configuration.getJsonElement().getAsJsonArray();
            array.forEach(element -> FOR_REMOVAL.add(element.getAsString()));
        }
    }

    public void registerCommand(TNLCommand command) {
        command.setOwner(plugin());
        Reflection.Field.set(command, Command.class, "commandMap", PluginHelper.getInstance().getCommandMap());
        Map<String, Command> commands = PluginHelper.getInstance().getCommands();
        for (String alias : command.getAliases()) commands.put(alias, command);
        commands.put(command.getName(), command);
        command.register(PluginHelper.getInstance().getCommandMap());
    }

    public boolean registerCommand(Version version, Getter<TNLCommand> command) {
        if (!Listener.getVersion().equals(version)) return false;
        registerCommand(command.get());
        return true;
    }

    public void unregisterAllCommands() {
        unregisterAllCommands(plugin());
    }

    public List<TNLCommand> getRegisteredCommands() {
        return getRegisteredCommands(plugin());
    }

    public static List<TNLCommand> getRegisteredCommands(Plugin plugin) {
        List<TNLCommand> commands = new ArrayList<>();
        for (Command command : PluginHelper.getInstance().getCommands().values()) {
            if (command instanceof TNLCommand tnlCommand && !commands.contains(tnlCommand)) {
                if (tnlCommand.getOwner() != null && tnlCommand.getOwner().equals(plugin)) commands.add(tnlCommand);
            }
        }
        return commands;
    }

    public static void unregisterAllCommands(Plugin plugin) {
        List<String> commands = new ArrayList<>();
        for (TNLCommand command : getRegisteredCommands(plugin)) {
            commands.add(command.getName());
            commands.addAll(command.getAliases());
        }
        commands.addAll(plugin.getDescription().getCommands().keySet());
        unregisterCommands(commands);
    }

    public static void unregisterCommands(List<String> commands) {
        if (commands.isEmpty()) return;
        CommandsUnregisterEvent event = new CommandsUnregisterEvent(commands);
        if (!event.call()) return;
        Map<String, Command> map = PluginHelper.getInstance().getCommands();
        for (String command : event.getCommands()) map.remove(command);
        Listener.getOnlinePlayers().forEach(all -> all.pipeline().updateCommands());
    }

    public static void flushUnregistration() {
        unregisterCommands(FOR_REMOVAL);
        FOR_REMOVAL.clear();
    }
}
