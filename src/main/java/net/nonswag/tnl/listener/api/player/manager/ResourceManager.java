package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.tnl.listener.api.packets.outgoing.ResourcePackPacket;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNullableByDefault;

@Getter
@Setter
@FieldsAreNullableByDefault
@ParametersAreNullableByDefault
public abstract class ResourceManager extends Manager {
    private String resourcePackUrl, resourcePackHash;
    private Action action;

    public void sendResourcePack(@Nonnull String url) {
        ResourcePackPacket.create(url).send(getPlayer());
    }

    public void sendResourcePack(@Nonnull String url, String hash) {
        ResourcePackPacket.create(url, hash).send(getPlayer());
    }

    public void sendResourcePack(@Nonnull String url, String hash, Component prompt) {
        ResourcePackPacket.create(url, hash, prompt).send(getPlayer());
    }

    public void sendResourcePack(@Nonnull String url, String hash, Component prompt, boolean required) {
        ResourcePackPacket.create(url, hash, prompt, required).send(getPlayer());
    }

    public boolean hasResourcePack() {
        return getAction() != null && getAction().isLoaded();
    }

    public enum Action {
        SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED;

        public boolean isLoaded() {
            return equals(SUCCESSFULLY_LOADED);
        }

        public boolean hasDeclined() {
            return equals(DECLINED);
        }

        public boolean isFailed() {
            return equals(FAILED_DOWNLOAD);
        }

        public boolean hasAccepted() {
            return equals(ACCEPTED);
        }
    }
}
