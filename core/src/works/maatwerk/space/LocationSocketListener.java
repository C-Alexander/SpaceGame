package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketException;

class LocationSocketListener implements WebSocketListener {


    private final JsonReader jsonReader;
    private final Json json;
    private final PlayingScreen playingScreen;

    public LocationSocketListener(NetworkManager networkManager, SpaceGame spaceGame, PlayingScreen playingScreen) {
        NetworkManager networkManager1 = networkManager;
        SpaceGame spaceGame1 = spaceGame;
        this.playingScreen = playingScreen;
        jsonReader = new JsonReader();
        json = new Json();
    }

    @Override
    public boolean onClose(WebSocket webSocket, WebSocketCloseCode code, String reason) {
        return false;
    }

    @Override
    public boolean onOpen(WebSocket webSocket) {
        return true;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, byte[] packet) {
        return false;
    }

    @Override
    public boolean onError(WebSocket webSocket, Throwable error) {
        return false;
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) throws WebSocketException {
        System.out.println(packet);

        if (packet.equalsIgnoreCase("update")) {
            playingScreen.moveAllShips();
            return false;
        }
        try {
            JsonValue parse = jsonReader.parse(jsonReader.parse(packet).toJson(JsonWriter.OutputType.json));
            if (parse.has("destination")) {
                playingScreen.updateDestination(parse.getString("id"), json.fromJson(Vector2.class, parse.get("destination").toJson(JsonWriter.OutputType.json)));
            }
        } catch (Exception e) {
            Gdx.app.error("Websocket", e.getMessage(), e);
        }
        return true;
    }
}
