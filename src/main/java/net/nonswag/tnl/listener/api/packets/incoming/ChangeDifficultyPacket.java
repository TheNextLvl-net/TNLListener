package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Difficulty;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ChangeDifficultyPacket implements IncomingPacket {

    @Nonnull
    private Difficulty difficulty;

    public ChangeDifficultyPacket(@Nonnull Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
