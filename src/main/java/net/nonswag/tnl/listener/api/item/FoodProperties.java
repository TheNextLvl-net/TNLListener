package net.nonswag.tnl.listener.api.item;

import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.HashMap;

public record FoodProperties(int nutrition, float saturation, boolean meat, boolean alwaysEdible, boolean fastFood, @Nonnull HashMap<PotionEffect, Float> effects) {
}
