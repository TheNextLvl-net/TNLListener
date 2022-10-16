package net.nonswag.tnl.listener.api.player;

import com.google.gson.JsonObject;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.core.api.logger.Logger;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

public class Skin implements Cloneable {

    @Nonnull
    private String value;
    @Nonnull
    private String signature;

    public Skin(@Nonnull String value, @Nonnull String signature) {
        this.signature = signature;
        this.value = value;
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    @Nonnull
    public String getSignature() {
        return signature;
    }

    @Nonnull
    public Skin setValue(@Nonnull String value) {
        this.value = value;
        return this;
    }

    @Nonnull
    public Skin setSignature(@Nonnull String signature) {
        this.signature = signature;
        return this;
    }

    @Nonnull
    public static Skin getSkin(@Nonnull String player) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = JsonHelper.parse(reader).getAsJsonObject().get("id").getAsString();
            uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
            return getSkin(UUID.fromString(uuid));
        } catch (Exception e) {
            Logger.error.println("Failed to load skin <'" + player + "'>", e);
        }
        return new Skin("", "");
    }

    @Nonnull
    public static Skin getSkin(@Nonnull UUID player) {
        try {
            URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + player + "?unsigned=false");
            InputStreamReader reader1 = new InputStreamReader(url1.openStream());
            JsonObject property = JsonHelper.parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            return new Skin(property.get("value").getAsString(), property.get("signature").getAsString());
        } catch (Exception e) {
            Logger.error.println("Failed to load skin <'" + player + "'>", e);
        }
        return new Skin("", "");
    }

    @Override
    public String toString() {
        return "Skin{" +
                "value='" + value + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skin skin = (Skin) o;
        return value.equals(skin.value) && signature.equals(skin.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, signature);
    }

    @Nonnull
    @Override
    public Skin clone() {
        return new Skin(getValue(), getSignature());
    }
}
