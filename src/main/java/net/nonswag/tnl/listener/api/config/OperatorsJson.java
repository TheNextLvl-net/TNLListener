package net.nonswag.tnl.listener.api.config;

import net.nonswag.core.api.file.formats.JsonFile;

public final class OperatorsJson extends JsonFile {

    private static final OperatorsJson instance = new OperatorsJson();

    private OperatorsJson() {
        super("ops.json");
    }

    public static OperatorsJson getInstance() {
        return instance;
    }
}
