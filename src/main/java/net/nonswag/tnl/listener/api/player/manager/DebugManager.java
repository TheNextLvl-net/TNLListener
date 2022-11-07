package net.nonswag.tnl.listener.api.player.manager;

import net.nonswag.core.api.message.Message;
import net.nonswag.tnl.listener.api.packets.outgoing.EntityStatusPacket;
import org.bukkit.NamespacedKey;

public abstract class DebugManager extends Manager {

    public void setBrand(String brand) {
        getPlayer().messenger().sendPluginMessage(NamespacedKey.minecraft("brand"), Message.format(brand));
    }

    public void setReducedDebugInformation(boolean reduced) {
        EntityStatusPacket.create(getPlayer().bukkit(), EntityStatusPacket.Status.reducedDebugInformation(reduced)).send(getPlayer());
    }
}
