package net.nonswag.tnl.listener.api.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GameProfile {
    private final UUID uniqueId;
    private final String name;
    @Nullable
    private Skin skin;

    public GameProfile(Player player) {
        this(player.getUniqueId(), player.getName());
    }

    public GameProfile(UUID uniqueId, String name) {
        this(uniqueId, name, null);
    }

    public GameProfile(String name) {
        this(new UUID(new Random().nextLong(), 0), name);
    }

    public GameProfile(String name, Skin skin) {
        this(new UUID(new Random().nextLong(), 0), name, skin);
    }
}
