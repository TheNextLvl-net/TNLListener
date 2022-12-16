package net.nonswag.tnl.listener.api.packets.outgoing;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.chat.ChatSession;
import net.nonswag.tnl.listener.api.entity.TNLEntityPlayer;
import net.nonswag.tnl.listener.api.gamemode.Gamemode;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.GameProfile;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlayerInfoUpdatePacket extends PacketBuilder {
    private List<Action> actions;
    private List<Entry> entries;

    public static PlayerInfoUpdatePacket create(List<Action> actions, List<Entry> entries) {
        return Mapping.get().packetManager().outgoing().playerInfoUpdatePacket(actions, entries);
    }

    public static PlayerInfoUpdatePacket create(Action action, Entry entry) {
        return create(List.of(action), List.of(entry));
    }

    public enum Action {
        ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, INITIALIZE_CHAT, UPDATE_LISTED
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Entry {
        private GameProfile profile;
        private boolean listed;
        private int ping;
        private Gamemode gamemode;
        @Nullable
        private Component displayName;
        @Nullable
        private ChatSession chatSession;

        public Entry(TNLEntityPlayer player) {
            this(player.getGameProfile(), player.isListed(), player.getPing(), player.getGamemode(), player.getDisplayName(), player.getChatSession());
        }
    }
}
