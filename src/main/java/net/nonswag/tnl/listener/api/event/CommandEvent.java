package net.nonswag.tnl.listener.api.event;

import lombok.Getter;
import net.nonswag.core.api.command.CommandSource;

import javax.annotation.Nonnull;
import java.util.Objects;

@Getter
public abstract class CommandEvent extends TNLEvent {

    @Nonnull
    private final CommandSource source;
    @Nonnull
    private final String fullCommand;

    protected CommandEvent(@Nonnull CommandSource source, @Nonnull String fullCommand) {
        this.source = source;
        this.fullCommand = fullCommand;
    }

    @Nonnull
    public String getCommand() {
        return getFullCommand().split(" ")[0];
    }

    @Override
    public String toString() {
        return "CommandEvent{" +
                "source=" + source +
                ", fullCommand='" + fullCommand + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommandEvent that = (CommandEvent) o;
        return source.equals(that.source) && fullCommand.equals(that.fullCommand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), source, fullCommand);
    }
}
