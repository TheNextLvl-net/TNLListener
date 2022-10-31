package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class InteractPacket extends PacketBuilder {
    private int entityId;
    private boolean sneaking;

    private InteractPacket(int entityId, boolean sneaking) {
        this.entityId = entityId;
        this.sneaking = sneaking;
    }

    public static abstract class Attack extends InteractPacket {
        protected Attack(int entityId, boolean sneaking) {
            super(entityId, sneaking);
        }

        @Nonnull
        public static Attack create(int entityId, boolean sneaking) {
            return Mapping.get().packetManager().incoming().attack(entityId, sneaking);
        }
    }

    @Getter
    @Setter
    public static abstract class Interact extends InteractPacket {
        @Nonnull
        private Hand hand;

        protected Interact(int entityId, boolean sneaking, @Nonnull Hand hand) {
            super(entityId, sneaking);
            this.hand = hand;
        }

        @Nonnull
        public static Interact create(int entityId, boolean sneaking, @Nonnull Hand hand) {
            return Mapping.get().packetManager().incoming().interactPacket(entityId, sneaking, hand);
        }
    }

    @Getter
    @Setter
    public static abstract class InteractAt extends Interact {
        @Nonnull
        private Vector location;

        protected InteractAt(int entityId, boolean sneaking, @Nonnull Hand hand, @Nonnull Vector location) {
            super(entityId, sneaking, hand);
            this.location = location;
        }

        @Nonnull
        public static InteractAt create(int entityId, boolean sneaking, @Nonnull Hand hand, @Nonnull Vector location) {
            return Mapping.get().packetManager().incoming().interactAtPacket(entityId, sneaking, hand, location);
        }
    }
}
