package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.space.models.Faction;
import works.maatwerk.space.SpaceGame;

import java.util.ArrayList;
import java.util.List;

public class GetFactionsRunnable implements Runnable {
    private SpaceGame spaceGame;

    public GetFactionsRunnable(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
    }

    @Override
    public void run() {
        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.newRequest();
        httpRequestBuilder.url("http://localhost:3000/factions");
        httpRequestBuilder.method(Net.HttpMethods.GET);

        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() != 200) {
                    Gdx.app.error("Networking", "Fatal Error");
                    return;
                }

                try {
                    JsonReader jsonReader = new JsonReader();
                    JsonValue rawMap = jsonReader.parse(httpResponse.getResultAsString());

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

                    Gdx.app.postRunnable(() -> spaceGame.setFactions(factions));
                } catch (Exception e) {
                    Gdx.app.error("UpdateMap", e.getMessage(), e);
                }
            }


            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
