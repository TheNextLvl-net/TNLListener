package net.nonswag.tnl.listener.events;

import net.nonswag.core.api.language.Language;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

import javax.annotation.Nonnull;

public class PlayerLanguageChangeEvent extends PlayerEvent {

    @Nonnull
    private final Language lastLanguage;
    @Nonnull
    private final Language newLanguage;

    public PlayerLanguageChangeEvent(@Nonnull TNLPlayer player, @Nonnull Language lastLanguage) {
        super(player);
        this.lastLanguage = lastLanguage;
        this.newLanguage = player.data().getLanguage();
    }

    @Nonnull
    public Language getLastLanguage() {
        return lastLanguage;
    }

    @Nonnull
    public Language getNewLanguage() {
        return newLanguage;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
