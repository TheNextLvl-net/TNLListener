package net.nonswag.tnl.listener.api.plugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.errors.DownloadException;
import net.nonswag.core.api.file.helper.FileDownloader;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.core.api.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;

@Getter
@Setter(AccessLevel.PROTECTED)
public class PluginUpdate {

    public static final String API_URL = "https://www.thenextlvl.net/api/";
    public static final String PLUGIN_URL = "https://www.thenextlvl.net/static/plugins/";
    public static final String VERIFICATION_URL = "https://www.thenextlvl.net/download/?plugin=";

    private final String plugin;
    private final long currentVersion;
    private double latestUpdate = -1;
    private boolean upToDate = false;
    private boolean downloaded = false;
    private boolean failed = false;
    private boolean accessible = true;
    @Nullable
    private final String key;

    public PluginUpdate(String plugin, long currentVersion) {
        this(plugin, currentVersion, null);
    }

    public PluginUpdate(String plugin, long currentVersion, @Nullable String key) {
        this.plugin = plugin;
        this.currentVersion = currentVersion;
        this.key = key;
        update();
    }

    public PluginUpdate(Plugin plugin) {
        this(plugin, null);
    }

    public PluginUpdate(Plugin plugin, @Nullable String key) {
        this(plugin.getName(), new File("plugins/" + plugin.getName() + ".jar").lastModified() / 1000, key);
    }

    private boolean updateAccess() {
        if (getKey() != null) {
            String url = VERIFICATION_URL + getPlugin() + "&code=" + getKey();
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();
                String disposition = connection.getHeaderField("Content-Disposition");
                setAccessible(disposition != null && disposition.contains("filename="));
                connection.disconnect();
            } catch (Exception e) {
                Logger.error.println("Failed to check access for <'" + getPlugin() + "'>", e);
                setAccessible(false);
            }
        } else setAccessible(true);
        return isAccessible();
    }

    public void update() {
        try {
            if (!updateAccess()) return;
            JsonElement root = retrievePluginAPI();
            if (root.isJsonObject()) {
                JsonElement plugin = root.getAsJsonObject().get(getPlugin());
                if (plugin != null && plugin.isJsonObject()) {
                    setLatestUpdate(plugin.getAsJsonObject().get("last-update").getAsDouble());
                    setUpToDate(getCurrentVersion() >= getLatestUpdate());
                } else Logger.error.println("<'" + getPlugin() + "'> is not a (public) plugin by TheNextLvl.net");
            } else Logger.error.println("Invalid server respond", root.toString());
        } catch (MalformedJsonException e) {
            Logger.error.println("Failed to retrieve plugin api", e);
            setFailed(true);
        } catch (Exception e) {
            Logger.error.println("Failed to update plugin <'" + getPlugin() + "'>", e);
            setFailed(true);
        }
    }

    public void downloadUpdate() {
        if (isFailed() || !isAccessible()) return;
        if (!isUpToDate()) {
            try {
                String name = getPlugin().concat(".jar");
                String url = getKey() != null ? (VERIFICATION_URL + getPlugin() + "&code=" + getKey()) : (PLUGIN_URL + name);
                FileDownloader.download(url, new File(Bukkit.getUpdateFolderFile(), name));
                Logger.debug.println("Downloaded latest version of <'" + getPlugin() + "'>");
                setDownloaded(true);
            } catch (DownloadException e) {
                Logger.error.printf("Failed to update plugin <'%s'>", getPlugin()).println();
                if (getKey() != null) {
                    Logger.error.println("It seems like <'" + getKey() + "'> is not a valid license for this product");
                    Plugin pl = Bukkit.getPluginManager().getPlugin(getPlugin());
                    if (pl != null) PluginManager.unloadSecure(pl);
                }
                Logger.error.println(e);
                setFailed(true);
                setDownloaded(false);
            } catch (Exception e) {
                Logger.error.printf("Failed to download <'%s'>: %s", getPlugin(), e.getMessage()).println();
                if (!(e instanceof SocketTimeoutException)) Logger.error.println(e);
                setFailed(true);
                setDownloaded(false);
            }
        } else Logger.debug.println("The plugin <'" + getPlugin() + "'> is up-to-date");
    }

    @Nullable
    private static JsonElement cachedApi = null;
    private static long lastUpdate = 0;

    public static synchronized JsonElement retrievePluginAPI(@Nullable String api) throws IOException {
        if (cachedApi != null && System.currentTimeMillis() - lastUpdate > 60000) return cachedApi;
        HttpsURLConnection connection = (HttpsURLConnection) new URL(api == null ? API_URL : api).openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line.replace(" ", ""));
        reader.close();
        connection.disconnect();
        String document = sb.toString().replace("<pre>", "").replace("</pre>", "");
        try {
            lastUpdate = System.currentTimeMillis();
            return cachedApi = JsonHelper.parse(document);
        } catch (JsonSyntaxException e) {
            Logger.error.println("Failed to read <'" + document + "'>");
            throw e;
        }
    }

    public JsonElement retrievePluginAPI() throws IOException {
        return retrievePluginAPI(API_URL);
    }
}
