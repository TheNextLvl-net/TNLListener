package net.nonswag.tnl.listener.api.mods.labymod.data;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class Permissions {
    private final JsonObject message = new JsonObject();

    public Permissions allow(Permission... permissions) {
        for (Permission permission : permissions) getMessage().addProperty(permission.name(), true);
        return this;
    }

    public Permissions deny(Permission... permissions) {
        for (Permission permission : permissions) getMessage().addProperty(permission.name(), false);
        return this;
    }

    public Permissions unset(Permission... permissions) {
        for (Permission permission : permissions) getMessage().remove(permission.name());
        return this;
    }

    public boolean isSet(Permission permission) {
        return getMessage().has(permission.name());
    }

    public boolean isAllowed(Permission permission) {
        return isSet(permission) && getMessage().get(permission.name()).getAsBoolean();
    }
}
