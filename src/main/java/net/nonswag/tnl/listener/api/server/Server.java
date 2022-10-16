package net.nonswag.tnl.listener.api.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.core.api.logger.Logger;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.Listener;
import net.nonswag.tnl.listener.api.data.Buffer;
import net.nonswag.tnl.listener.api.settings.Settings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Server {

    @Nonnull
    private static final HashMap<String, Server> servers = new HashMap<>();

    @Nonnull
    private final ServerInfo serverInfo;
    @Nonnull
    private final String name;
    @Nonnull
    private final InetSocketAddress inetSocketAddress;

    @Nonnull
    private Status status = Status.OFFLINE;
    private int playerCount = 0;
    private int maxPlayerCount = 0;

    private Server(@Nonnull ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        this.name = serverInfo.name();
        this.inetSocketAddress = serverInfo.address();
        update(Server::registerPlaceholders);
    }

    public void registerPlaceholders() {
        Placeholder.Registry.register(new Placeholder("online_" + getName(), this::getPlayerCount));
        Placeholder.Registry.register(new Placeholder("max_online_" + getName(), this::getMaxPlayerCount));
        Placeholder.Registry.register(new Placeholder("status_" + getName(), () -> getStatus().getName()));
    }

    @Nonnull
    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    @Nonnull
    public Server setStatus(@Nonnull Status status) {
        this.status = status;
        return this;
    }

    @Nonnull
    private Server setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        return this;
    }

    @Nonnull
    private Server setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
        return this;
    }

    @Nonnull
    public Status getStatus() {
        return status;
    }

    public void update() {
        update(update -> {
        });
    }

    public void update(@Nonnull Consumer<Server> update) {
        new Thread(() -> {
            try {
                Socket socket = new Socket();
                socket.setSoTimeout(Settings.SERVER_UPDATE_TIMEOUT.getValue());
                socket.connect(getInetSocketAddress(), Settings.SERVER_UPDATE_TIMEOUT.getValue());
                try {
                    JsonElement jsonElement = sendHandshake(socket);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has("players") && jsonObject.get("players").isJsonObject()) {
                            JsonObject players = jsonObject.get("players").getAsJsonObject();
                            if (players.has("max") && players.has("online")) {
                                setMaxPlayerCount(players.get("max").getAsInt());
                                setPlayerCount(players.get("online").getAsInt());
                                update.accept(setStatus(Status.ONLINE));
                                return;
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                socket.close();
                setStatus(Status.STARTING);
                setPlayerCount(0);
                setMaxPlayerCount(0);
            } catch (Exception ignored) {
                setStatus(Status.OFFLINE);
                setPlayerCount(0);
                setMaxPlayerCount(0);
            } finally {
                update.accept(this);
            }
        }).start();
    }

    @Nonnull
    private JsonElement sendHandshake(@Nonnull Socket socket) throws IOException {
        if (socket.isConnected()) {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            DataOutputStream handshake = new DataOutputStream(buffer);
            handshake.writeByte(0x00);
            Buffer.writeVarInt(handshake, Listener.getVersion().getProtocol());
            Buffer.writeString(handshake, getInetSocketAddress().getHostName());
            handshake.writeShort(getInetSocketAddress().getPort());
            Buffer.writeVarInt(handshake, 1);
            byte[] handshakeMessage = buffer.toByteArray();
            Buffer.writeVarInt(output, handshakeMessage.length);
            output.write(handshakeMessage);
            output.writeByte(0x01);
            output.writeByte(0x00);
            byte[] in = new byte[Buffer.readVarInt(input)];
            input.readFully(in);
            String s = new String(in, StandardCharsets.UTF_8);
            for (int i = 0; i < 4; i++) {
                if (s.substring(i).startsWith("{")) return JsonHelper.parse(s.substring(i));
            }
            Logger.error.println("Server <'" + getName() + "'> has sent a invalid handshake", s);
        }
        return new JsonObject();
    }

    @Nonnull
    public static Server wrap(@Nonnull ServerInfo serverInfo) {
        if (!getServers().containsKey(serverInfo.name())) {
            getServers().put(serverInfo.name(), new Server(serverInfo));
        }
        return getServers().get(serverInfo.name());
    }

    @Nullable
    public static Server wrap(@Nonnull String server) {
        return getServers().get(server);
    }

    public static void purgeCache(@Nonnull ServerInfo server) {
        purgeCache(server.name());
    }

    public static void purgeCache(@Nonnull String server) {
        getServers().remove(server);
    }

    @Nonnull
    private static HashMap<String, Server> getServers() {
        return servers;
    }

    @Nonnull
    public static List<Server> servers() {
        return new ArrayList<>(getServers().values());
    }

    public static boolean isReachable(@Nonnull ServerInfo serverInfo) {
        return isReachable(serverInfo.address());
    }

    public static boolean isReachable(@Nonnull InetSocketAddress address) {
        try {
            Socket socket = new Socket();
            socket.setSoTimeout(1000);
            socket.connect(address, 1000);
            socket.close();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Server{" +
                "serverInfo=" + serverInfo +
                ", name='" + name + '\'' +
                ", inetSocketAddress=" + inetSocketAddress +
                ", status=" + status +
                ", playerCount=" + playerCount +
                ", maxPlayerCount=" + maxPlayerCount +
                '}';
    }

    public enum Status {
        ONLINE("Online"),
        OFFLINE("Offline"),
        STARTING("Starting");

        @Nonnull
        private final String name;

        Status(@Nonnull String name) {
            this.name = name;
        }

        @Nonnull
        public String getName() {
            return name;
        }

        public boolean isStarting() {
            return equals(STARTING);
        }

        public boolean isOnline() {
            return equals(ONLINE);
        }

        public boolean isOffline() {
            return equals(OFFLINE);
        }
    }
}
