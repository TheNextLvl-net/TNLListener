package net.nonswag.tnl.listener.api.settings;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Setting<S> {

    @Getter
    @Nonnull
    private static final List<Setting<?>> list = new ArrayList<>();

    @Nonnull
    private final String key;
    @Nonnull
    private S value;

    public Setting(@Nonnull String key, @Nonnull S value) {
        this.key = key;
        this.value = value;
        getList().add(this);
    }

    public static boolean exists(@Nonnull String key) {
        for (Setting<?> setting : Setting.getList()) if (setting.getKey().equals(key)) return true;
        return false;
    }
}
