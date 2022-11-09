package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.*;
import lombok.experimental.Accessors;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class MerchantOffersPacket extends PacketBuilder {
    private int containerId;
    private List<Offer> offers;
    private int level, experience;
    @Accessors(fluent = true)
    private boolean showProgress, canRestock;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Builder(builderClassName = "Builder")
    public static class Offer {
        private TNLItem baseCost, extraCost, result;
        private int uses, maxUses;
        private boolean rewardExp;
        private int specialPrice, bonus;
        private float priceMultiplier;
        private int xp;
        private boolean ignoreDiscounts;
    }

    public static MerchantOffersPacket create(int containerId, List<Offer> offers, int level, int experience, boolean showProgress, boolean canRestock) {
        return Mapping.get().packetManager().outgoing().merchantOffersPacket(containerId, offers, level, experience, showProgress, canRestock);
    }
}
