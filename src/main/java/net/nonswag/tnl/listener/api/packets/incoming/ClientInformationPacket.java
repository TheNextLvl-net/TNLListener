package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ClientInformationPacket extends PacketBuilder {
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

    protected ClientInformationPacket(@Nonnull String language, int viewDistance, @Nonnull ChatVisibility chatVisibility,
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

    @Nonnull
    public static ClientInformationPacket create(@Nonnull String language, int viewDistance, @Nonnull ChatVisibility chatVisibility,
                                                 boolean chatColors, int modelCustomisation, @Nonnull HandSide mainHand,
                                                 boolean textFiltering, boolean listingAllowed) {
        return Mapping.get().packetManager().incoming().clientInformationPacket(language, viewDistance, chatVisibility, chatColors, modelCustomisation, mainHand, textFiltering, listingAllowed);
    }
}
