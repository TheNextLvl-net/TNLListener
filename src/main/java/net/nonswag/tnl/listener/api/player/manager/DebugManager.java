package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.listener.api.packets.EntityStatusPacket;

import javax.annotation.Nonnull;

public abstract class DebugManager extends Manager {

    public void setBrand(@Nonnull String brand) {
        getPlayer().messenger().sendPluginMessage("minecraft:brand", Message.format(brand));
    }

    public void setReducedDebugInformation(boolean reduced) {
        EntityStatusPacket.create(getPlayer().bukkit(), EntityStatusPacket.Status.reducedDebugInformation(reduced)).send(getPlayer());
    }
}
