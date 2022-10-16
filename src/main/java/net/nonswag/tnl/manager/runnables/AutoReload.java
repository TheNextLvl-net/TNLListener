package net.nonswag.tnl.manager.runnables;

import com.google.common.io.Files;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.manager.Manager;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;

public class AutoReload {

    @Nullable
    private static Thread currentThread = null;

    protected AutoReload() {
    }

    @Nonnull
    private static Thread getNewThread() {
        return new Thread(() -> {
            HashMap<String, Long> timestamps = new HashMap<>();
            File pluginsFolder = new File("plugins");
            try {
                while (Manager.getInstance().isAutoReload()) {
                    Thread.sleep(3000);
                    File[] files = pluginsFolder.listFiles(file -> Files.getFileExtension(file.getName()).equalsIgnoreCase("jar"));
                    if (files == null) continue;
                    for (File file : files) {
                        Plugin plugin = PluginManager.getPlugin(file.getName().replace(".jar", ""));
                        if (plugin == null) continue;
                        if (timestamps.containsKey(file.getName())) {
                            if (timestamps.get(file.getName()) >= file.lastModified()) continue;
                            Logger.info.println("Detected file update for plugin <'" + plugin.getName() + "'>");
                            long size = file.length();
                            Thread.sleep(1000);
                            for (int i = 0; ; i++) {
                                long length = file.length();
                                if (length == size) {
                                    if (i > 0) Logger.info.println("Finished upload (" + (i + 1) + " attempts)");
                                    break;
                                } else if (i == 0) Logger.info.println("Waiting for upload completion");
                                size = length;
                                Thread.sleep(1000);
                            }
                            Bootstrap.getInstance().sync(() -> {
                                try {
                                    PluginManager.reload(plugin);
                                } catch (Throwable t) {
                                    Logger.error.println(t);
                                }
                            });
                        }
                        timestamps.put(file.getName(), file.lastModified());
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }, "auto-reload");
    }

    public static void start() {
        if (Manager.getInstance().isAutoReload() && currentThread == null) (currentThread = getNewThread()).start();
    }

    public static void shutdown() {
        if (currentThread != null) currentThread.interrupt();
        currentThread = null;
    }
}
