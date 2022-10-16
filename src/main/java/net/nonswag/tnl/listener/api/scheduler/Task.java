package net.nonswag.tnl.listener.api.scheduler;

public final class Task {

    private Task() {
    }

    public static boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException ignored) {
            return false;
        }
    }
}
