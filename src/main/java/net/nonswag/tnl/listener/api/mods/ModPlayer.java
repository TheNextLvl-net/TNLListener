package net.nonswag.tnl.listener.api.mods;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.data.Buffer;
import net.nonswag.tnl.listener.api.player.manager.Manager;

import javax.annotation.Nonnull;

@Getter
@Setter
public abstract class ModPlayer extends Manager {

    private boolean modUser = false;

    public abstract void sendMessage(@Nonnull String key, @Nonnull JsonElement message);

    public void sendMessage(@Nonnull ModMessage message) {
        if (!isModUser()) return;
        getPlayer().messenger().sendPluginMessage(message.channel(), Buffer.toByteArray(message.key(), message.message().toString()));
    }

    public void handleMessage(@Nonnull ModMessage message) {
    }
}
