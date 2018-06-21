package works.maatwerk.space.networking.networklisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.models.Faction;

import java.util.ArrayList;
import java.util.List;

public class FactionsHTTPResponseListener implements Net.HttpResponseListener {

    private SpaceGame spaceGame;

    public FactionsHTTPResponseListener(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() != 200) {
            Gdx.app.error("Networking", "Fatal Error");
            return;
        }

        try {
            Gdx.app.error("Networking", "Set Factions");
            JsonReader jsonReader = new JsonReader();
            JsonValue rawMap = jsonReader.parse(httpResponse.getResultAsString());
            List<Faction> factions = getFactionsFromJson(rawMap);

            Gdx.app.postRunnable(() -> spaceGame.setFactions(factions));
        } catch (Exception e) {
            Gdx.app.error("UpdateMap", e.getMessage(), e);
        }
    }

    private List<Faction> getFactionsFromJson(JsonValue rawMap) {
        List<Faction> factions = new ArrayList<>();
        for (JsonValue jsonValue : rawMap) {
            Faction faction = new Faction(
                    jsonValue.getString("id"),
                    jsonValue.getString("name"),
                    jsonValue.getFloat("tax"),
                    jsonValue.getString("icon"),
                    jsonValue.getString("flag")
            );
            factions.add(faction);
        }
        return factions;
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error("GetFactions", t.getMessage(), t);
    }

    @Override
    public void cancelled() {
        Gdx.app.error("GetFactions", "Failed to get factions");
    }
}
