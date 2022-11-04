package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.entity.Entity;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetPassengersPacket extends PacketBuilder {
    private int holderId;
    private int[] passengers;

    public static SetPassengersPacket create(int holderId, int[] passengers) {
        return Mapping.get().packetManager().outgoing().setPassengersPacket(holderId, passengers);
    }

    public static SetPassengersPacket create(Entity holder, Entity... passengers) {
        int[] ids = new int[passengers.length];
        for (int i = 0; i < passengers.length; i++) ids[i] = passengers[i].getEntityId();
        return create(holder.getEntityId(), ids);
    }

    public static SetPassengersPacket create(Entity holder) {
        return create(holder, holder.getPassengers().toArray(new Entity[]{}));
    }
}
