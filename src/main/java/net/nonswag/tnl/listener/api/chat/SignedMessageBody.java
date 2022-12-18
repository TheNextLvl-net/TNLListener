package net.nonswag.tnl.listener.api.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class SignedMessageBody {
    private String content;
    private Instant timeStamp;
    private long salt;
    private LastSeenMessages.Packed lastSeen;
}
