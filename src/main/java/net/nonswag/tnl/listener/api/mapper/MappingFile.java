package net.nonswag.tnl.listener.api.mapper;

import com.google.gson.JsonObject;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.tnl.listener.api.mapper.errors.MappingError;

public class MappingFile {

    private final JsonObject root;

    public MappingFile(String string) {
        this.root = JsonHelper.parse(string).getAsJsonObject();
    }

    public String getMain() {
        if (!root.has("main")) throw new MappingError("main not defined");
        return root.get("main").getAsString();
    }
}
