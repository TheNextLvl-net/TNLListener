package net.nonswag.tnl.listener.events.mapping;

import net.nonswag.tnl.listener.api.event.TNLEvent;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

public abstract class MappingEvent extends TNLEvent {

    @Nonnull
    public Mapping getMapping() {
        return Mapping.get();
    }
}
