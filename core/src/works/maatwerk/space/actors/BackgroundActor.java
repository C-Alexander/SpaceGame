package works.maatwerk.space.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {
    private final TextureRegion background;

    public BackgroundActor(TextureRegion background) {
        super();
        this.background = background;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(background, getX(), getY());
    }
}
