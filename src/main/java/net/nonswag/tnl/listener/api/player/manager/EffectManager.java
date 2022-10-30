package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.packets.outgoing.GameStateChangePacket;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public abstract class EffectManager extends Manager {

    @Nonnull
    public Collection<PotionEffect> getEffects() {
        return getPlayer().bukkit().getActivePotionEffects();
    }

    public void addEffect(@Nonnull PotionEffect effect) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().addPotionEffect(effect));
    }

    public void addEffects(@Nonnull List<PotionEffect> effects) {
        Bootstrap.getInstance().sync(() -> {
            for (PotionEffect effect : effects) addEffect(effect);
        });
    }

    public void removeEffect(@Nonnull PotionEffectType type) {
        Bootstrap.getInstance().sync(() -> getPlayer().bukkit().removePotionEffect(type));
    }

    public void removeEffects(@Nonnull List<PotionEffect> effects) {
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
    public PotionEffect getEffect(@Nonnull PotionEffectType type) {
        for (PotionEffect effect : getEffects()) if (effect.getType().equals(type)) return effect;
        return null;
    }

    public boolean hasEffect(@Nonnull PotionEffectType type) {
        return getPlayer().bukkit().hasPotionEffect(type);
    }

    public void playEffect(@Nonnull EntityEffect effect) {
        getPlayer().bukkit().playEffect(effect);
    }

    public <T> void playEffect(@Nonnull Location location, @Nonnull Effect effect, @Nullable T data) {
        getPlayer().bukkit().playEffect(location, effect, data);
    }

    public void playMobAppearance() {
        GameStateChangePacket.create(GameStateChangePacket.ELDER_GUARDIAN).send(getPlayer());
    }
}
