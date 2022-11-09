package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ClientInformationPacket extends PacketBuilder {
    private String language;
    private int viewDistance;
    private ChatVisibility chatVisibility;
    private boolean chatColors;
    private int modelCustomisation;
    private Hand.Side mainHand;
    private boolean textFiltering, listingAllowed;

    public enum ChatVisibility {
        FULL, SYSTEM, HIDDEN
    }

    public static ClientInformationPacket create(String language, int viewDistance, ChatVisibility chatVisibility,
                                                 boolean chatColors, int modelCustomisation, Hand.Side mainHand,
                                                 boolean textFiltering, boolean listingAllowed) {
        return Mapping.get().packetManager().incoming().clientInformationPacket(language, viewDistance, chatVisibility, chatColors, modelCustomisation, mainHand, textFiltering, listingAllowed);
    }
}
