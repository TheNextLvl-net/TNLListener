package net.nonswag.tnl.holograms.api;

import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.holograms.Holograms;
import net.nonswag.tnl.listener.Listener;

import javax.annotation.Nonnull;

public class UpdateRunnable {

    @Nonnull
    private static final Thread thread = new Thread(() -> {
        try {
            while (Holograms.getInstance().isEnabled() && Listener.getInstance().isEnabled()) {
                Listener.getOnlinePlayers().forEach(all -> all.hologramManager().updateAll());
                Thread.sleep(Holograms.getInstance().getUpdateTime());
            }
        } catch (InterruptedException ignored) {
        } catch (Exception e) {
            Logger.error.println(e);
        }
    }, "hologram-update-task");

    public static void start() {
        thread.start();
    }

    public static void stop() {
        thread.interrupt();
    }
}
