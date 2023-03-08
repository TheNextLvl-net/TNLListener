package net.nonswag.tnl.manager.commands.subcommands;

import com.google.common.io.Files;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginLoad extends SubCommand {

    public PluginLoad() {
        super("load", "tnl.manage");
    }

    @Override
    protected void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 2) throw new InvalidUseException(this);
        File file = new File("plugins/" + args[1]);
        if (file.exists()) {
            if (file.isFile() && Files.getFileExtension(file.getName()).equalsIgnoreCase("jar")) {
                Plugin plugin = PluginManager.getPlugin(args[1].replace(".jar", ""));
                if (plugin == null) {
                    source.sendMessage("%prefix% §aLoading plugin §6" + args[1].replace(".jar", ""));
                    try {
                        plugin = PluginManager.load(file);
                        source.sendMessage("%prefix% §aSuccessfully loaded plugin §6" + plugin.getName());
                    } catch (Throwable t) {
                        source.sendMessage("%prefix% §cFailed to load plugin §4" + file.getAbsolutePath());
                        t.printStackTrace();
                    }
                } else source.sendMessage("%prefix% §cThe plugin §4" + plugin.getName() + "§c is already loaded");
            } else source.sendMessage("%prefix% §cThe file §4" + file.getName() + "§c is not a jar file");
        } else source.sendMessage("%prefix% §cThe file §4" + args[1] + "§c doesn't exist");
    }

    @Override
    public void usage(Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin load §8[§6File§8]");
    }

    @Override
    protected List<String> suggest(Invocation invocation) {
        List<String> suggestions = new ArrayList<>();
        if (invocation.arguments().length > 2) return suggestions;
        File[] plugins = new File("plugins").listFiles();
        if (plugins == null) return suggestions;
        for (File file : plugins) {
            if (!file.getName().toLowerCase().endsWith(".jar")) continue;
            if (PluginManager.getPlugin(file.getName().replace(".jar", "")) != null) continue;
            suggestions.add(file.getName());
        }
        return suggestions;
    }
}
