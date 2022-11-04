package net.nonswag.tnl.listener.api.sign;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.annotation.FieldsAreNonnullByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.tnl.listener.api.player.TNLPlayer;
import org.bukkit.Location;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@Setter
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SignMenu {

    @Nullable
    private Response response = null;
    @Nullable
    private Location location = null;
    private final String[] lines;
    private Type type = Type.DARK_OAK_WALL_SIGN;
    private boolean reopenOnFail = false;

    public SignMenu(String... lines) {
        for (int i = 0; i < lines.length; i++) if (lines[i] == null) lines[i] = "";
        this.lines = lines;
    }

    public SignMenu setType(Type type) {
        this.type = type;
        return this;
    }

    public SignMenu response(Response response) {
        this.response = response;
        return this;
    }

    public SignMenu setReopenOnFail(boolean reopenOnFail) {
        this.reopenOnFail = reopenOnFail;
        return this;
    }

    public SignMenu reopenOnFail() {
        return setReopenOnFail(true);
    }

    public enum Type {
        SPRUCE_WALL_SIGN,
        DARK_OAK_WALL_SIGN,
        BIRCH_WALL_SIGN,
        ACACIA_WALL_SIGN,
        JUNGLE_WALL_SIGN,
        OAK_WALL_SIGN,
        WARPED_WALL_SIGN,
        CRIMSON_WALL_SIGN,
        SPRUCE_SIGN,
        DARK_OAK_SIGN,
        BIRCH_SIGN,
        ACACIA_SIGN,
        JUNGLE_SIGN,
        OAK_SIGN,
        WARPED_SIGN,
        CRIMSON_SIGN
    }

    public interface Response {
        boolean test(TNLPlayer player, String[] lines);
    }
}
