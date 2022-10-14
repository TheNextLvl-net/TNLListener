package net.nonswag.tnl.listener.api.player.manager;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;

import javax.annotation.Nonnull;

public abstract class AttributeManager extends Manager {

    @Nonnull
    public abstract AttributeInstance getAttribute(@Nonnull Attribute attribute);

    public double getMaxHealth() {
        return getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }

    public void setMaxHealth(double value) {
        getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
    }

    public void resetMaxHealth() {
        setMaxHealth(getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
    }

    public double getFollowRange() {
        return getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getValue();
    }

    public void setFollowRange(double value) {
        getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(value);
    }

    public void resetFollowRange() {
        setFollowRange(getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getDefaultValue());
    }

    public double getKnockbackResistance() {
        return getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue();
    }

    public void setKnockbackResistance(double value) {
        getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(value);
    }

    public void resetKnockbackResistance() {
        setKnockbackResistance(getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getDefaultValue());
    }

    public double getMovementSpeed() {
        return getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
    }

    public void setMovementSpeed(double value) {
        getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(value);
    }

    public void resetMovementSpeed() {
        setMovementSpeed(getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getDefaultValue());
    }

    public double getFlyingSpeed() {
        return getAttribute(Attribute.GENERIC_FLYING_SPEED).getValue();
    }

    public void setFlyingSpeed(double value) {
        getAttribute(Attribute.GENERIC_FLYING_SPEED).setBaseValue(value);
    }

    public void resetFlyingSpeed() {
        setFlyingSpeed(getAttribute(Attribute.GENERIC_FLYING_SPEED).getDefaultValue());
    }

    public double getAttackDamage() {
        return getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
    }

    public void setAttackDamage(double value) {
        getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(value);
    }

    public void resetAttackDamage() {
        setAttackDamage(getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getDefaultValue());
    }

    public double getAttackKnockback() {
        return getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getValue();
    }

    public void setAttackKnockback(double value) {
        getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).setBaseValue(value);
    }

    public void resetAttackKnockback() {
        setAttackKnockback(getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK).getDefaultValue());
    }

    public double getAttackSpeed() {
        return getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
    }

    public void setAttackSpeed(double value) {
        getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(value);
    }

    public void resetAttackSpeed() {
        setAttackSpeed(getAttribute(Attribute.GENERIC_ATTACK_SPEED).getDefaultValue());
    }

    public double getArmor() {
        return getAttribute(Attribute.GENERIC_ARMOR).getValue();
    }

    public void setArmor(double value) {
        getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(value);
    }

    public void resetArmor() {
        setArmor(getAttribute(Attribute.GENERIC_ARMOR).getDefaultValue());
    }

    public double getArmorToughness() {
        return getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getValue();
    }

    public void setArmorToughness(double value) {
        getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(value);
    }

    public void resetArmorToughness() {
        setArmorToughness(getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).getDefaultValue());
    }

    public double getLuck() {
        return getAttribute(Attribute.GENERIC_LUCK).getValue();
    }

    public void setLuck(double value) {
        getAttribute(Attribute.GENERIC_LUCK).setBaseValue(value);
    }

    public void resetLuck() {
        setLuck(getAttribute(Attribute.GENERIC_LUCK).getDefaultValue());
    }

    public double getHorseJumpStrength() {
        return getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue();
    }

    public void setHorseJumpStrength(double value) {
        getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(value);
    }

    public void resetHorseJumpStrength() {
        setHorseJumpStrength(getAttribute(Attribute.HORSE_JUMP_STRENGTH).getDefaultValue());
    }

    public double getZombieSpawnReinforcements() {
        return getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).getValue();
    }

    public void setZombieSpawnReinforcements(double value) {
        getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(value);
    }

    public void resetZombieSpawnReinforcements() {
        setZombieSpawnReinforcements(getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).getDefaultValue());
    }
}
