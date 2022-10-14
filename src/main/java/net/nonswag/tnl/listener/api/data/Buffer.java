package net.nonswag.tnl.listener.api.data;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Buffer {

    public static byte[] toByteArray(@Nonnull String... strings) {
        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++) bytes[i] = Byte.parseByte(strings[i]);
        return bytes;
    }

    public static void writeVarInt(@Nonnull DataOutputStream stream, int var) throws IOException {
        stream.write(var);
    }

    public static void writeString(@Nonnull DataOutputStream stream, @Nonnull String string) throws IOException {
        stream.write(string.getBytes(StandardCharsets.UTF_8));
    }

    public static int readVarInt(@Nonnull DataInputStream stream) throws IOException {
        return stream.read();
    }

    @Nonnull
    public static String readString(@Nonnull DataInputStream stream) throws IOException {
        return stream.readUTF();
    }
}
