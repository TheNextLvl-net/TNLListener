package net.nonswag.tnl.listener.events;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.core.api.language.Language;
import net.nonswag.tnl.listener.api.event.PlayerEvent;
import net.nonswag.tnl.listener.api.player.TNLPlayer;

@Getter
@Setter
public class PlayerLanguageChangeEvent extends PlayerEvent {
    private final Language lastLanguage, newLanguage;

    public PlayerLanguageChangeEvent(TNLPlayer player, Language lastLanguage) {
        this(player, lastLanguage, player.data().getLanguage());
    }

    public PlayerLanguageChangeEvent(TNLPlayer player, Language lastLanguage, Language newLanguage) {
        super(player);
        this.lastLanguage = lastLanguage;
        this.newLanguage = newLanguage;
    }
}
