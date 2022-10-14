package net.nonswag.tnl.listener.api.config;

import net.nonswag.core.api.file.formats.PropertyFile;

import javax.annotation.Nonnull;

public final class ServerProperties extends PropertyFile {

    @Nonnull
    private static final ServerProperties instance = new ServerProperties();

    private ServerProperties() {
        super("server.properties");
    }

    @Nonnull
    public static ServerProperties getInstance() {
        return instance;
    }
}
