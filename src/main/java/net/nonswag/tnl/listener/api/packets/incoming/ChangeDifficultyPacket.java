package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Difficulty;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ChangeDifficultyPacket extends PacketBuilder {

    @Nonnull
    private Difficulty difficulty;

    protected ChangeDifficultyPacket(@Nonnull Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Nonnull
    public static ChangeDifficultyPacket create(@Nonnull Difficulty difficulty) {
        return Mapping.get().packetManager().incoming().changeDifficultyPacket(difficulty);
    }
}
