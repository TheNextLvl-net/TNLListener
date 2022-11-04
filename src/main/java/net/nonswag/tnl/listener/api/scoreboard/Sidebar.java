package net.nonswag.tnl.listener.api.scoreboard;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.listener.api.player.manager.ScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Getter
public final class Sidebar {

    private final Objective objective;
    private final ScoreboardManager scoreboardManager;

    public Sidebar(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
        Scoreboard scoreboard = scoreboardManager.getScoreboard();
        Objective display = scoreboard.getObjective("TNLSidebar");
        if (display != null) display.unregister();
        this.objective = scoreboard.registerNewObjective("TNLSidebar", "dummy", Component.empty());
        getObjective().setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Sidebar setTitle(String title) {
        getObjective().displayName(Component.text(Message.format(title, getScoreboardManager().getPlayer())));
        return this;
    }

    @SuppressWarnings("deprecation")
    public String getTitle() {
        return getObjective().getDisplayName();
    }

    public Sidebar setScores(String content) {
        String[] scores = content.split("\n");
        for (int i = 0; i < scores.length; i++) setScore(i, scores[scores.length - 1 - i]);
        return this;
    }

    public Sidebar setScore(int score, String content) {
        content = Message.format(content, getScoreboardManager().getPlayer());
        Team team = getTeamByScore(score);
        if (team == null) return this;
        team.prefix(Component.text(content.length() > 64 ? content.substring(0, 63) : content));
        return showScore(score);
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public String getScore(int score) {
        Team team = getTeamByScore(score);
        if (team != null) return team.getPrefix();
        return null;
    }

    public void remove() {
        for (int score : getScores()) hideScore(score);
        setTitle("");
    }

    public List<String> getScoreEntries() {
        List<String> scores = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            String score = getScore(i);
            if (score != null) scores.add(score);
        }
        return scores;
    }

    public List<Integer> getScores() {
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < 15; i++) if (getScore(i) != null) scores.add(i);
        return scores;
    }

    @Nullable
    private Team getTeamByScore(int score) {
        Entry name = Entry.getById(score);
        if (name != null) {
            Scoreboard scoreboard = getScoreboardManager().getScoreboard();
            Team team = scoreboard.getEntryTeam(name.getName());
            if (team == null) team = scoreboard.registerNewTeam(name.name());
            if (!team.hasEntry(name.getName())) team.addEntry(name.getName());
            return team;
        }
        return null;
    }

    private Sidebar showScore(int score) {
        Entry name = Entry.getById(score);
        if (name != null && !objective.getScore(name.getName()).isScoreSet()) {
            objective.getScore(name.getName()).setScore(score);
        }
        return this;
    }

    private Sidebar hideScore(int score) {
        Entry name = Entry.getById(score);
        if (name != null && objective.getScore(name.getName()).isScoreSet()) {
            getScoreboardManager().getScoreboard().resetScores(name.getName());
        }
        return this;
    }

    public enum Entry {
        ENTRY_0(ChatColor.BLACK, 0),
        ENTRY_1(ChatColor.DARK_BLUE, 1),
        ENTRY_2(ChatColor.DARK_GREEN, 2),
        ENTRY_3(ChatColor.DARK_AQUA, 3),
        ENTRY_4(ChatColor.DARK_RED, 4),
        ENTRY_5(ChatColor.DARK_PURPLE, 5),
        ENTRY_6(ChatColor.GOLD, 6),
        ENTRY_7(ChatColor.GRAY, 7),
        ENTRY_8(ChatColor.DARK_GRAY, 8),
        ENTRY_9(ChatColor.BLUE, 9),
        ENTRY_10(ChatColor.GREEN, 10),
        ENTRY_11(ChatColor.AQUA, 11),
        ENTRY_12(ChatColor.RED, 12),
        ENTRY_13(ChatColor.LIGHT_PURPLE, 13),
        ENTRY_14(ChatColor.YELLOW, 14);

        @Nonnull
        private final String name;
        private final int entry;

        Entry(ChatColor color, int entry) {
            this.name = color.toString();
            this.entry = entry;
        }

        @Nonnull
        public String getName() {
            return name;
        }

        public int getEntry() {
            return entry;
        }

        @Nullable
        public static Entry getById(int entry) {
            if (values().length <= entry) return null;
            for (Entry value : values()) if (value.getEntry() == entry) return value;
            return null;
        }
    }
}
