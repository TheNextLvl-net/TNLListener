package net.nonswag.tnl.listener.api.item;

import org.bukkit.potion.PotionEffect;

import java.util.HashMap;

public record FoodProperties(int nutrition, float saturation, boolean meat, boolean alwaysEdible, boolean fastFood, HashMap<PotionEffect, Float> effects) {
}
