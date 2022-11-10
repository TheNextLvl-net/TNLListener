package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.outgoing.GameEventPacket;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public abstract class EffectManager extends Manager {

    public Collection<PotionEffect> getEffects() {
        return getPlayer().bukkit().getActivePotionEffects();
    }

    public void addEffect(PotionEffect effect) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().addPotionEffect(effect));
    }

    public void addEffects(List<PotionEffect> effects) {
        Bootstrap.getInstance().sync(() -> {
            for (PotionEffect effect : effects) addEffect(effect);
        });
    }

    public void removeEffect(PotionEffectType type) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().removePotionEffect(type));
    }

    public void removeEffects(List<PotionEffect> effects) {
        Bootstrap.getInstance().sync(() -> {
            for (PotionEffect effect : effects) removeEffect(effect.getType());
        });
    }

    public void removeEffects() {
        Bootstrap.getInstance().sync(() -> {
            for (PotionEffect effect : getEffects()) removeEffect(effect.getType());
        });
    }

    @Nullable
    public PotionEffect getEffect(PotionEffectType type) {
        for (PotionEffect effect : getEffects()) if (effect.getType().equals(type)) return effect;
        return null;
    }

    public boolean hasEffect(PotionEffectType type) {
        return getPlayer().bukkit().hasPotionEffect(type);
    }

    public void playEffect(EntityEffect effect) {
        getPlayer().bukkit().playEffect(effect);
    }

    public <T> void playEffect(Location location, Effect effect, @Nullable T data) {
        getPlayer().bukkit().playEffect(location, effect, data);
    }

    public void playMobAppearance() {
        GameEventPacket.create(GameEventPacket.GUARDIAN_ELDER_EFFECT).send(getPlayer());
    }
}
