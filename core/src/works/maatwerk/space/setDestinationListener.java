package works.maatwerk.space;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

class setDestinationListener extends ChangeListener {
    private final GameUI gameUI;

    public setDestinationListener(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        gameUI.setDestination();
    }
}
