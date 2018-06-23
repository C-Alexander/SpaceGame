package works.maatwerk.space.networking.networklisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.data.WebSocketException;
import works.maatwerk.space.PlayingScreen;

public class LocationSocketListener extends WebSocketAdapter {


    private final JsonReader jsonReader;
    private final Json json;
    private final PlayingScreen playingScreen;

    public LocationSocketListener(PlayingScreen playingScreen) {
        this.playingScreen = playingScreen;
        jsonReader = new JsonReader();
        json = new Json();
    }

    @Override
    public boolean onMessage(WebSocket webSocket, String packet) throws WebSocketException {

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
