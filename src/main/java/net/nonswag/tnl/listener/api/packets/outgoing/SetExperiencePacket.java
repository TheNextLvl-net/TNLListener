package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class SetExperiencePacket extends PacketBuilder {

    private float experienceProgress;
    private int totalExperience;
    private int experienceLevel;

    protected SetExperiencePacket(float experienceProgress, int totalExperience, int experienceLevel) {
        this.experienceProgress = experienceProgress;
        this.totalExperience = totalExperience;
        this.experienceLevel = experienceLevel;
    }

    @Nonnull
    public static SetExperiencePacket create(float experienceProgress, int totalExperience, int experienceLevel) {
        return Mapping.get().packetManager().outgoing().setExperiencePacket(experienceProgress, totalExperience, experienceLevel);
    }
}
