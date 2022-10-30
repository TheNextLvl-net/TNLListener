package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.api.packets.outgoing.CooldownPacket;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public abstract class CooldownManager extends Manager {

    public void swingMainHand() {
        getPlayer().bukkit().swingMainHand();
    }

    public void swingOffHand() {
        getPlayer().bukkit().swingOffHand();
    }

    public float getAttackCooldown() {
        return getPlayer().bukkit().getAttackCooldown();
    }

    public abstract void resetAttackCooldown();

    public abstract void setAttackCooldown(float cooldown);

    public void setCooldown(@Nonnull Material material, int ticks, boolean packet) {
        if (packet) CooldownPacket.create(material, ticks).send(getPlayer());
        else getPlayer().bukkit().setCooldown(material, ticks);
    }

    public void setCooldown(@Nonnull Material material, int ticks) {
        setCooldown(material, ticks, false);
    }

    public void resetCooldown(@Nonnull Material material, boolean packet) {
        setCooldown(material, 0, packet);
    }

    public void resetCooldown(@Nonnull Material material) {
        resetCooldown(material, false);
    }

    public int getCooldown(@Nonnull Material material) {
        return getPlayer().bukkit().getCooldown(material);
    }

    public boolean hasCooldown(@Nonnull Material material) {
        return getPlayer().bukkit().hasCooldown(material);
    }

    public void setArrowCooldown(int ticks) {
        getPlayer().bukkit().setArrowCooldown(ticks);
    }

    public void setPortalCooldown(int ticks) {
        getPlayer().bukkit().setPortalCooldown(ticks);
    }

    public int getArrowCooldown() {
        return getPlayer().bukkit().getArrowCooldown();
    }

    public int getPortalCooldown() {
        return getPlayer().bukkit().getPortalCooldown();
    }
}
