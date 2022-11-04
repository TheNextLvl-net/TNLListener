package net.nonswag.tnl.listener.api.data;

import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Buffer {

    public static byte[] toByteArray(String... strings) {
        byte[] bytes = new byte[strings.length];
        for (int i = 0; i < strings.length; i++) bytes[i] = Byte.parseByte(strings[i]);
        return bytes;
    }

    public static void writeVarInt(DataOutputStream stream, int var) throws IOException {
        stream.write(var);
    }

    public static void writeString(DataOutputStream stream, String string) throws IOException {
        stream.write(string.getBytes(StandardCharsets.UTF_8));
    }

    public static int readVarInt(DataInputStream stream) throws IOException {
        return stream.read();
    }

    public static String readString(DataInputStream stream) throws IOException {
        return stream.readUTF();
    }
}
