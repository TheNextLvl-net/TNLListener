package net.nonswag.tnl.listener.api.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class PacketSerializer {

    public static void writeString(@Nonnull DataOutputStream outputStream, @Nonnull String value) {
        try {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            writeVarInt(outputStream, bytes.length);
            outputStream.write(bytes);
        } catch (IOException ignored) {
        }
    }

    public static void writeVarInt(@Nonnull DataOutputStream outputStream, int value) {
        try {
            while (true) {
                if ((value & 0xFFFFFF80) == 0) {
                    outputStream.writeByte(value);
                    return;
                }
                outputStream.writeByte(value & 0x7F | 0x80);
                value >>>= 7;
            }
        } catch (IOException ignored) {
        }
    }

    public static int readVarInt(@Nonnull DataInputStream inputStream) {
        int i = 0, j = 0;
        try {
            while (true) {
                int k = inputStream.readByte();
                i |= (k & 0x7F) << j++ * 7;
                if (j > 5) throw new RuntimeException("VarInt too big");
                if ((k & 0x80) != 128) break;
            }
        } catch (IOException ignored) {
        }
        return i;
    }

    @Getter
    @Nonnull
    private final ByteBuf buf = Unpooled.buffer();
    @Nonnull
    private final byte[] result;

    public PacketSerializer(@Nonnull String string) {
        writeString(string);
        result = buf.array();
        buf.release();
    }

    public PacketSerializer(int i) {
        writeVarInt(i);
        result = buf.array();
        buf.release();
    }

    public PacketSerializer() {
        result = buf.array();
        buf.release();
    }

    public void writeString(@Nonnull String s) {
        if (s.length() > 32767) {
            throw new IllegalArgumentException(String.format("Cannot send string longer than Short.MAX_VALUE (got %s characters)", s.length()));
        } else {
            byte[] b = s.getBytes(StandardCharsets.UTF_8);
            this.writeVarInt(b.length);
            buf.writeBytes(b);
        }
    }

    public void writeByte(byte b) {
        buf.writeByte(b);
    }

    public void writeVarInt(int value) {
        do {
            int part = value & 127;
            value >>>= 7;
            if (value != 0) {
                part |= 128;
            }
            getBuf().writeByte(part);
        } while (value != 0);
    }

    @Nonnull
    public byte[] toArray() {
        return this.result;
    }
}
