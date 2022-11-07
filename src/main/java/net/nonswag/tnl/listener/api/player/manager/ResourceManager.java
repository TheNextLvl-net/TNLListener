package net.nonswag.tnl.listener.api.player.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.nonswag.tnl.listener.api.packets.outgoing.ResourcePackPacket;

import javax.annotation.Nullable;

@Getter
public abstract class ResourceManager extends Manager {

    @Nullable
    protected String resourcePackUrl = null;
    @Nullable
    protected String resourcePackHash = null;
    @Nullable
    protected Action action = null;

    public void setResourcePack(String url) {
        ResourcePackPacket.create(url).send(getPlayer());
    }

    public void setResourcePack(String url, @Nullable String hash) {
        ResourcePackPacket.create(url, hash).send(getPlayer());
    }

    public void setResourcePack(String url, @Nullable String hash, @Nullable Component prompt) {
        ResourcePackPacket.create(url, hash, prompt).send(getPlayer());
    }

    public void setResourcePack(String url, @Nullable String hash, @Nullable Component prompt, boolean required) {
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
