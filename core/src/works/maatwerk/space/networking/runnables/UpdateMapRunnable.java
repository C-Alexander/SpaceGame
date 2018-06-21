package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.space.GameUI;

public class UpdateMapRunnable implements Runnable {
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

        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new UpdateMapResponseListener(gameUI));
    }

}
