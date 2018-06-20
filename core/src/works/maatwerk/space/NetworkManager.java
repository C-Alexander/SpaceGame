package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSockets;

class NetworkManager {
    private final PlayingScreen playingScreen;
    private final SpaceGame spaceGame;
    private final Json json;
    private WebSocket ws;

    public NetworkManager(PlayingScreen playingScreen, SpaceGame spaceGame) {
        this.playingScreen = playingScreen;
        this.spaceGame = spaceGame;
        json = new Json(JsonWriter.OutputType.json);
    }

    public void connect() {
        ws = WebSockets.newSocket("ws://localhost:9000/game");
        ws.setSerializeAsString(true);
        ws.addListener(new LocationSocketListener(this, spaceGame, playingScreen));
        ws.connect();
    }

    public void sendPacket(Object packet) {
        String packetText = json.toJson(packet);
        sendPacket(packetText);
    }

    private void sendPacket(String packet) {
        Gdx.app.debug("Websockets", "Sending packet from listener: " + packet);
        ws.send(packet);
    }

}
