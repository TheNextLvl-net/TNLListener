package net.nonswag.tnl.listener.api.server;

import javax.annotation.Nonnull;
import java.net.InetSocketAddress;

public record ServerInfo(@Nonnull String name, @Nonnull InetSocketAddress address) {
}
