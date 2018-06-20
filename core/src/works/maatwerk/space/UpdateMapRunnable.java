package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

class UpdateMapRunnable implements Runnable {
    private final GameUI gameUI;

    public UpdateMapRunnable(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void run() {
        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.newRequest();
        httpRequestBuilder.url("http://localhost:3000/maps?name=Sol");
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

                    Map map = new Map(rawMap.child.getString("id"), rawMap.child.getString("name"));

                    map.createShipsFromJson(rawMap.child.get("ships"));


                    Gdx.app.postRunnable(() -> gameUI.replaceSolarSystem(map));
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
