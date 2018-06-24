package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.space.GameUI;
import works.maatwerk.space.models.Map;

class UpdateMapResponseListener implements Net.HttpResponseListener {
    private GameUI gameUI;

    UpdateMapResponseListener(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() != 200) {
            Gdx.app.error("UpdateMapResponseListener", httpResponse.getResultAsString());
            return;
        }

        try {
            JsonReader jsonReader = new JsonReader();
            JsonValue rawMap = jsonReader.parse(httpResponse.getResultAsString());

            Map map = new Map(rawMap.child);

            Gdx.app.postRunnable(() -> gameUI.replaceSolarSystem(map));
        } catch (Exception e) {
            Gdx.app.error("UpdateMap", e.getMessage(), e);
        }
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error("UpdateMapResponseListener", t.getMessage(), t);
    }

    @Override
    public void cancelled() {
        Gdx.app.error("UpdateMapResponseListener", "Cancelled.");

    }
}
