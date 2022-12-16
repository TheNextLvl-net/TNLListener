package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChatSession {
    private UUID sessionId;
    private PublicKey publicKey;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class PublicKey {
        private Instant expiresAt;
        private java.security.PublicKey key;
        private byte[] signature;
    }
}
