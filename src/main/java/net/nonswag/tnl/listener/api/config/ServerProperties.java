package net.nonswag.tnl.listener.api.config;

import net.nonswag.core.api.file.formats.PropertyFile;

public final class ServerProperties extends PropertyFile {

    private static final ServerProperties instance = new ServerProperties();

    private ServerProperties() {
        super("server.properties");
    }

    public static ServerProperties getInstance() {
        return instance;
    }
}
