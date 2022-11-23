package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.event.TNLEvent;

import java.util.List;

@Getter
@Setter
public class CommandsUnregisterEvent extends TNLEvent {
    private List<String> commands;

    public CommandsUnregisterEvent(List<String> commands) {
        this.commands = commands;
    }
}
