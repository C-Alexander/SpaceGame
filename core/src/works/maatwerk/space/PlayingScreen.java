package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import works.maatwerk.space.models.DestinationPacket;
import works.maatwerk.space.networking.NetworkManager;
import works.maatwerk.space.networking.runnables.UpdateMapRunnable;

public class PlayingScreen extends ScreenAdapter {
    private final TextureRegion background;
    private final NetworkManager networkManager;
    private SpriteBatch batch;
    private final SpaceGame spaceGame;
    private GameUI gameUI;

    public PlayingScreen(SpaceGame spaceGame) {
        this.spaceGame = spaceGame;
        networkManager = new NetworkManager(this);
        Texture stars = new Texture(Gdx.files.internal("stars.jpg"));
        stars.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        background = new TextureRegion(stars, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        initializeWebSockets();
        initializeUI();
        initializeMap();
    }

    private void initializeMap() {
        new Thread(new UpdateMapRunnable(gameUI)).start();
    }

    private void initializeUI() {
        gameUI = new GameUI(spaceGame, this, spaceGame.getAccountManager());
    }

    private void initializeWebSockets() {
        networkManager.connect();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        batch.begin();
        gameUI.act(delta);
        gameUI.draw();
        batch.end();
    }

    public void setDestination(Vector2 selectedMapLocation) {
        DestinationPacket destinationPacket = new DestinationPacket(spaceGame.getAccountManager().getCurrentShip().getId(), selectedMapLocation);
        networkManager.sendPacket(destinationPacket);
    }

    public void updateDestination(String shipId, Vector2 destination) {
        gameUI.updateDestination(shipId, destination);
    }

    public void moveAllShips() {
        gameUI.moveAllShips();
    }
}
