package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.math.Range;

public abstract class HealthManager extends Manager {

    public void setHealth(double health) {
        getPlayer().bukkit().setHealth(health);
    }

    public double getHealth() {
        return getPlayer().bukkit().getHealth();
    }

    public void setMaxHealth(double maxHealth) {
        getPlayer().attributeManager().setMaxHealth(maxHealth);
    }

    public double getMaxHealth() {
        return getPlayer().attributeManager().getMaxHealth();
    }

    @Range(from = 0, to = 20)
    public int getFoodLevel() {
        return getPlayer().bukkit().getFoodLevel();
    }

    public void setFoodLevel(int value) {
        getPlayer().bukkit().setFoodLevel(value);
    }

    public float getSaturation() {
        return getPlayer().bukkit().getSaturation();
    }

    public void setSaturation(float saturation) {
        getPlayer().bukkit().setSaturation(saturation);
    }

    public float getExhaustion() {
        return getPlayer().bukkit().getExhaustion();
    }

    public void setExhaustion(float exhaustion) {
        getPlayer().bukkit().setExhaustion(exhaustion);
    }
}
