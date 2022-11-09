package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.Hand;
import org.bukkit.util.Vector;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class InteractPacket extends PacketBuilder {
    private int entityId;
    private boolean sneaking;

    public static abstract class Attack extends InteractPacket {
        protected Attack(int entityId, boolean sneaking) {
            super(entityId, sneaking);
        }

        public static Attack create(int entityId, boolean sneaking) {
            return Mapping.get().packetManager().incoming().attack(entityId, sneaking);
        }
    }

    @Getter
    @Setter
    public static abstract class Interact extends InteractPacket {
        private Hand hand;

        protected Interact(int entityId, boolean sneaking, Hand hand) {
            super(entityId, sneaking);
            this.hand = hand;
        }

        public static Interact create(int entityId, boolean sneaking, Hand hand) {
            return Mapping.get().packetManager().incoming().interactPacket(entityId, sneaking, hand);
        }
    }

    @Getter
    @Setter
    public static abstract class InteractAt extends Interact {
        private Vector location;

        protected InteractAt(int entityId, boolean sneaking, Hand hand, Vector location) {
            super(entityId, sneaking, hand);
            this.location = location;
        }

        public static InteractAt create(int entityId, boolean sneaking, Hand hand, Vector location) {
            return Mapping.get().packetManager().incoming().interactAtPacket(entityId, sneaking, hand, location);
        }
    }
}
