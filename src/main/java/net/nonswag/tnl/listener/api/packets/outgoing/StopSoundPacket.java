package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class StopSoundPacket extends PacketBuilder {

    @Nullable
    private NamespacedKey sound;
    @Nullable
    private SoundCategory category;

    protected StopSoundPacket(@Nullable NamespacedKey sound, @Nullable SoundCategory category) {
        this.sound = sound;
        this.category = category;
    }

    @Nonnull
    public static StopSoundPacket create(@Nullable NamespacedKey sound, @Nullable SoundCategory category) {
        return Mapping.get().packetManager().outgoing().stopSoundPacket(sound, category);
    }
}
