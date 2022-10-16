package net.nonswag.tnl.listener.api.packets;

import lombok.Getter;
import net.nonswag.core.api.object.MutualGetter;
import net.nonswag.tnl.listener.api.gamemode.Gamemode;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class GameStateChangePacket extends PacketBuilder {

    @Nullable
    public static NoRespawnBlockAvailable NO_RESPAWN_BLOCK_AVAILABLE = new NoRespawnBlockAvailable();
    @Nonnull
    public static StopRain STOP_RAIN = new StopRain();
    @Nonnull
    public static StartRain START_RAIN = new StartRain();
    @Nonnull
    public static ChangeGamemode CHANGE_GAMEMODE = new ChangeGamemode();
    @Nonnull
    public static WinGame WIN_GAME = new WinGame();
    @Nonnull
    public static Demo DEMO = new Demo();
    @Nonnull
    public static ArrowHit ARROW_HIT = new ArrowHit();
    @Nonnull
    public static RainLevelChange RAIN_LEVEL_CHANGE = new RainLevelChange();
    @Nonnull
    public static ThunderLevelChange THUNDER_LEVEL_CHANGE = new ThunderLevelChange();
    @Nonnull
    public static PufferFishSting PUFFER_FISH_STING = new PufferFishSting();
    @Nonnull
    public static ElderGuardian ELDER_GUARDIAN = new ElderGuardian();
    @Nonnull
    public static Respawn RESPAWN = new Respawn();

    @Nonnull
    private Identifier identifier;
    private float state;

    protected GameStateChangePacket(@Nonnull Identifier identifier, float state) {
        this.identifier = identifier;
        this.state = state;
    }

    @Nonnull
    public GameStateChangePacket setIdentifier(@Nonnull Identifier identifier) {
        this.identifier = identifier;
        return this;
    }

    @Nonnull
    public GameStateChangePacket setState(float state) {
        this.state = state;
        return this;
    }

    @Nonnull
    public static GameStateChangePacket create(@Nonnull Identifier identifier, float state) {
        return Mapping.get().packets().gameStateChangePacket(identifier, state);
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

    public static final class StopRain extends Identifier {
        private StopRain() {
            super(1);
        }
    }

    public static final class StartRain extends Identifier {
        private StartRain() {
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

    public static final class Demo extends Identifier {
        public final float WELCOME = 0, MOVEMENTS = 101, JUMPING = 102, INVENTORY = 103, OVER = 104;

        private Demo() {
            super(5);
        }
    }

    public static final class ArrowHit extends Identifier {
        private ArrowHit() {
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

    public static final class ElderGuardian extends Identifier {
        private ElderGuardian() {
            super(10);
        }
    }

    public static final class Respawn extends Identifier {

        private Respawn() {
            super(11);
        }

        public float immediate(boolean immediate) {
            return immediate ? 1 : 0;
        }
    }
}
