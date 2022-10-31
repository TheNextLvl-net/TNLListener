package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import java.util.UUID;

@Getter
@Setter
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
    public static ChatPacket create(@Nonnull String message, @Nonnull Type type, @Nonnull UUID sender) {
        return Mapping.get().packetManager().outgoing().chatPacket(message, type, sender);
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
