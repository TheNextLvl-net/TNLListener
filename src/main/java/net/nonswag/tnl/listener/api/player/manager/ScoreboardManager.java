package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.listener.api.scoreboard.Sidebar;
import net.nonswag.tnl.listener.api.scoreboard.Team;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class ScoreboardManager extends Manager {

    @Getter
    @Nonnull
    protected Team team = Team.NONE;
    @Setter
    @Nullable
    protected Sidebar sidebar = null;

    protected ScoreboardManager() {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (!getScoreboard().equals(manager.getMainScoreboard())) return;
        setScoreboard(manager.getNewScoreboard());
    }

    @Nonnull
    public Scoreboard getScoreboard() {
        return getPlayer().bukkit().getScoreboard();
    }

    public void setScoreboard(@Nonnull Scoreboard scoreboard) {
        getPlayer().bukkit().setScoreboard(scoreboard);
    }

    public void setTeam(@Nonnull Team team) {
        this.team = team;
        updateTeam();
    }

    @SuppressWarnings("deprecation")
    public void updateTeam() {
        getTeam().getTeam().addEntry(getPlayer().getName());
        getPlayer().bukkit().displayName(Component.text(Message.format(getTeam().getPrefix() + getTeam().getColor() + getPlayer().getName() + getTeam().getSuffix(), getPlayer())));
        for (TNLPlayer all : Listener.getOnlinePlayers()) {
            Scoreboard scoreboard = all.scoreboardManager().getScoreboard();
            org.bukkit.scoreboard.Team npcs = scoreboard.getTeam("npcs");
            if (npcs == null) npcs = scoreboard.registerNewTeam("npcs");
            for (FakePlayer npc : FakePlayer.getFakePlayers()) {
                if (!npc.canSee().test(all)) continue;
                if (npcs.hasEntry(npc.getPlayer().getGameProfile().getName())) continue;
                npcs.addEntry(npc.getPlayer().getGameProfile().getName());
            }
            npcs.color(NamedTextColor.DARK_GRAY);
            npcs.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            for (org.bukkit.scoreboard.Team team : Team.getScoreboard().getTeams()) {
                org.bukkit.scoreboard.Team allTeam = scoreboard.getTeam(team.getName());
                if (allTeam == null) allTeam = scoreboard.registerNewTeam(team.getName());
                allTeam.displayName(Component.text(Message.format(team.getDisplayName(), all)));
                allTeam.prefix(Component.text(Message.format(team.getPrefix(), all)));
                allTeam.suffix(Component.text(Message.format(team.getSuffix(), all)));
                allTeam.color(NamedTextColor.nearestTo(team.color()));
                for (String entry : allTeam.getEntries()) allTeam.removeEntry(entry);
                for (String entry : team.getEntries()) allTeam.addEntry(entry);
                for (org.bukkit.scoreboard.Team.Option option : org.bukkit.scoreboard.Team.Option.values()) {
                    allTeam.setOption(option, team.getOption(option));
                }
                if (Listener.getCollision() != null) {
                    allTeam.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, Listener.getCollision());
                }
                if (Listener.getNametagVisibility() != null) {
                    allTeam.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, Listener.getNametagVisibility());
                }
                allTeam.setCanSeeFriendlyInvisibles(team.canSeeFriendlyInvisibles());
                allTeam.setAllowFriendlyFire(team.allowFriendlyFire());
            }
        }
    }

    @Nonnull
    public Sidebar getSidebar() {
        if (sidebar == null) sidebar = new Sidebar(this);
        return sidebar;
    }
}
