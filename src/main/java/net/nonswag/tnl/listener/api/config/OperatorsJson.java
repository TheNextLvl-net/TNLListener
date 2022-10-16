package net.nonswag.tnl.listener.api.config;

import net.nonswag.core.api.file.formats.JsonFile;

import javax.annotation.Nonnull;

public final class OperatorsJson extends JsonFile {

    @Nonnull
    private static final OperatorsJson instance = new OperatorsJson();

    private OperatorsJson() {
        super("ops.json");
    }

    @Nonnull
    public static OperatorsJson getInstance() {
        return instance;
    }
}
