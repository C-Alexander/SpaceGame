package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.space.GameUI;
import works.maatwerk.space.networking.networklisteners.GetShipResponseListener;

import java.util.HashMap;

public class GetShipByIdRunnable implements Runnable {
    private GameUI gameUI;
    private String shipId;

    public GetShipByIdRunnable(GameUI gameUI, String shipId) {
        this.gameUI = gameUI;
        this.shipId = shipId;
    }

    @Override
    public void run() {
        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.newRequest();
        httpRequestBuilder.url("http://localhost:3000/ships/" + shipId);
        httpRequestBuilder.method(Net.HttpMethods.GET);
        httpRequestBuilder.timeout(5000);
        HashMap<String, String> contentMap = new HashMap<>();
        httpRequestBuilder.formEncodedContent(contentMap);
        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new GetShipResponseListener(gameUI));
    }

}
