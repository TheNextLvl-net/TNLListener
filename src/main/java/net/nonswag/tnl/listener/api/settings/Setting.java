package net.nonswag.tnl.listener.api.settings;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Setting<S> {

    @Getter
    private static final List<Setting<?>> list = new ArrayList<>();

    private final String key;
    private S value;

    public Setting(String key, S value) {
        this.key = key;
        this.value = value;
        getList().add(this);
    }

    public static boolean exists(String key) {
        for (Setting<?> setting : Setting.getList()) if (setting.getKey().equals(key)) return true;
        return false;
    }
}
