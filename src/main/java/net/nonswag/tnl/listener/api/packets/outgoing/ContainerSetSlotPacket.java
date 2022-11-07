package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContainerSetSlotPacket extends PacketBuilder {

    private int containerId, stateId, slot;
    @Nullable
    private ItemStack itemStack;

    public static ContainerSetSlotPacket create(int containerId, int stateId, int slot, @Nullable ItemStack itemStack) {
        return Mapping.get().packetManager().outgoing().containerSetSlotPacket(containerId, stateId, slot, itemStack);
    }

    public static ContainerSetSlotPacket create(int containerId, int slot, @Nullable ItemStack itemStack) {
        return create(containerId, 0, slot, itemStack);
    }
}
