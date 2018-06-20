package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.widget.VisLabel;

class ShipActor extends Actor {

    private final VisLabel visLabel;
    private final Texture texture;
    private final Ship ship;
    private GameUI gameUI;

    public ShipActor(Ship ship, GameUI gameUI) {
        this.gameUI = gameUI;
        this.ship = ship;

        texture = new Texture(Gdx.files.internal("Pirate.gif"));
        visLabel = new VisLabel(ship.getCaptain().getUsername());
        setName(ship.getId());

        this.setPosition(ship.getLocation().x, ship.getLocation().y);
        this.setSize(texture.getWidth() + visLabel.getWidth(), texture.getHeight());
        visLabel.setPosition(this.getX() + texture.getWidth(), this.getY());

        addListener(new ShipClickListener(this));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        visLabel.setPosition(this.getX() + texture.getWidth(), this.getY());
        visLabel.draw(batch, parentAlpha);
        batch.draw(texture, this.getX(), this.getY());
    }

    public Ship getShip() {
        return ship;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    public void isClicked() {
        gameUI.selectedShipActor(this);
    }
}
