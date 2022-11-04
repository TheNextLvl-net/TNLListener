package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CommandSuggestionsPacket extends PacketBuilder {

    private int completionId;
    private Suggestions suggestions;

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Suggestions {
        public static final Suggestions EMPTY = new Suggestions(new StringRange(0, 0), new ArrayList<>());

        private StringRange range;
        private List<Suggestion> suggestions;
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Suggestion {
        private StringRange range;
        private String text;
        @Nullable
        private Tooltip tooltip;

        public Suggestion(StringRange range, String text) {
            this(range, text, null);
        }
    }

    public record StringRange(int start, int end) {
    }

    public interface Tooltip {
        String getMessage();
    }

    public static CommandSuggestionsPacket create(int completionId, Suggestions suggestions) {
        return Mapping.get().packetManager().outgoing().commandSuggestionsPacket(completionId, suggestions);
    }
}
