package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.UUID;

@Getter
public abstract class ChatPacket extends PacketBuilder {

    @Nonnull
    private static final UUID SYSTEM = new UUID(0L, 0L);

    @Nonnull
    private String message;
    @Nonnull
    private Type type;
    @Nonnull
    private UUID sender;

    protected ChatPacket(@Nonnull String message, @Nonnull Type type, @Nonnull UUID sender) {
        this.message = message;
        this.type = type;
        this.sender = sender;
    }

    @Nonnull
    public ChatPacket setMessage(@Nonnull String message) {
        this.message = message;
        return this;
    }

    @Nonnull
    public ChatPacket setSender(@Nonnull UUID sender) {
        this.sender = sender;
        return this;
    }

    @Nonnull
    public ChatPacket setType(@Nonnull Type type) {
        this.type = type;
        return this;
    }

    @Nonnull
    public static ChatPacket create(@Nonnull String message, @Nonnull Type type, @Nonnull UUID sender) {
        return Mapping.get().packets().chatPacket(message, type, sender);
    }

    @Nonnull
    public static ChatPacket create(@Nonnull String message, @Nonnull UUID sender) {
        return create(message, Type.SYSTEM, sender);
    }

    @Nonnull
    public static ChatPacket create(@Nonnull String message, @Nonnull Type type) {
        return create(message, type, SYSTEM);
    }

    @Nonnull
    public static ChatPacket create(@Nonnull String message) {
        return create(message, Type.SYSTEM);
    }

    public enum Type {
        CHAT, SYSTEM, GAME_INFO
    }
}
