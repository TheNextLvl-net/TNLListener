package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public abstract class TitlePacket extends PacketBuilder {

    @Nonnull
    private Action action;
    @Nullable
    private String text;
    private int timeIn, timeStay, timeOut;

    protected TitlePacket(@Nonnull Action action, @Nullable String text, int timeIn, int timeStay, int timeOut) {
        this.action = action;
        this.text = text;
        this.timeIn = timeIn;
        this.timeStay = timeStay;
        this.timeOut = timeOut;
    }

    @Nonnull
    public static TitlePacket create(@Nonnull Action action, @Nullable String text, int timeIn, int timeStay, int timeOut) {
        return Mapping.get().packetManager().outgoing().titlePacket(action, text, timeIn, timeStay, timeOut);
    }

    @Nonnull
    public static TitlePacket create(@Nonnull Action action, int timeIn, int timeStay, int timeOut) {
        return create(action, null, timeIn, timeStay, timeOut);
    }

    @Nonnull
    public static TitlePacket create(@Nonnull Action action, @Nullable String text) {
        return create(action, text, -1, -1, -1);
    }

    @Nonnull
    public static TitlePacket create(int timeIn, int timeStay, int timeOut) {
        return create(Action.TIMES, timeIn, timeStay, timeOut);
    }

    @Nonnull
    public static TitlePacket create() {
        return create(Action.RESET, null);
    }

    @Getter
    public enum Action {
        TITLE, SUBTITLE, ACTIONBAR, TIMES, CLEAR, RESET
    }
}
