package net.nonswag.tnl.listener.api.nbt;

import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class NBTTag {

    public abstract String get(String key);

    public abstract int getInt(String key);

    public abstract double getDouble(String key);

    public abstract float getFloat(String key);

    public abstract long getLong(String key);

    public abstract short getShort(String key);

    public abstract byte getByte(String key);

    public abstract boolean getBoolean(String key);

    public abstract int[] getIntArray(String key);

    public abstract byte[] getByteArray(String key);

    public abstract void set(String key, String value);

    public abstract void set(String key, int value);

    public abstract void set(String key, double value);

    public abstract void set(String key, float value);

    public abstract void set(String key, long value);

    public abstract void set(String key, short value);

    public abstract void set(String key, byte value);

    public abstract void set(String key, boolean value);

    public abstract void set(String key, int[] value);

    public abstract void set(String key, byte[] value);

    public abstract NBTTag append(NBTTag nbt);

    public abstract <T> T versioned();

    @Override
    public String toString() {
        return versioned().toString();
    }
}
