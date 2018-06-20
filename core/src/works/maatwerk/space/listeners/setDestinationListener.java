package works.maatwerk.space.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import works.maatwerk.space.GameUI;

public class setDestinationListener extends ChangeListener {
    private final GameUI gameUI;

    public setDestinationListener(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        gameUI.setDestination();
    }
}
