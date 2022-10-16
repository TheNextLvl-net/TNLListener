package net.nonswag.tnl.listener.api.scoreboard;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import javax.annotation.Nonnull;
import java.util.Objects;

@Getter
public class Team implements Cloneable {

    @Getter
    @Nonnull
    private static final Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
    @Nonnull
    public static final Team NONE = new Team(Integer.MAX_VALUE);

    private final int id;
    @Nonnull
    private final org.bukkit.scoreboard.Team team;
    @Nonnull
    private String prefix = "";
    @Nonnull
    private String suffix = "";
    @Nonnull
    private ChatColor color = ChatColor.WHITE;

    public Team(int id) {
        this.id = id;
        org.bukkit.scoreboard.Team team = getScoreboard().getTeam(String.valueOf(id));
        if (team != null) team.unregister();
        this.team = getScoreboard().registerNewTeam(String.valueOf(id));
    }

    @Nonnull
    public Team setPrefix(@Nonnull String prefix) {
        this.prefix = prefix;
        getTeam().prefix(Component.text(prefix));
        return this;
    }

    @Nonnull
    public Team setSuffix(@Nonnull String suffix) {
        this.suffix = suffix;
        getTeam().suffix(Component.text(suffix));
        return this;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public Team setColor(@Nonnull ChatColor color) {
        this.color = color;
        getTeam().setColor(color);
        return this;
    }

    @Nonnull
    @Override
    public Team clone() {
        return clone(getId());
    }

    @Nonnull
    public Team clone(int id) {
        return new Team(id).setPrefix(getPrefix()).setSuffix(getSuffix()).setColor(getColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team1 = (Team) o;
        return id == team1.id && team.equals(team1.team) && prefix.equals(team1.prefix) && suffix.equals(team1.suffix) && color == team1.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team, prefix, suffix, color);
    }

    @Override
    public String toString() {
        return getPrefix() + getColor() + "Player" + getSuffix();
    }
}
