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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PluginHelper {

    protected PluginHelper() {
    }

    @Nonnull
    public SimpleCommandMap getCommandMap() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getServer(), "commandMap"));
    }

    @Nonnull
    public List<Plugin> getPlugins() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "plugins"));
    }

    @Nonnull
    public Map<String, Plugin> getLookupNames() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "lookupNames"));
    }

    @Nonnull
    public Map<String, Permission> getPermissions() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "permissions"));
    }

    @Nonnull
    public Map<Boolean, Set<Permission>> getDefaultPerms() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "defaultPerms"));
    }

    @Nonnull
    public Map<String, Map<Permissible, Boolean>> getPermSubs() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "permSubs"));
    }

    @Nonnull
    public Map<Boolean, Map<Permissible, Boolean>> getDefSubs() {
        return Objects.requireNonNull(Reflection.Field.get(Bukkit.getPluginManager(), SimplePluginManager.class, "defSubs"));
    }

    @Nonnull
    public Map<String, Command> getCommands() {
        return Objects.requireNonNull(Reflection.Field.get(getCommandMap(), SimpleCommandMap.class, "knownCommands"));
    }

    @Nullable
    public Command getCommand(@Nonnull String command) {
        return getCommands().get(command);
    }

    public void setCommand(@Nonnull String name, @Nonnull Command command) {
        getCommands().put(name, command);
    }

    @Nonnull
    public static PluginHelper getInstance() {
        return Mapping.get().pluginHelper();
    }
}
