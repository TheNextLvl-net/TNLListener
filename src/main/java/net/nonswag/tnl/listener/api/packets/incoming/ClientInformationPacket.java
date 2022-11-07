package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;

@Getter
@Setter
public abstract class ClientInformationPacket extends PacketBuilder {
    private String language;
    private ChatVisibility chatVisibility;
    private Hand.Side mainHand;
    private int modelCustomisation;
    private int viewDistance;
    private boolean chatColors;
    private boolean textFiltering;
    private boolean listingAllowed;

    protected ClientInformationPacket(String language, int viewDistance, ChatVisibility chatVisibility,
                                      boolean chatColors, int modelCustomisation, Hand.Side mainHand,
                                      boolean textFiltering, boolean listingAllowed) {
        this.language = language;
        this.viewDistance = viewDistance;
        this.chatVisibility = chatVisibility;
        this.chatColors = chatColors;
        this.modelCustomisation = modelCustomisation;
        this.mainHand = mainHand;
        this.textFiltering = textFiltering;
        this.listingAllowed = listingAllowed;
    }

    public enum ChatVisibility {
        FULL, SYSTEM, HIDDEN
    }

    public static ClientInformationPacket create(String language, int viewDistance, ChatVisibility chatVisibility,
                                                 boolean chatColors, int modelCustomisation, Hand.Side mainHand,
                                                 boolean textFiltering, boolean listingAllowed) {
        return Mapping.get().packetManager().incoming().clientInformationPacket(language, viewDistance, chatVisibility, chatColors, modelCustomisation, mainHand, textFiltering, listingAllowed);
    }
}
