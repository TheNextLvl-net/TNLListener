package net.nonswag.tnl.listener.api.gui.builtin;

import lombok.Getter;
import net.nonswag.tnl.listener.api.gui.GUI;
import net.nonswag.tnl.listener.api.gui.GUIItem;
import net.nonswag.tnl.listener.api.gui.GUIOverflowException;
import net.nonswag.tnl.listener.api.gui.Interaction;
import net.nonswag.tnl.listener.api.item.TNLItem;
import net.nonswag.tnl.listener.api.mapper.Mapping;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.plugin.PluginBuilder;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PluginsGUI extends GUI {

    @Getter
    @Nonnull
    protected static final PluginsGUI instance = new PluginsGUI();

    @Nonnull
    private final GUIItem close = TNLItem.create(Material.BARRIER).setName("§8* §cClose Inventory").
            toGUIItem().addInteractions(new Interaction(player -> player.interfaceManager().closeGUI()));
    private long lastUpdate = 0;

    protected PluginsGUI() {
        super(6, "§8» §6§lPlugin§e§lList");
        setOpenListener(player -> {
            if (player.permissionManager().hasPermission("tnl.admin") || System.currentTimeMillis() - lastUpdate >= 5000) {
                lastUpdate = System.currentTimeMillis();
                update();
            }
            return true;
        });
    }

    private void update() {
        try {
            formatDefault();
            setItem(49, close);
            for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                List<String> authors = new ArrayList<>();
                plugin.getDescription().getAuthors().stream().sorted().forEach(authors::add);
                TNLItem item = TNLItem.create(Material.BOOK).setName("§8» §r" + PluginManager.getName(plugin, true));
                if (authors.size() != 1) {
                    String[] lore = new String[authors.size() + 1];
                    lore[0] = "§8* §7Authors §8(§a" + authors.size() + "§8)";
                    for (int i = 0; i < authors.size(); i++) lore[i + 1] = "§6" + authors.get(i);
                    addItems(item.withLore(lore).toGUIItem().addInteractions(new Interaction(consume(plugin))));
                } else
                    addItems(item.withLore("§7Author§8: §6" + authors.get(0)).toGUIItem().addInteractions(new Interaction(consume(plugin))));
                if (plugin instanceof PluginBuilder) item.addLore("§8[§7Module§8]");
            }
            Mapping.Info info = Mapping.get().info();
            TNLItem mapping = TNLItem.create(Material.KNOWLEDGE_BOOK).setName("§8* §7Mapping§8: §6" + info.name());
            if (info.authors().length != 1) {
                mapping.addLore("§8* §7Authors §8(§a" + info.authors().length + "§8)");
                for (String author : info.authors()) mapping.addLore("§6" + author);
            } else mapping.withLore("§7Author§8: §6" + info.authors()[0]);
            if (!info.description().isEmpty()) mapping.addLore("§8* §7Description§8: §6" + info.description());
            if (!info.version().isEmpty()) mapping.addLore("§8* §7Version§8: §6" + info.version());
            addItem(mapping.toGUIItem().addInteractions(new Interaction(player -> {
                if (!player.delay("mapping-url", 2000)) return;
                player.cooldownManager().setCooldown(Material.KNOWLEDGE_BOOK, 40, true);
                player.messenger().sendMessage("%prefix% §7Website§8: §6" + info.website());
            })));
        } catch (GUIOverflowException ignored) {
            // TODO: 1/9/22  infinite pages
        }
    }

    @Nonnull
    private Consumer<TNLPlayer> consume(@Nonnull Plugin plugin) {
        return player -> {
            if (!player.delay("plugin-url", 2000)) return;
            player.cooldownManager().setCooldown(Material.BOOK, 40, true);
            if (plugin.getDescription().getWebsite() == null || plugin.getDescription().getWebsite().isEmpty()) {
                player.messenger().sendMessage("%prefix% §7Google§8: §6https://www.google.com/search?q=" + plugin.getName());
            } else player.messenger().sendMessage("%prefix% §7Website§8: §6" + plugin.getDescription().getWebsite());
        };
    }

    @Nonnull
    @Override
    public GUI formatDefault() {
        super.formatDefault();
        for (int i = 1; i <= 7; i++) for (int i1 = 1; i1 <= 4; i1++) remove(i + 9 * i1);
        return this;
    }
}
