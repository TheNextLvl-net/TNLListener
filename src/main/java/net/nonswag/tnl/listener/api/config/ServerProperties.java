package net.nonswag.tnl.listener.api.config;

import net.nonswag.core.api.file.formats.PropertiesFile;

public final class ServerProperties extends PropertiesFile {

    private static final ServerProperties instance = new ServerProperties();

    private ServerProperties() {
        super("server.properties");
    }

    public static ServerProperties getInstance() {
        return instance;
    }
}
