package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LastSeenMessages {
    private byte[] signature;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Update {
        private int offset;
        private BitSet acknowledged;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Packed {
        private List<MessageSignature.Packed> entries;
    }
}
