package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.gamemode.Gamemode;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class GameStateChangePacket extends PacketBuilder {

    @Nonnull
    public static NoRespawnBlockAvailable NO_RESPAWN_BLOCK_AVAILABLE = new NoRespawnBlockAvailable();
    @Nonnull
    public static StopRaining START_RAINING = new StopRaining();
    @Nonnull
    public static StartRaining STOP_RAINING = new StartRaining();
    @Nonnull
    public static ChangeGamemode CHANGE_GAMEMODE = new ChangeGamemode();
    @Nonnull
    public static WinGame WIN_GAME = new WinGame();
    @Nonnull
    public static DemoEvent DEMO_EVENT = new DemoEvent();
    @Nonnull
    public static ArrowHitPlayer ARROW_HIT_PLAYER = new ArrowHitPlayer();
    @Nonnull
    public static RainLevelChange RAIN_LEVEL_CHANGE = new RainLevelChange();
    @Nonnull
    public static ThunderLevelChange THUNDER_LEVEL_CHANGE = new ThunderLevelChange();
    @Nonnull
    public static PufferFishSting PUFFER_FISH_STING = new PufferFishSting();
    @Nonnull
    public static GuardianElderEffect GUARDIAN_ELDER_EFFECT = new GuardianElderEffect();
    @Nonnull
    public static ImmediateRespawn IMMEDIATE_RESPAWN = new ImmediateRespawn();

    @Nonnull
    private Identifier identifier;
    private float state;

    protected GameStateChangePacket(@Nonnull Identifier identifier, float state) {
        this.identifier = identifier;
        this.state = state;
    }

    @Nonnull
    public static GameStateChangePacket create(@Nonnull Identifier identifier, float state) {
        return Mapping.get().packetManager().outgoing().gameStateChangePacket(identifier, state);
    }

    @Nonnull
    public static <I extends Identifier> GameStateChangePacket create(@Nonnull I identifier, @Nonnull MutualGetter<I, Float> state) {
        return create(identifier, state.get(identifier));
    }

    @Nonnull
    public static GameStateChangePacket create(@Nonnull Identifier identifier) {
        return create(identifier, 0);
    }

    @Getter
    public static abstract class Identifier {

        private final int id;

        protected Identifier(int id) {
            this.id = id;
        }
    }

    public static final class NoRespawnBlockAvailable extends Identifier {
        private NoRespawnBlockAvailable() {
            super( 0);
        }
    }

    public static final class StopRaining extends Identifier {
        private StopRaining() {
            super(1);
        }
    }

    public static final class StartRaining extends Identifier {
        private StartRaining() {
            super(2);
        }
    }

    public static final class ChangeGamemode extends Identifier {
        private ChangeGamemode() {
            super(3);
        }

        public float gamemode(@Nonnull Gamemode gamemode) {
            return gamemode.getId();
        }
    }

    public static final class WinGame extends Identifier {
        private WinGame() {
            super(4);
        }

        public final float RESPAWN = 0, ROLL_CREDITS = 1;
    }

    public static final class DemoEvent extends Identifier {
        public final int INTRO = 0, MOVEMENTS = 101, JUMPING = 102, INVENTORY = 103, OVER = 104;

        private DemoEvent() {
            super(5);
        }
    }

    public static final class ArrowHitPlayer extends Identifier {
        private ArrowHitPlayer() {
            super(6);
        }
    }

    public static final class RainLevelChange extends Identifier {
        private RainLevelChange() {
            super(7);
        }

        public long level(long level) {
            return level;
        }
    }

    public static final class ThunderLevelChange extends Identifier {
        private ThunderLevelChange() {
            super(8);
        }

        public long level(long level) {
            return level;
        }
    }

    public static final class PufferFishSting extends Identifier {
        private PufferFishSting() {
            super(9);
        }
    }

    public static final class GuardianElderEffect extends Identifier {
        private GuardianElderEffect() {
            super(10);
        }
    }

    public static final class ImmediateRespawn extends Identifier {

        private ImmediateRespawn() {
            super(11);
        }

        public float immediate(boolean immediate) {
            return immediate ? 1 : 0;
        }
    }
}
