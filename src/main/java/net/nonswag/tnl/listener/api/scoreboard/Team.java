package net.nonswag.tnl.listener.api.scoreboard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Team implements Cloneable {

    @Getter
    private static final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
    public static final Team NONE = new Team(Integer.MAX_VALUE);

    private final int id;
    private final org.bukkit.scoreboard.Team team;
    private String prefix = "";
    private String suffix = "";
    private ChatColor color = ChatColor.WHITE;

    public Team(int id) {
        this.id = id;
        org.bukkit.scoreboard.Team team = getScoreboard().getTeam(String.valueOf(id));
        if (team != null) team.unregister();
        this.team = getScoreboard().registerNewTeam(String.valueOf(id));
    }

    public Team setPrefix(String prefix) {
        this.prefix = prefix;
        getTeam().prefix(Component.text(prefix));
        return this;
    }

    public Team setSuffix(String suffix) {
        this.suffix = suffix;
        getTeam().suffix(Component.text(suffix));
        return this;
    }

    @SuppressWarnings("deprecation")
    public Team setColor(ChatColor color) {
        this.color = color;
        getTeam().setColor(color);
        return this;
    }

    @Override
    public Team clone() {
        return clone(getId());
    }

    public Team clone(int id) {
        return new Team(id).setPrefix(getPrefix()).setSuffix(getSuffix()).setColor(getColor());
    }

    @Override
    public String toString() {
        return getPrefix() + getColor() + "Player" + getSuffix();
    }
}
