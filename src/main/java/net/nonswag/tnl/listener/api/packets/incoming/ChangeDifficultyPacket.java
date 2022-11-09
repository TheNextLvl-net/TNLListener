package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.Difficulty;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ChangeDifficultyPacket extends PacketBuilder {
    private Difficulty difficulty;

    public static ChangeDifficultyPacket create(Difficulty difficulty) {
        return Mapping.get().packetManager().incoming().changeDifficultyPacket(difficulty);
    }
}
