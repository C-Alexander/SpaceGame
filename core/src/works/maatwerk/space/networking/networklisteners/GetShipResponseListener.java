package works.maatwerk.space.networking.networklisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import works.maatwerk.space.GameUI;
import works.maatwerk.space.models.Ship;

public class GetShipResponseListener implements Net.HttpResponseListener {

    private GameUI gameUI;

    public GetShipResponseListener(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() == 200) {
            Ship newShip = new Ship(httpResponse);
            Gdx.app.postRunnable(() -> {
                gameUI.addShip(newShip);
            });
        } else {
            Gdx.app.error("GetShipResponseListener", httpResponse.getResultAsString());

        }
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error("GetShipResponseListener", t.getMessage(), t);
    }

    @Override
    public void cancelled() {
        Gdx.app.error("GetShipResponseListener", "Cancelled.");
    }
}
