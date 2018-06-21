package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.networking.networklisteners.FactionsHTTPResponseListener;

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

        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new FactionsHTTPResponseListener(spaceGame));
    }
}
