package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.HttpRequestBuilder;

import java.util.HashMap;

class LoginRunnable implements Runnable {
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
        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 200) {
                    spaceGame.getAccountManager().setSessionFromResponse(httpResponse);
                    Gdx.app.postRunnable(() -> {
                        identificationScreen.getLoginWindow().fadeOut(0.3f);
                        spaceGame.startGame();
                    });
            } else  {
                    identificationScreen.getLoginWindow().errorLabel.setText(httpResponse.getResultAsString());
                    identificationScreen.getLoginWindow().errorLabel.setColor(Color.RED);
//                    spaceGame.stage.addActor(spaceGame.loginWindow.fadeIn(0.3f));
                }
                System.out.println(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("Failed");
                System.out.println(t.getMessage());
                identificationScreen.getStage().addActor(identificationScreen.getLoginWindow().fadeIn(0.3f));
            }

            @Override
            public void cancelled() {
                System.out.println("Cancelled");
                identificationScreen.getStage().addActor(identificationScreen.getLoginWindow().fadeIn(0.3f));
            }
        });
    }
}
