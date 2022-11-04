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
public abstract class TitlePacket extends PacketBuilder {

    private Action action;
    @Nullable
    private String text;
    private int timeIn, timeStay, timeOut;

    public static TitlePacket create(Action action, @Nullable String text, int timeIn, int timeStay, int timeOut) {
        return Mapping.get().packetManager().outgoing().titlePacket(action, text, timeIn, timeStay, timeOut);
    }

    public static TitlePacket create(Action action, int timeIn, int timeStay, int timeOut) {
        return create(action, null, timeIn, timeStay, timeOut);
    }

    public static TitlePacket create(Action action, @Nullable String text) {
        return create(action, text, -1, -1, -1);
    }

    public static TitlePacket create(int timeIn, int timeStay, int timeOut) {
        return create(Action.TIMES, timeIn, timeStay, timeOut);
    }

    public static TitlePacket create() {
        return create(Action.RESET, null);
    }

    public enum Action {
        TITLE, SUBTITLE, TIMES, CLEAR, RESET
    }
}
