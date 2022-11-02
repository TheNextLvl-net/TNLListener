package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class CommandSuggestionsPacket extends PacketBuilder {

    @Nonnull
    private Suggestions suggestions;
    private int completionId;

    protected CommandSuggestionsPacket(int completionId, @Nonnull Suggestions suggestions) {
        this.completionId = completionId;
        this.suggestions = suggestions;
    }

    @Getter
    @Setter
    public static class Suggestions {
        @Nonnull
        public static final Suggestions EMPTY = new Suggestions(new StringRange(0, 0), new ArrayList<>());

        @Nonnull
        private StringRange range;
        @Nonnull
        private List<Suggestion> suggestions;

        public Suggestions(@Nonnull StringRange range, @Nonnull List<Suggestion> suggestions) {
            this.range = range;
            this.suggestions = suggestions;
        }
    }

    @Getter
    @Setter
    public static class Suggestion {
        @Nonnull
        private StringRange range;
        @Nonnull
        private String text;
        @Nullable
        private Tooltip tooltip;

        public Suggestion(@Nonnull StringRange range, @Nonnull String text) {
            this(range, text, null);
        }

        public Suggestion(@Nonnull StringRange range, @Nonnull String text, @Nullable Tooltip tooltip) {
            this.range = range;
            this.text = text;
            this.tooltip = tooltip;
        }
    }

    public record StringRange(int start, int end) {
    }

    public interface Tooltip {
        @Nonnull
        String getMessage();
    }

    @Nonnull
    public static CommandSuggestionsPacket create(int completionId, @Nonnull Suggestions suggestions) {
        return Mapping.get().packetManager().outgoing().commandSuggestionsPacket(completionId, suggestions);
    }
}
