package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.ParametersAreNullableByDefault;

@Getter
@Setter
@FieldsAreNullableByDefault
@ParametersAreNullableByDefault
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ServerDataPacket extends PacketBuilder {
    private Component motd;
    private String serverIcon;
    private boolean chatPreview;

    public static ServerDataPacket create(Component motd, String serverIcon, boolean chatPreview) {
        return Mapping.get().packetManager().outgoing().serverDataPacket(motd, serverIcon, chatPreview);
    }
}
