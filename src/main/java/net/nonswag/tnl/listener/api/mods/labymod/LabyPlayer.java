package net.nonswag.tnl.listener.api.mods.labymod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.math.Range;
import net.nonswag.tnl.listener.api.mods.ModMessage;
import net.nonswag.tnl.listener.api.mods.ModPlayer;
import net.nonswag.tnl.listener.api.mods.labymod.data.*;
import net.nonswag.tnl.listener.api.mods.labymod.data.answer.Answer;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import net.nonswag.tnl.listener.api.player.npc.FakePlayer;
import net.nonswag.tnl.listener.events.mods.labymod.LabyPlayerJoinEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class LabyPlayer extends ModPlayer {

    @Nonnull
    public final Discord discord = new Discord();
    @Nonnull
    public final Cinematic cinematic = new Cinematic();
    @Nonnull
    public final Subtitle subtitle = new Subtitle();
    @Nonnull
    public final Client client = new Client();
    @Nonnull
    public final Data data = new Data();
    @Nonnull
    public final Tablist tablist = new Tablist();
    @Nonnull
    public final VoiceChat voicechat = new VoiceChat();
    @Nonnull
    public final Server server = new Server();
    @Nonnull
    public final Balance balance = new Balance();
    @Nonnull
    public final Emote emote = new Emote();
    @Nonnull
    public final Sticker sticker = new Sticker();
    @Nonnull
    public final Addons addons = new Addons();
    @Nonnull
    public final Watermark watermark = new Watermark();
    @Nonnull
    public final ActionMenu actionmenu = new ActionMenu();
    @Nonnull
    public final Permissions permissions = new Permissions();

    public static class Data {

        @Nonnull
        private final HashMap<String, Answer.ServerConnectRequest> serverConnectRequests = new HashMap<>();
        @Nullable
        private Answer.AddonRecommendation addonRecommendation = null;
        @Nullable
        private Answer.Cinematic cinematic = null;

        private Data() {
        }

        @Nonnull
        public HashMap<String, Answer.ServerConnectRequest> getServerConnectRequests() {
            return serverConnectRequests;
        }

        @Nullable
        public Answer.AddonRecommendation getAddonRecommendation() {
            return addonRecommendation;
        }

        public void setAddonRecommendation(@Nullable Answer.AddonRecommendation addonRecommendation) {
            this.addonRecommendation = addonRecommendation;
        }

        @Nullable
        public Answer.Cinematic getCinematic() {
            return cinematic;
        }

        public void setCinematic(@Nullable Answer.Cinematic cinematic) {
            this.cinematic = cinematic;
        }
    }

    public class Client {

        @Nonnull
        private String version = "unknown";
        @Nonnull
        private final List<Addon> addons = new ArrayList<>();
        private boolean shadow = false;
        private int shadowVersion = -1;

        private Client() {
        }

        @Nonnull
        public String getVersion() {
            return version;
        }

        public void setVersion(@Nonnull String version) {
            this.version = version;
        }

        @Nonnull
        public List<Addon> getAddons() {
            return addons;
        }

        public boolean isShadow() {
            return shadow;
        }

        public void setShadow(boolean shadow) {
            this.shadow = shadow;
        }

        public int getShadowVersion() {
            return shadowVersion;
        }

        public void setShadowVersion(int shadowVersion) {
            this.shadowVersion = shadowVersion;
        }

        public void setPermissions(@Nonnull Permissions permissions) {
            sendMessage("PERMISSIONS", permissions.getMessage());
        }

        public void sendGamemodeToast(@Nonnull String message) {
            JsonObject object = new JsonObject();
            object.addProperty("show_gamemode", true);
            object.addProperty("gamemode_name", message);
            sendMessage("server_gamemode", object);
        }
    }

    public class VoiceChat {

        private VoiceChat() {
        }

        public void disable() {
            JsonObject object = new JsonObject();
            object.addProperty("allowed", false);
            sendMessage("voicechat", object);
        }

        public void setSettings(boolean keepSettings, boolean required) {
            JsonObject voicechatObject = new JsonObject();
            voicechatObject.addProperty("keep_settings_on_server_switch", keepSettings);
            JsonObject requestSettingsObject = new JsonObject();
            requestSettingsObject.addProperty("required", required);
            JsonObject settingsObject = new JsonObject();
            settingsObject.addProperty("enabled", true);
            settingsObject.addProperty("microphoneVolume", 10);
            settingsObject.addProperty("surroundRange", 10);
            settingsObject.addProperty("surroundVolume", 10);
            settingsObject.addProperty("continuousTransmission", false);
            requestSettingsObject.add("settings", settingsObject);
            voicechatObject.add("request_settings", requestSettingsObject);
            sendMessage("voicechat", voicechatObject);
        }

        public void mute(@Nonnull UUID player) {
            setMute(player, true);
        }

        public void unmute(@Nonnull UUID player) {
            setMute(player, false);
        }

        public void setMute(@Nonnull UUID player, boolean muted) {
            JsonObject voicechatObject = new JsonObject();
            JsonObject mutePlayerObject = new JsonObject();
            mutePlayerObject.addProperty("mute", muted);
            mutePlayerObject.addProperty("target", player.toString());
            voicechatObject.add("mute_player", mutePlayerObject);
            sendMessage("voicechat", voicechatObject);
        }
    }

    public class Cinematic {

        private Cinematic() {
        }

        public void setCineScope(@Range(from = 0, to = 50) int coveragePercent, long millis) {
            JsonObject object = new JsonObject();
            object.addProperty("coverage", coveragePercent);
            object.addProperty("duration", millis);
            sendMessage("cinescopes", object);
        }

        public void play(@Nonnull List<Point> points, long millis) {
            play(points, millis, null);
        }

        public void play(@Nonnull List<Point> points, long millis, @Nullable Answer.Cinematic answer) {
            if (answer != null) data.setCinematic(answer);
            JsonObject cinematic = new JsonObject();
            JsonArray array = new JsonArray();
            for (Point point : points) {
                JsonObject object = new JsonObject();
                object.addProperty("x", point.x());
                object.addProperty("y", point.y());
                object.addProperty("z", point.z());
                object.addProperty("yaw", point.yaw());
                object.addProperty("pitch", point.pitch());
                object.addProperty("tilt", point.tilt());
                array.add(object);
            }
            cinematic.add("points", array);
            cinematic.addProperty("clear_chat", true);
            cinematic.addProperty("duration", millis);
            sendMessage("cinematic", cinematic);
        }

        public void cancel() {
            sendMessage("cinematic", new JsonObject());
        }
    }

    public class Subtitle {

        private Subtitle() {
        }

        public void set(@Nonnull TNLPlayer viewer, @Nullable String value) {
            set(viewer.getUniqueId(), value, 0.8);
        }

        public void set(@Nonnull TNLPlayer viewer, @Nullable String value, double size) {
            set(viewer.getUniqueId(), value, size);
        }

        public void set(@Nonnull UUID viewer, @Nullable String value) {
            set(viewer, value, 0.8);
        }

        public void set(@Nonnull UUID viewer, @Nullable String value, double size) {
            JsonArray array = new JsonArray();
            JsonObject subtitle = new JsonObject();
            subtitle.addProperty("uuid", viewer.toString());
            subtitle.addProperty("size", size);
            if (value != null && !value.isEmpty()) subtitle.addProperty("value", value);
            array.add(subtitle);
            sendMessage("account_subtitle", array);
        }
    }

    public class Discord {

        private Discord() {
        }

        public void hideParty() {
            JsonObject partyObject = new JsonObject();
            partyObject.addProperty("hasParty", false);
            sendMessage("discord_rpc", partyObject);
        }

        public void setParty(@Nonnull String id, int members, int maximalMembers) {
            JsonObject partyObject = new JsonObject();
            partyObject.addProperty("hasParty", true);
            partyObject.addProperty("partyId", id);
            partyObject.addProperty("party_size", members);
            partyObject.addProperty("party_max", maximalMembers);
            sendMessage("discord_rpc", partyObject);
        }

        public void hideRichPresence() {
            JsonObject richPresenceObject = new JsonObject();
            richPresenceObject.addProperty("hasGame", false);
            sendMessage("discord_rpc", richPresenceObject);
        }

        public void setRichPresenceTimer(@Nonnull String gamemode, long startTime) {
            setRichPresence(gamemode, startTime, 0);
        }

        public void setRichPresenceCountdown(@Nonnull String gamemode, long endTime) {
            setRichPresence(gamemode, 0, System.currentTimeMillis());
        }

        public void setRichPresence(@Nonnull String gamemode, long startTime, long endTime) {
            JsonObject richPresenceObject = new JsonObject();
            richPresenceObject.addProperty("hasGame", true);
            richPresenceObject.addProperty("game_mode", gamemode);
            richPresenceObject.addProperty("game_startTime", startTime);
            richPresenceObject.addProperty("game_endTime", endTime);
            sendMessage("discord_rpc", richPresenceObject);
        }
    }

    public class Server {

        private Server() {
        }

        public void requestConnect(@Nonnull String title, @Nonnull String address, @Nonnull Answer.ServerConnectRequest answer) {
            requestConnect(title, address, true, answer);
        }

        public void requestConnect(@Nonnull String title, @Nonnull String address) {
            requestConnect(title, address, true);
        }

        public void requestConnect(@Nonnull String title, @Nonnull String address, boolean preview) {
            requestConnect(title, address, preview, null);
        }

        public void requestConnect(@Nonnull String title, @Nonnull String address, boolean preview, @Nullable Answer.ServerConnectRequest answer) {
            getPlayer().interfaceManager().closeGUI(false);
            if (answer != null) data.getServerConnectRequests().put(address, answer);
            JsonObject object = new JsonObject();
            object.addProperty("title", title);
            object.addProperty("address", address);
            object.addProperty("preview", preview);
            sendMessage("server_switch", object);
        }
    }

    public class Balance {

        @Nonnull
        public final Cash cash = new Cash();
        @Nonnull
        public final Bank bank = new Bank();

        @Getter
        @Setter
        public abstract class Manager {

            @Nonnull
            private final String type;
            @Nonnull
            private String format = "$#,###.#####";
            @Nonnull
            private String icon = "";
            @Setter(AccessLevel.NONE)
            @Range(from = 0, to = 5)
            private int divisor = 2;

            private Manager(@Nonnull String type) {
                this.type = type;
            }

            public void setDivisor(@Range(from = 0, to = 5) int divisor) {
                this.divisor = divisor;
            }

            public void hide() {
                JsonObject economyObject = new JsonObject();
                JsonObject cashObject = new JsonObject();
                cashObject.addProperty("visible", false);
                economyObject.add(getType(), cashObject);
                sendMessage("economy", economyObject);
            }

            public void set(double balance) {
                JsonObject economyObject = new JsonObject();
                JsonObject cashObject = new JsonObject();
                int divisor = switch (this.divisor) {
                    case 1 -> 10;
                    case 2 -> 100;
                    case 3 -> 1000;
                    case 4 -> 10000;
                    case 5 -> 100000;
                    default -> 1;
                };
                cashObject.addProperty("balance", balance * divisor);
                cashObject.addProperty("visible", true);
                if (!icon.isEmpty()) cashObject.addProperty("icon", icon);
                if (!format.isEmpty()) {
                    JsonObject decimalObject = new JsonObject();
                    decimalObject.addProperty("format", format);
                    decimalObject.addProperty("divisor", divisor);
                    cashObject.add("decimal", decimalObject);
                }
                economyObject.add(getType(), cashObject);
                sendMessage("economy", economyObject);
            }
        }

        public class Cash extends Manager {
            private Cash() {
                super("cash");
            }
        }

        public class Bank extends Manager {
            private Bank() {
                super("bank");
            }
        }

        private Balance() {
        }
    }

    public class Tablist {

        private Tablist() {
        }

        public void setServerBanner(@Nonnull File file) {
            try {
                setBanner(file.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                Logger.error.println(e);
            }
        }

        public void setBanner(@Nonnull String imageUrl) {
            JsonObject object = new JsonObject();
            object.addProperty("url", imageUrl);
            sendMessage("server_banner", object);
        }

        public void cache(boolean enabled) {
            JsonObject object = new JsonObject();
            object.addProperty("enabled", enabled);
            sendMessage("tablist_cache", object);
        }

        public void setFlag(@Nonnull TNLPlayer player, @Nonnull Flag flag) {
            setFlag(player.getUniqueId(), flag);
        }

        public void setFlag(@Nonnull UUID player, @Nonnull Flag flag) {
            JsonObject flagPacket = new JsonObject();
            JsonArray users = new JsonArray();
            JsonObject userObject = new JsonObject();
            userObject.addProperty("uuid", player.toString());
            userObject.addProperty("code", flag.code());
            users.add(userObject);
            flagPacket.add("users", users);
            sendMessage("language_flag", flagPacket);
        }
    }

    public class Sticker {

        private Sticker() {
        }

        public void showSticker(@Nonnull TNLPlayer player, @Nonnull StickerType type) {
            showSticker(player.getUniqueId(), type);
        }

        public void showSticker(@Nonnull FakePlayer npc, @Nonnull StickerType type) {
            showSticker(npc.getPlayer().getGameProfile().getUniqueId(), type);
        }

        public void showSticker(@Nonnull UUID player, @Nonnull StickerType type) {
            JsonArray array = new JsonArray();
            JsonObject sendSticker = new JsonObject();
            sendSticker.addProperty("uuid", player.toString());
            sendSticker.addProperty("sticker_id", type.id());
            array.add(sendSticker);
            sendMessage("sticker_api", array);
        }
    }

    public class Emote {

        private Emote() {
        }

        public void playEmote(@Nonnull TNLPlayer player, @Nonnull EmoteType emote) {
            playEmote(player.getUniqueId(), emote);
        }

        public void playEmote(@Nonnull FakePlayer npc, @Nonnull EmoteType emote) {
            playEmote(npc.getPlayer().getGameProfile().getUniqueId(), emote);
        }

        public void playEmote(@Nonnull UUID player, @Nonnull EmoteType emote) {
            JsonArray array = new JsonArray();
            JsonObject forcedEmote = new JsonObject();
            forcedEmote.addProperty("uuid", player.toString());
            forcedEmote.addProperty("emote_id", emote.getId());
            array.add(forcedEmote);
            sendMessage("emote_api", array);
        }
    }

    public class Addons {

        private Addons() {
        }

        public void recommend(@Nonnull Addon... addons) {
            recommend(null, addons);
        }

        public void recommend(@Nullable Answer.AddonRecommendation answer, @Nonnull Addon... addons) {
            getPlayer().interfaceManager().closeGUI(false);
            if (answer != null) data.setAddonRecommendation(answer);
            JsonObject root = new JsonObject();
            JsonArray array = new JsonArray();
            for (Addon addon : addons) {
                JsonObject object = new JsonObject();
                object.addProperty("uuid", addon.getUniqueId());
                object.addProperty("required", addon.isRequired());
                array.add(object);
            }
            root.add("addons", array);
            sendMessage("addon_recommendation", root);
        }
    }

    public class Watermark {

        private Watermark() {
        }

        public void show() {
            setWatermark(true);
        }

        public void hide() {
            setWatermark(false);
        }

        public void setWatermark(boolean visible) {
            JsonObject object = new JsonObject();
            object.addProperty("visible", visible);
            sendMessage("watermark", object);
        }
    }

    public class ActionMenu {

        private ActionMenu() {
        }

        public void reset() {
            sendMessage("user_menu_actions", new JsonArray());
        }

        public void set(@Nonnull Entry... entries) {
            JsonArray array = new JsonArray();
            for (Entry entry : entries) {
                JsonObject object = new JsonObject();
                object.addProperty("displayName", entry.getName());
                object.addProperty("type", entry.getAction().name());
                object.addProperty("value", entry.getValue());
                array.add(object);
            }
            sendMessage("user_menu_actions", array);
        }
    }

    @Override
    public void sendMessage(@Nonnull String key, @Nonnull JsonElement message) {
        sendMessage(new ModMessage("labymod3:main", key, message));
    }

    @Override
    public void handleMessage(@Nonnull ModMessage message) {
        if (!message.channel().equals("main")) return;
        if (message.key().equals("INFO") && !isModUser()) {
            if (!message.message().isJsonObject()) return;
            JsonObject root = message.message().getAsJsonObject();
            if (root.has("version")) client.setVersion(root.get("version").getAsString());
            if (root.has("shadow") && root.get("shadow").isJsonObject()) {
                JsonObject shadow = root.getAsJsonObject("shadow");
                if (shadow.has("enabled")) client.setShadow(shadow.get("enabled").getAsBoolean());
                if (shadow.has("version")) client.setShadowVersion(shadow.get("version").getAsInt());
            }
            if (root.has("addons") && root.get("addons").isJsonArray()) {
                client.getAddons().addAll(getAddons(root.getAsJsonArray("addons")));
            }
            setModUser(true);
            LabyPlayerJoinEvent event = new LabyPlayerJoinEvent(this);
            if (event.call()) return;
            if (event.getDisconnectReason() == null) getPlayer().pipeline().disconnect();
            else getPlayer().pipeline().disconnect(event.getDisconnectReason());
        } else if (message.key().equals("cinematic") && isModUser()) {
            Answer.Cinematic cinematic = data.getCinematic();
            if (cinematic == null) return;
            if (!message.message().isJsonObject()) return;
            JsonObject object = message.message().getAsJsonObject();
            if (!object.has("completed")) return;
            cinematic.send(getPlayer(), object.get("completed").getAsBoolean());
        } else if (message.key().equals("addon_recommendation") && isModUser()) {
            Answer.AddonRecommendation answer = data.getAddonRecommendation();
            if (answer == null) return;
            if (!message.message().isJsonObject()) return;
            JsonObject object = message.message().getAsJsonObject();
            if (!object.has("gui_close")) return;
            if (!object.has("all_installed")) return;
            if (!object.has("missing") || !object.get("missing").isJsonArray()) return;
            boolean guiClosed = object.get("gui_close").getAsBoolean();
            boolean allInstalled = object.get("all_installed").getAsBoolean();
            List<Addon> missing = getAddons(object.getAsJsonArray("missing"));
            boolean b = answer.send(getPlayer(), guiClosed, allInstalled, missing);
            if (b || (missing.isEmpty() && allInstalled)) data.setAddonRecommendation(null);
        } else if (message.key().equals("server_switch") && isModUser()) {
            HashMap<String, Answer.ServerConnectRequest> requests = data.getServerConnectRequests();
            if (!message.message().isJsonObject()) return;
            JsonObject object = message.message().getAsJsonObject();
            if (!object.has("address") || !object.has("accepted")) return;
            String address = object.get("address").getAsString();
            Answer.ServerConnectRequest answer = requests.get(address);
            answer.send(getPlayer(), object.get("accepted").getAsBoolean());
            data.getServerConnectRequests().remove(address);
        }
    }

    @Nullable
    private Addon getAddon(@Nonnull JsonObject object) {
        if (!object.has("uuid") || !object.has("name")) return null;
        String uuid = object.get("uuid").getAsString();
        String name = object.get("name").getAsString();
        Addon addon = Addon.getAddons().get(uuid);
        if (addon == null) addon = new Addon(name, uuid);
        return addon;
    }

    @Nonnull
    private List<Addon> getAddons(@Nonnull JsonArray array) {
        List<Addon> addons = new ArrayList<>();
        for (JsonElement element : array) {
            if (!element.isJsonObject()) continue;
            addons.add(getAddon(element.getAsJsonObject()));
        }
        return addons;
    }
}
