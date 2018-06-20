package works.maatwerk.space.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import works.maatwerk.space.actors.ShipActor;

public class ShipClickListener extends ClickListener {
    private final ShipActor shipActor;

    public ShipClickListener(ShipActor shipActor) {
        this.shipActor = shipActor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);

        System.out.println("hi");

        event.stop();

        shipActor.isClicked();
    }
}
