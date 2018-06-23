package works.maatwerk.space.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import works.maatwerk.space.PlayingScreen;
import works.maatwerk.space.networking.networklisteners.LocationSocketListener;

public class NetworkManager {
    private final Json json;
    private WebSocket ws;

    public NetworkManager() {
        json = new Json(JsonWriter.OutputType.json);
    }

    public void connect(PlayingScreen playingScreen) {
        connect(new LocationSocketListener(playingScreen));
    }

    public void connect(WebSocketListener webSocketListener) {
        ws = WebSockets.newSocket("ws://localhost:9000/game");
        ws.setSerializeAsString(true);
        ws.addListener(webSocketListener);

        new Thread(() -> ws.connect()).start();
    }

    public void sendPacket(Object packet) {
        String packetText = json.toJson(packet);
        sendPacket(packetText);
    }

    private void sendPacket(String packet) {
        Gdx.app.debug("Websockets", "Sending packet from listener: " + packet);
        ws.send(packet);
    }

    public WebSocket getWs() {
        return ws;
    }
}
