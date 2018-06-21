package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.space.IdentificationScreen;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.models.User;
import works.maatwerk.space.networking.networklisteners.LoginResponseListener;

import java.util.HashMap;

public class LoginRunnable implements Runnable {
    private final SpaceGame spaceGame;
    private final IdentificationScreen identificationScreen;
    private final User user;

    public LoginRunnable(SpaceGame spaceGame, IdentificationScreen identificationScreen, User user) {
        this.spaceGame = spaceGame;
        this.identificationScreen = identificationScreen;
        this.user = user;
    }

    @Override
    public void run() {
        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.newRequest();
        httpRequestBuilder.url("http://localhost:3000/login");
        httpRequestBuilder.method(Net.HttpMethods.POST);
        httpRequestBuilder.timeout(5000);
        HashMap<String, String> contentMap = new HashMap<>();
        contentMap.put("username", user.getUsername());
        contentMap.put("password", user.getPassword());
        httpRequestBuilder.formEncodedContent(contentMap);
        Gdx.net.sendHttpRequest(httpRequestBuilder.build(),
                new LoginResponseListener(
                        identificationScreen.getLoginWindow(),
                        spaceGame,
                        identificationScreen.getStage()
                ));
    }

}
