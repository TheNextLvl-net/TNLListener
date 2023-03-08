package net.nonswag.tnl.listener.api.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.nonswag.core.api.file.helper.JsonHelper;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.listener.api.settings.Settings;
import net.nonswag.tnl.listener.api.version.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    @Getter
    private static final HashMap<String, Server> servers = new HashMap<>();

    private final ServerInfo serverInfo;
    private final String name;
    private final InetSocketAddress inetSocketAddress;

    private Status status = Status.OFFLINE;
    private int playerCount = 0;
    private int maxPlayerCount = 0;

    private Server(ServerInfo serverInfo) {
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

    public void update() {
        update(update -> {
        });
    }

    public void update(Consumer<Server> update) {
        new Thread(() -> {
            try (Socket socket = new Socket()) {
                socket.setSoTimeout(Settings.SERVER_UPDATE_TIMEOUT.getValue());
                socket.connect(getInetSocketAddress(), Settings.SERVER_UPDATE_TIMEOUT.getValue());
                try {
                    JsonObject object = sendHandshake(socket).getAsJsonObject();
                    JsonObject players = object.getAsJsonObject("players");
                    setMaxPlayerCount(players.get("max").getAsInt());
                    setPlayerCount(players.get("online").getAsInt());
                    setStatus(Status.ONLINE);
                } catch (Exception e) {
                    logger.debug("Server has sent an invalid response", e);
                    setStatus(Status.STARTING).setPlayerCount(0).setMaxPlayerCount(0);
                }
            } catch (Exception e) {
                setStatus(Status.OFFLINE).setPlayerCount(0).setMaxPlayerCount(0);
            } finally {
                update.accept(this);
            }
        }).start();
    }

    private JsonElement sendHandshake(Socket socket) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        DataInputStream input = new DataInputStream(socket.getInputStream());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(buffer);
        handshake.writeByte(0);
        Serializer.writeVarInt(handshake, Version.latest().getProtocol());
        Serializer.writeString(handshake, this.getInetSocketAddress().getHostName());
        handshake.writeShort(this.getInetSocketAddress().getPort());
        Serializer.writeVarInt(handshake, 1);
        byte[] handshakeMessage = buffer.toByteArray();
        Serializer.writeVarInt(output, handshakeMessage.length);
        output.write(handshakeMessage);
        output.writeByte(1);
        output.writeByte(0);
        byte[] in = new byte[Serializer.readVarInt(input)];
        input.readFully(in);
        String json = new String(in, StandardCharsets.UTF_8);
        for (int i = 0; i < 4; i++) if (json.substring(i).startsWith("{")) return JsonHelper.parse(json.substring(i));
        throw new IllegalStateException(json);
    }

    private static class Serializer {

        public static void writeString(DataOutputStream outputStream, String value) throws IOException {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            writeVarInt(outputStream, bytes.length);
            outputStream.write(bytes);
        }

        public static void writeVarInt(DataOutputStream outputStream, int value) throws IOException {
            while ((value & -128) != 0) {
                outputStream.writeByte(value & 127 | 128);
                value >>>= 7;
            }
            outputStream.writeByte(value);
        }

        public static int readVarInt(DataInputStream inputStream) throws IOException {
            int i = 0;
            int j = 0;
            byte k;
            do {
                k = inputStream.readByte();
                i |= (k & 127) << j++ * 7;
            } while ((k & 128) == 128);
            return i;
        }
    }

    public static Server wrap(ServerInfo serverInfo) {
        if (!getServers().containsKey(serverInfo.name())) {
            getServers().put(serverInfo.name(), new Server(serverInfo));
        }
        return getServers().get(serverInfo.name());
    }

    @Nullable
    public static Server wrap(String server) {
        return getServers().get(server);
    }

    public static void purgeCache(ServerInfo server) {
        purgeCache(server.name());
    }

    public static void purgeCache(String server) {
        getServers().remove(server);
    }

    public static List<Server> servers() {
        return new ArrayList<>(getServers().values());
    }

    public static boolean isReachable(ServerInfo serverInfo) {
        return isReachable(serverInfo.address());
    }

    public static boolean isReachable(InetSocketAddress address) {
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

    @Getter
    public enum Status {
        ONLINE("Online"),
        OFFLINE("Offline"),
        STARTING("Starting");

        private final String name;

        Status(String name) {
            this.name = name;
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
