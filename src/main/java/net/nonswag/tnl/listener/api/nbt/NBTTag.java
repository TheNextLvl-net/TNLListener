package net.nonswag.tnl.listener.api.nbt;

import javax.annotation.Nonnull;

public abstract class NBTTag {

    @Nonnull
    public abstract String get(@Nonnull String key);

    public abstract int getInt(@Nonnull String key);

    public abstract double getDouble(@Nonnull String key);

    public abstract float getFloat(@Nonnull String key);

    public abstract long getLong(@Nonnull String key);

    public abstract short getShort(@Nonnull String key);

    public abstract byte getByte(@Nonnull String key);

    public abstract boolean getBoolean(@Nonnull String key);

    public abstract int[] getIntArray(@Nonnull String key);

    public abstract byte[] getByteArray(@Nonnull String key);

    public abstract void set(@Nonnull String key, @Nonnull String value);

    public abstract void set(@Nonnull String key, int value);

    public abstract void set(@Nonnull String key, double value);

    public abstract void set(@Nonnull String key, float value);

    public abstract void set(@Nonnull String key, long value);

    public abstract void set(@Nonnull String key, short value);

    public abstract void set(@Nonnull String key, byte value);

    public abstract void set(@Nonnull String key, boolean value);

    public abstract void set(@Nonnull String key, int[] value);

    public abstract void set(@Nonnull String key, byte[] value);

    @Nonnull
    public abstract NBTTag append(@Nonnull NBTTag nbt);

    @Nonnull
    public abstract <T> T versioned();

    @Override
    public String toString() {
        return versioned().toString();
    }
}
