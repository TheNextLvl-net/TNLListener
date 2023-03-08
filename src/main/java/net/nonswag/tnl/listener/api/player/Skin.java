package net.nonswag.tnl.listener.api.player;

import com.google.gson.JsonObject;
import lombok.*;
import net.nonswag.core.api.file.helper.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Skin implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(Skin.class);
    private String value, signature;

    public static Skin getSkin(String player) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = JsonHelper.parse(reader).getAsJsonObject().get("id").getAsString();
            uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
            return getSkin(UUID.fromString(uuid));
        } catch (Exception e) {
            logger.error("Failed to load skin <'" + player + "'>", e);
        }
        return new Skin("", "");
    }

    public static Skin getSkin(UUID player) {
        try {
            URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + player + "?unsigned=false");
            InputStreamReader reader1 = new InputStreamReader(url1.openStream());
            JsonObject property = JsonHelper.parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            return new Skin(property.get("value").getAsString(), property.get("signature").getAsString());
        } catch (Exception e) {
            logger.error("Failed to load skin <'" + player + "'>", e);
        }
        return new Skin("", "");
    }

    @Override
    public Skin clone() {
        return new Skin(getValue(), getSignature());
    }
}
