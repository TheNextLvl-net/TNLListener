package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public class EditBookPacket implements IncomingPacket {
    @Nullable
    private String title;
    @Nonnull
    private List<String> pages;
    private int slot;

    public EditBookPacket(@Nullable String title, @Nonnull List<String> pages, int slot) {
        this.title = title;
        this.pages = pages;
        this.slot = slot;
    }
}
