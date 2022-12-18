package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class MessageSignature {
    private byte[] bytes;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Packed {
        private int id;
        @Nullable
        private MessageSignature fullSignature;
    }
}
