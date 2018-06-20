package works.maatwerk.space.networking.runnables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.HttpRequestBuilder;
import works.maatwerk.space.IdentificationScreen;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.models.User;

import java.util.HashMap;

public class RegisterRunnable implements Runnable {
    private final SpaceGame spaceGame;
    private final IdentificationScreen identificationScreen;
    private final User user;

    public RegisterRunnable(SpaceGame spaceGame, IdentificationScreen identificationScreen, User user) {
        this.spaceGame = spaceGame;
        this.identificationScreen = identificationScreen;
        this.user = user;
    }

    @Override
    public void run() {
        HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
        httpRequestBuilder.newRequest();
        httpRequestBuilder.url("http://localhost:3000/register");
        httpRequestBuilder.method(Net.HttpMethods.POST);
        httpRequestBuilder.timeout(5000);
        HashMap<String, String> contentMap = new HashMap<>();
        contentMap.put("username", user.getUsername());
        contentMap.put("password", user.getPassword());
        httpRequestBuilder.formEncodedContent(contentMap);
        Gdx.net.sendHttpRequest(httpRequestBuilder.build(), new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if (httpResponse.getStatus().getStatusCode() == 201) {
                    spaceGame.getAccountManager().setSessionFromResponse(httpResponse);
                    Gdx.app.postRunnable(() -> {
                        identificationScreen.getRegisterWindow().fadeOut(0.3f);
                        spaceGame.startGame();
                    });

                } else  {
                    identificationScreen.getRegisterWindow().errorLabel.setText(httpResponse.getResultAsString());
                    identificationScreen.getRegisterWindow().errorLabel.setColor(Color.RED);
//                    spaceGame.stage.addActor(spaceGame.registerWindow.fadeIn(0.3f));
                }
                System.out.println(httpResponse.getResultAsString());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t.getMessage());
                identificationScreen.getStage().addActor(identificationScreen.getRegisterWindow().fadeIn(0.3f));
            }

            @Override
            public void cancelled() {
                identificationScreen.getStage().addActor(identificationScreen.getRegisterWindow().fadeIn(0.3f));
            }
        });
    }
    }
