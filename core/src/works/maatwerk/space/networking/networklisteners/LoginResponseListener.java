package works.maatwerk.space.networking.networklisteners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.windows.LoginWindow;

public class LoginResponseListener implements Net.HttpResponseListener {
    private final LoginWindow loginWindow;
    private final SpaceGame spaceGame;
    private final Stage windowStage;

    public LoginResponseListener(LoginWindow loginWindow, SpaceGame spaceGame, Stage windowStage) {
        this.loginWindow = loginWindow;
        this.spaceGame = spaceGame;
        this.windowStage = windowStage;
    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        if (httpResponse.getStatus().getStatusCode() == 200) {
            spaceGame.getAccountManager().setSessionFromResponse(httpResponse);
            Gdx.app.postRunnable(() -> {
                loginWindow.fadeOut(0.3f);
                spaceGame.startGame();
            });
        } else {
            loginWindow.errorLabel.setText(httpResponse.getResultAsString());
            loginWindow.errorLabel.setColor(Color.RED);
        }
    }

    @Override
    public void failed(Throwable t) {
        Gdx.app.error("LoginResponseListener", t.getMessage(), t);
        windowStage.addActor(loginWindow.fadeIn(0.3f));
    }

    @Override
    public void cancelled() {
        Gdx.app.error("LoginResponseListener", "Cancelled");
        windowStage.addActor(loginWindow.fadeIn(0.3f));
    }
}
