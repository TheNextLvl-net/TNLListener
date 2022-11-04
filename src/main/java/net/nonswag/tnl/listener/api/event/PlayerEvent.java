package net.nonswag.tnl.listener.api.event;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class PlayerEvent extends TNLEvent {
    private final TNLPlayer player;
}
