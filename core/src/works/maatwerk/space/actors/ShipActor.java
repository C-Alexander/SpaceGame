package works.maatwerk.space.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.widget.VisLabel;
import works.maatwerk.space.GameUI;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.listeners.ShipClickListener;
import works.maatwerk.space.models.Ship;

public class ShipActor extends Actor {

    private final VisLabel visLabel;
    private final Texture texture;
    private final Ship ship;
    private GameUI gameUI;

    public ShipActor(Ship ship, GameUI gameUI, SpaceGame spaceGame) {
        this.gameUI = gameUI;
        this.ship = ship;

        spaceGame.setFactionForUser(ship.getCaptain());

        texture = new Texture(Gdx.files.internal("factions/icons/" + ship.getCaptain().getFaction().getIcon()));
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
