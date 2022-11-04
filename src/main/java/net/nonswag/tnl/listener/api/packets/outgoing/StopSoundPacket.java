package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;

import javax.annotation.ParametersAreNullableByDefault;

@Getter
@Setter
@ParametersAreNullableByDefault
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class StopSoundPacket extends PacketBuilder {

    private NamespacedKey sound;
    private SoundCategory category;

    public static StopSoundPacket create(NamespacedKey sound, SoundCategory category) {
        return Mapping.get().packetManager().outgoing().stopSoundPacket(sound, category);
    }
}
