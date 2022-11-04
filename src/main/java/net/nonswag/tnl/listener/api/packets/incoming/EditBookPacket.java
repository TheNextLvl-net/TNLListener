package net.nonswag.tnl.listener.api.packets.incoming;

import lombok.Getter;
import lombok.Setter;
import net.nonswag.tnl.listener.api.mapper.Mapping;

import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public abstract class EditBookPacket extends PacketBuilder {
    @Nullable
    private String title;
    private List<String> pages;
    private int slot;

    protected EditBookPacket(@Nullable String title, List<String> pages, int slot) {
        this.title = title;
        this.pages = pages;
        this.slot = slot;
    }

    public static EditBookPacket create(@Nullable String title, List<String> pages, int slot) {
        return Mapping.get().packetManager().incoming().editBookPacket(title, pages, slot);
    }
}
