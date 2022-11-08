package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class SetScorePacket extends PacketBuilder {
    private Method method;
    @Nullable
    private String objectiveName;
    private String owner;
    private int score;

    public static SetScorePacket create(Method method, @Nullable String objectiveName, String owner, int score) {
        return Mapping.get().packetManager().outgoing().setScorePacket(method, objectiveName, owner, score);
    }

    public enum Method {
        UPDATE, REMOVE
    }
}
