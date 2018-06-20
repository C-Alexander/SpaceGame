package works.maatwerk.space.windows;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.kotcrab.vis.ui.widget.VisWindow;
import works.maatwerk.space.models.Faction;

public class FactionWindow extends VisWindow {
    public FactionWindow(Faction faction) {
        super("Faction - " , true);

        this.getTitleLabel().setText("Faction - " + faction.getName());

        this.setVisible(false);

        this.setSize(400, 400);

        centerWindow();
    }
}
