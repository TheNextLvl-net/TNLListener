package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.*;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommandSuggestionsPacket extends PacketBuilder {
    private int id;
    private Suggestions suggestions;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Suggestions {
        private StringRange range;
        private List<Suggestion> suggestions;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Suggestion {
        private StringRange range;
        private String text;
        @Nullable
        private Tooltip tooltip;
    }

    public record StringRange(int start, int end) {
    }

    public interface Tooltip {
        String getMessage();
    }

    public static CommandSuggestionsPacket create(int id, Suggestions suggestions) {
        return Mapping.get().packetManager().outgoing().commandSuggestionsPacket(id, suggestions);
    }
}
