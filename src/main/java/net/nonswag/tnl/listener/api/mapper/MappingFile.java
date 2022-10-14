package net.nonswag.tnl.listener.api.mapper;

import com.google.gson.JsonObject;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;

import javax.annotation.Nonnull;

public class MappingFile {

    @Nonnull
    private final JsonObject root;

    public MappingFile(@Nonnull String string) {
        this.root = JsonHelper.parse(string).getAsJsonObject();
    }

    @Nonnull
    public String getMain() {
        if (!root.has("main")) throw new MappingError("main not defined");
        return root.get("main").getAsString();
    }
}
