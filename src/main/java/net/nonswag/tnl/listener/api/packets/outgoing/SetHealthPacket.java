package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetHealthPacket extends PacketBuilder {
    private float health;
    private int food;
    private float saturation;

    public static SetHealthPacket create(float health, int food, float saturation) {
        return Mapping.get().packetManager().outgoing().setHealthPacket(health, food, saturation);
    }
}
