package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

class IdentificationScreen extends ScreenAdapter {

    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private Stage stage;
    private SpriteBatch batch;
    private final SpaceGame spaceGame;

    public IdentificationScreen(SpaceGame spaceGame) {

        this.spaceGame = spaceGame;
    }

    @Override
    public void show() {
        super.show();

        batch = new SpriteBatch();
        stage = new Stage();

        loginWindow = new LoginWindow(spaceGame, this);
        registerWindow = new RegisterWindow(spaceGame, this);

        stage.addActor(loginWindow);
        loginWindow.usernameField.focusField();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        stage.act();
        batch.end();
    }

    public LoginWindow getLoginWindow() {
        return loginWindow;
    }

    public void setLoginWindow(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    public RegisterWindow getRegisterWindow() {
        return registerWindow;
    }

    public void setRegisterWindow(RegisterWindow registerWindow) {
        this.registerWindow = registerWindow;
    }

    public Stage getStage() {
        return stage;
    }
}
