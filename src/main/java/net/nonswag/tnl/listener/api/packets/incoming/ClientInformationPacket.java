package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;

@Getter
@Setter
public class ClientInformationPacket implements IncomingPacket {
    @Nonnull
    private String language;
    @Nonnull
    private ChatVisibility chatVisibility;
    @Nonnull
    private HandSide mainHand;
    private int modelCustomisation;
    private int viewDistance;
    private boolean chatColors;
    private boolean textFiltering;
    private boolean listingAllowed;

    public ClientInformationPacket(@Nonnull String language, int viewDistance, @Nonnull ChatVisibility chatVisibility,
                                   boolean chatColors, int modelCustomisation, @Nonnull HandSide mainHand,
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

    public enum HandSide {
        LEFT, RIGHT
    }
}
