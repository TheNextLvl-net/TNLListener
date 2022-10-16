package net.nonswag.tnl.listener.api.mods.labymod.data;

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

public class Permissions {

    @Nonnull
    private final JsonObject message = new JsonObject();

    @Nonnull
    public Permissions allow(@Nonnull Permission... permissions) {
        for (Permission permission : permissions) getMessage().addProperty(permission.name(), true);
        return this;
    }

    @Nonnull
    public Permissions deny(@Nonnull Permission... permissions) {
        for (Permission permission : permissions) getMessage().addProperty(permission.name(), false);
        return this;
    }

    @Nonnull
    public Permissions unset(@Nonnull Permission... permissions) {
        for (Permission permission : permissions) getMessage().remove(permission.name());
        return this;
    }

    public boolean isSet(@Nonnull Permission permission) {
        return getMessage().has(permission.name());
    }

    public boolean isAllowed(@Nonnull Permission permission) {
        return isSet(permission) && getMessage().get(permission.name()).getAsBoolean();
    }

    @Nonnull
    public JsonObject getMessage() {
        return message;
    }
}
