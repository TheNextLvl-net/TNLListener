package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.mapper.Mapping;

public abstract class TitlePacket extends PacketBuilder {

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class SetTitlesAnimation extends TitlePacket {
        private int timeIn, timeStay, timeOut;

        public static SetTitlesAnimation create(int timeIn, int timeStay, int timeOut) {
            return Mapping.get().packetManager().outgoing().setTitlesAnimation(timeIn, timeStay, timeOut);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class SetTitleText extends TitlePacket {
        private Component text;

        public static SetTitleText create(Component text) {
            return Mapping.get().packetManager().outgoing().setTitleText(text);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class SetSubtitleText extends TitlePacket {
        private Component text;

        public static SetSubtitleText create(Component text) {
            return Mapping.get().packetManager().outgoing().setSubtitleText(text);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static abstract class ClearTitles extends TitlePacket {
        private boolean resetTimes;

        public static ClearTitles create(boolean resetTimes) {
            return Mapping.get().packetManager().outgoing().clearTitles(resetTimes);
        }
    }
}
