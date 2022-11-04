package net.nonswag.tnl.listener.api.server;

import net.nonswag.tnl.listener.api.settings.Settings;

public class UpdateRunnable {

    private static final Thread thread;

    static {
        thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(Settings.SERVER_UPDATE_TIME.getValue());
                    for (Server server : Server.servers()) server.update();
                }
            } catch (InterruptedException ignored) {
            }
        }, "ServerRunnable");
    }

    public static void start() {
        if (!Server.servers().isEmpty() && !isRunning()) thread.start();
    }

    public static void stop() {
        if (isRunning()) thread.interrupt();
    }

    public static boolean isRunning() {
        return thread.isAlive() && !thread.isInterrupted();
    }
}
