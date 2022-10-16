package net.nonswag.tnl.listener.api.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public final class ModPacketSerializer {

    public static byte[] getBytesToSend(String messageKey, String messageContents) {
        ByteBuf byteBuf = Unpooled.buffer();
        writeString(byteBuf, messageKey);
        writeString(byteBuf, messageContents);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        byteBuf.release();
        return bytes;
    }

    private static void writeVarIntToBuffer(ByteBuf buf, int input) {
        while ((input & -128) != 0) {
            buf.writeByte(input & 127 | 128);
            input >>>= 7;
        }
        buf.writeByte(input);
    }

    private static void writeString(@Nonnull ByteBuf buf, @Nonnull String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max 32767)");
        } else {
            writeVarIntToBuffer(buf, bytes.length);
            buf.writeBytes(bytes);
        }
    }

    public static int readVarIntFromBuffer(@Nonnull ByteBuf buf) {
        int i = 0;
        int j = 0;
        byte b0;
        do {
            b0 = buf.readByte();
            i |= (b0 & 127) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
        } while ((b0 & 128) == 128);
        return i;
    }

    @Nonnull
    public static String readString(@Nonnull ByteBuf buf, int maxLength) {
        int i = readVarIntFromBuffer(buf);
        if (i > maxLength * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + maxLength * 4 + ")");
        } else if (i < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            byte[] bytes = new byte[i];
            buf.readBytes(bytes);
            String s = new String(bytes, StandardCharsets.UTF_8);
            if (s.length() > maxLength) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
            } else return s;
        }
    }
}
