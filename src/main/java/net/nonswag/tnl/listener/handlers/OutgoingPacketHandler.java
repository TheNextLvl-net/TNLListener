package net.nonswag.tnl.listener.handlers;

import net.nonswag.tnl.listener.api.event.EventManager;
import net.nonswag.tnl.listener.api.packets.outgoing.AddEntityPacket;
import net.nonswag.tnl.listener.api.packets.outgoing.ResourcePackPacket;
import net.nonswag.tnl.listener.api.settings.Settings;
import org.bukkit.entity.EntityType;

class OutgoingPacketHandler {

    static void init(EventManager manager) {
        manager.registerPacketWriter(ResourcePackPacket.class, (player, packet, cancelled) -> {
            player.resourceManager().setResourcePackUrl(packet.getUrl());
            player.resourceManager().setResourcePackHash(packet.getHash());
        });
        if (Settings.BETTER_FALLING_BLOCKS.getValue()) betterFallingBlocks(manager);
        if (Settings.BETTER_TNT.getValue()) betterTNT(manager);
    }

    private static void betterFallingBlocks(EventManager manager) {
        manager.registerPacketWriter(AddEntityPacket.class, (player, packet, cancelled) -> {
            if (packet.getEntityType().equals(EntityType.FALLING_BLOCK)) cancelled.set(true);
        });
    }

    private static void betterTNT(EventManager manager) {
        manager.registerPacketWriter(AddEntityPacket.class, (player, packet, cancelled) -> {
            if (packet.getEntityType().equals(EntityType.PRIMED_TNT)) cancelled.set(true);
        });
    }
}
