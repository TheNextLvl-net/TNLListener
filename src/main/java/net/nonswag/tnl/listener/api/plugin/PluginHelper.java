package net.nonswag.tnl.listener.api.plugin;

import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PluginHelper {

    protected PluginHelper() {
    }

    public SimpleCommandMap getCommandMap() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getServer(), "commandMap"));
    }

    public List<Plugin> getPlugins() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "plugins"));
    }

    public Map<String, Plugin> getLookupNames() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "lookupNames"));
    }

    public Map<String, Permission> getPermissions() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "permissions"));
    }

    public Map<Boolean, Set<Permission>> getDefaultPerms() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "defaultPerms"));
    }

    public Map<String, Map<Permissible, Boolean>> getPermSubs() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "permSubs"));
    }

    public Map<Boolean, Map<Permissible, Boolean>> getDefSubs() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "defSubs"));
    }

    public Map<String, Command> getCommands() {
        return Objects.requireNonNull(Reflection.Field.get(getCommandMap(), SimpleCommandMap.class, "knownCommands"));
    }

    @Nullable
    public Command getCommand(String command) {
        return getCommands().get(command);
    }

    public void setCommand(String name, Command command) {
        getCommands().put(name, command);
    }

    public static PluginHelper getInstance() {
        return Mapping.get().pluginHelper();
    }
}
