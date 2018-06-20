package works.maatwerk.space.models;

import com.badlogic.gdx.math.Vector2;

public class DestinationPacket {
    private final String id;
    private final Vector2 destination;

    public DestinationPacket(String id, Vector2 selectedMapLocation) {
        this.id = id;
        this.destination = selectedMapLocation;
    }
}
