package works.maatwerk.space.networking.networklisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.windows.RegisterWindow;

public class RegisterResponseListener implements Net.HttpResponseListener {
    private RegisterWindow registerWindow;
    private SpaceGame spaceGame;
    private Stage windowStage;

    public RegisterResponseListener(RegisterWindow registerWindow, SpaceGame spaceGame, Stage windowStage) {
        this.registerWindow = registerWindow;
        this.spaceGame = spaceGame;
        this.windowStage = windowStage;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() == 201) {
            spaceGame.getAccountManager().setSessionFromResponse(httpResponse);
            Gdx.app.postRunnable(() -> {
                registerWindow.fadeOut(0.3f);
                spaceGame.startGame();
            });

        } else {
            registerWindow.errorLabel.setText(httpResponse.getResultAsString());
            registerWindow.errorLabel.setColor(Color.RED);
        }
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error("RegisterResponseListener", t.getMessage(), t);
        windowStage.addActor(registerWindow.fadeIn(0.3f));
    }

    @Override
    public void cancelled() {
        Gdx.app.error("RegisterResponseListener", "Cancelled.");
        windowStage.addActor(registerWindow.fadeIn(0.3f));
    }
}
