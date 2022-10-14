package net.nonswag.tnl.listener.api.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
public class GameProfile {

    @Nonnull
    private final UUID uniqueId;
    @Nonnull
    private final String name;
    @Nullable
    private Skin skin;

    public GameProfile(@Nonnull UUID uniqueId, @Nonnull String name, @Nullable Skin skin) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.skin = skin;
    }

    public GameProfile(@Nonnull Player player) {
        this(player.getUniqueId(), player.getName());
    }

    public GameProfile(@Nonnull UUID uniqueId, @Nonnull String name) {
        this(uniqueId, name, null);
    }

    public GameProfile(@Nonnull String name) {
        this(new UUID(new Random().nextLong(), 0), name);
    }

    public GameProfile(@Nonnull String name, @Nonnull Skin skin) {
        this(new UUID(new Random().nextLong(), 0), name, skin);
    }
}
