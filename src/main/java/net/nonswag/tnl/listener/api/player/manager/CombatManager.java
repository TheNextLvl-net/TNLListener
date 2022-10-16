package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;

public abstract class CombatManager extends Manager {

    public abstract void exitCombat();

    public abstract void enterCombat();

    public abstract void setKiller(@Nullable TNLPlayer player);

    @Nullable
    public TNLPlayer getKiller() {
        return TNLPlayer.nullable(getPlayer().bukkit().getKiller());
    }

    public abstract void setLastDamager(@Nullable LivingEntity damager);

    @Nullable
    public abstract LivingEntity getLastDamager();
}
