package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.advancement.Toast;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class AdvancementManager extends Manager {
    public abstract void sendToast(Toast toast);
}
