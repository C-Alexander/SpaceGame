package works.maatwerk.space.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.space.GameUI;

public class MapClickListener extends ClickListener {
    private final GameUI gameUI;

    public MapClickListener(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (event.isStopped()) return;

        gameUI.selectedMapPosition(x, y);
    }
}
