package works.maatwerk.space.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.space.windows.FactionWindow;

public class FactionFlagActor extends Actor {
    Texture flag;

    public FactionFlagActor(String flag, FactionWindow window, Stage windowStage) {
        if (flag.isEmpty())
        this.flag = new Texture(Gdx.files.internal("factions/flags/" + "piratelogo.jpg"));
        else this.flag = new Texture(Gdx.files.internal("factions/flags/" + flag));


        if (window != null && windowStage != null)
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (window.getStage() != null) {
                    window.setVisible(false);
                    window.fadeOut();
                } else {
                    window.setVisible(true);
                    windowStage.addActor(window.fadeIn(0.3f));
                }
            }
        });
        this.setSize(this.flag.getWidth(), this.flag.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (isVisible())
        batch.draw(flag, this.getX(), this.getY());
    }

    public void setFlagFromName(String name) {
        this.flag = new Texture(Gdx.files.internal("factions/flags/" + name));
        this.setSize(this.flag.getWidth(), this.flag.getHeight());
    }
}
