package net.nonswag.tnl.manager.commands.subcommands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.nonswag.core.api.command.CommandSource;
import net.nonswag.core.api.command.Invocation;
import net.nonswag.core.api.errors.DownloadException;
import net.nonswag.core.api.file.helper.FileDownloader;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.tnl.listener.Bootstrap;
import net.nonswag.tnl.listener.api.command.exceptions.InvalidUseException;
import net.nonswag.tnl.listener.api.command.simple.SubCommand;
import net.nonswag.tnl.listener.api.plugin.PluginManager;
import net.nonswag.tnl.listener.api.plugin.PluginUpdate;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginInstall extends SubCommand {

    @Nonnull
    private static final List<String> tnlProducts = new ArrayList<>();
    private static long lastUpdate = 0;

    public PluginInstall() {
        super("install", "tnl.manage");
    }

    @Override
    protected void execute(@Nonnull Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        if (args.length < 2) throw new InvalidUseException(this);
        String product = args[1];
        List<String> products = getTnlProducts();
        List<String> missing = getMissingTNLProducts();
        if (products.contains(product)) {
            if (missing.contains(product)) {
                source.sendMessage("%prefix% §aDownloading plugin §6" + product);
                Bootstrap.getInstance().async(() -> {
                    try {
                        FileDownloader.download(PluginUpdate.PLUGIN_URL.concat(product).concat(".jar"), new File("plugins", product.concat(".jar")));
                        source.sendMessage("%prefix% §aLoading plugin §6" + product);
                        PluginManager.load(new File("plugins/" + product + ".jar"));
                        source.sendMessage("%prefix% §aInstalled plugin §6" + product);
                    } catch (DownloadException | IOException e) {
                        source.sendMessage("%prefix% §cFailed to download plugin §4" + product);
                        source.sendMessage("%prefix% §c" + e.getMessage());
                    } catch (Throwable t) {
                        source.sendMessage("%prefix% §cFailed to load plugin §4" + product);
                        source.sendMessage("%prefix% §c" + t.getMessage());
                    }
                });
            } else source.sendMessage("%prefix% §4" + product + "§c is already installed");
        } else source.sendMessage("%prefix% §4" + product + "§c is not a §8(§7public§8)§c tnl product");
    }

    @Nonnull
    @Override
    protected List<String> suggest(@Nonnull Invocation invocation) {
        if (invocation.arguments().length > 2) return new ArrayList<>();
        return getMissingTNLProducts();
    }

    @Override
    public void usage(@Nonnull Invocation invocation) {
        invocation.source().sendMessage("%prefix% §c/plugin install §8[§6Product§8]");
    }

    @Nonnull
    static List<String> getMissingTNLProducts() {
        List<String> missingProducts = new ArrayList<>();
        for (String product : getTnlProducts()) {
            if (Bukkit.getPluginManager().isPluginEnabled(product)) continue;
            if (new File("plugins/" + product + ".jar").exists()) continue;
            missingProducts.add(product);
        }
        return missingProducts;
    }

    @Nonnull
    static List<String> getTnlProducts() {
        if (System.currentTimeMillis() - lastUpdate > 60000) {
            try {
                JsonElement element = PluginUpdate.retrievePluginAPI(null);
                if (element.isJsonObject()) {
                    JsonObject root = element.getAsJsonObject();
                    tnlProducts.clear();
                    for (Map.Entry<String, JsonElement> entry : root.entrySet()) tnlProducts.add(entry.getKey());
                    lastUpdate = System.currentTimeMillis();
                }
            } catch (IOException e) {
                Logger.error.println(e);
            }
        }
        return tnlProducts;
    }

    @Nonnull
    static List<String> getInstalledTNLProducts() {
        List<String> products = new ArrayList<>(PluginInstall.getTnlProducts());
        products.removeIf(product -> !Bukkit.getPluginManager().isPluginEnabled(product));
        products.add("Mapping");
        return products;
    }
}
