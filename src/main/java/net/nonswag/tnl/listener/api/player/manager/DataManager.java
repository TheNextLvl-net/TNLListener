package net.nonswag.tnl.listener.api.player.manager;

import com.google.gson.JsonObject;
import lombok.Getter;
import net.nonswag.core.api.file.formats.JsonFile;
import net.nonswag.core.api.language.Language;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public abstract class DataManager extends Manager {

    @Nonnull
    private final JsonFile file;
    @Nonnull
    private Language language = Language.AMERICAN_ENGLISH;

    protected DataManager() {
        super();
        this.file = new JsonFile("plugins/Listener/Data", getPlayer().getUniqueId() + ".json");
        JsonObject root = getFile().getJsonElement().getAsJsonObject();
        if (root.has("language")) setLanguage(Language.fromLocale(root.get("language").getAsString()));
    }

    public void export() {
        JsonObject root = getFile().getJsonElement().getAsJsonObject();
        root.addProperty("language", language.name());
        getFile().save();
    }

    public void setLanguage(@Nonnull Language language) {
        if (!language.equals(Language.UNKNOWN)) this.language = language;
    }

    @Nonnull
    public <V> V getOrDefault(@Nonnull String key, @Nonnull V defaultValue, @Nonnull Parser<V> parser) {
        String value = get(key);
        if (value == null) put(key, defaultValue);
        return value != null ? parser.parse(value) : defaultValue;
    }

    @Nonnull
    public String getOrDefault(@Nonnull String key, @Nonnull String defaultValue) {
        String value = get(key);
        if (value == null) put(key, defaultValue);
        return value != null ? value : defaultValue;
    }

    @Nullable
    public <V> V get(@Nonnull String key, @Nonnull Parser<V> parser) {
        String value = get(key);
        return value != null ? parser.parse(value) : null;
    }

    @Nullable
    public String get(@Nonnull String key) {
        JsonObject root = getFile().getJsonElement().getAsJsonObject();
        return root.has(key) ? root.get(key).getAsString() : null;
    }

    public <V> void put(@Nonnull String key, @Nullable V value) {
        JsonObject root = getFile().getJsonElement().getAsJsonObject();
        if (value != null) root.addProperty(key, value.toString());
        else remove(key);
    }

    public void remove(@Nonnull String key) {
        getFile().getJsonElement().getAsJsonObject().remove(key);
    }

    public interface Parser<V> {
        @Nonnull
        V parse(@Nonnull String value);
    }
}
