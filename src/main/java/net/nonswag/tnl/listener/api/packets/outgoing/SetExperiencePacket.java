package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetExperiencePacket extends PacketBuilder {
    private float experienceProgress;
    private int totalExperience, experienceLevel;

    public static SetExperiencePacket create(float experienceProgress, int totalExperience, int experienceLevel) {
        return Mapping.get().packetManager().outgoing().setExperiencePacket(experienceProgress, totalExperience, experienceLevel);
    }
}
