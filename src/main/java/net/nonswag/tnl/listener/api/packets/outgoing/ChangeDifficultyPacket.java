package net.nonswag.tnl.listener.api.packets.outgoing;

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
    private boolean locked;

    public static ChangeDifficultyPacket create(Difficulty difficulty, boolean locked) {
        return Mapping.get().packetManager().outgoing().changeDifficultyPacket(difficulty, locked);
    }
}
