package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public abstract class SetPlayerTeamPacket extends PacketBuilder {

    @Nonnull
    private Option option;
    @Nonnull
    private String name;
    @Nullable
    private Parameters parameters;
    @Nonnull
    private List<String> entries;

    protected SetPlayerTeamPacket(@Nonnull String name, @Nonnull Option option, @Nullable Parameters parameters, @Nonnull List<String> entries) {
        this.name = name;
        this.option = option;
        this.parameters = parameters;
        this.entries = entries;
    }

    @Getter
    @Setter
    public static class Parameters {

        @Nonnull
        private String displayName, prefix, suffix;
        @Nonnull
        private Visibility nameTagVisibility = Visibility.ALWAYS;
        @Nonnull
        private CollisionRule collisionRule = CollisionRule.ALWAYS;
        @Nonnull
        private ChatColor color = ChatColor.RESET;
        private boolean allowFriendlyFire = true, seeFriendlyInvisibles = true;

        public enum Visibility {
            ALWAYS, NEVER, HIDE_FOR_OTHER_TEAMS, HIDE_FOR_OWN_TEAM
        }

        public enum CollisionRule {
            ALWAYS, NEVER, PUSH_OTHER_TEAMS, PUSH_OWN_TEAM
        }

        public int packOptions() {
            return isAllowFriendlyFire() && isSeeFriendlyInvisibles() ? 3 : isAllowFriendlyFire() ? 1 : isSeeFriendlyInvisibles() ? 2 : 0;
        }

        public void unpackOptions(int options) {
            setAllowFriendlyFire(options == 1 || options == 3);
            setSeeFriendlyInvisibles(options == 2 || options == 3);
        }
    }

    public enum Option {
        ADD, REMOVE, CHANGE, JOIN, LEAVE;

        public boolean needsParameters() {
            return equals(ADD) || equals(CHANGE);
        }

        public boolean needsEntries() {
            return equals(ADD) || equals(JOIN) || equals(LEAVE);
        }
    }

    @Nonnull
    public static SetPlayerTeamPacket create(@Nonnull String name, @Nonnull Option option, @Nullable Parameters parameters, @Nonnull List<String> entries) {
        return Mapping.get().packetManager().outgoing().setPlayerTeamPacket(name, option, parameters, entries);
    }
}
