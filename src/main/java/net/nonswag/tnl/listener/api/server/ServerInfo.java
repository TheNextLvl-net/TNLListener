package net.nonswag.tnl.listener.api.server;

import java.net.InetSocketAddress;

public record ServerInfo(String name, InetSocketAddress address) {
}
