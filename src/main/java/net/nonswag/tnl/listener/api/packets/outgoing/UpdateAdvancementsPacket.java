package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.advancement.Advancement;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class UpdateAdvancementsPacket extends PacketBuilder {
    private boolean reset;
    private HashMap<NamespacedKey, Advancement.Builder> added;
    private List<NamespacedKey> removed;
    private HashMap<NamespacedKey, Advancement.Progress> progress;

    public static UpdateAdvancementsPacket create(boolean reset, HashMap<NamespacedKey, Advancement.Builder> added, List<NamespacedKey> removed, HashMap<NamespacedKey, Advancement.Progress> progress) {
        return Mapping.get().packetManager().outgoing().updateAdvancementsPacket(reset, added, removed, progress);
    }
}
