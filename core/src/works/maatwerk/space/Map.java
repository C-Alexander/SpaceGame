package works.maatwerk.space;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

class Map {
    private final String id;
    private final String name;
    public final List<Ship> ships = new ArrayList<>();

    public Map(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void createShipsFromJson(JsonValue ships) {
        for (JsonValue ship : ships) {
            Ship newShip = new Ship(ship.getString("id"),
                    ship.getInt("hull"),
                    ship.getInt("shield"),
                    ship.getInt("armor"),
                    new Vector2(ship.getInt("xPos"), ship.getInt("yPos")),
                    new User(ship.get("users").child().getString("username")));
            newShip.setDestination(new Vector2(
                    ship.getInt("xDestination", (int)newShip.getLocation().x),
                    ship.getInt("yDestination", (int)newShip.getLocation().y)
                    ));
            this.ships.add(newShip);
        }
    }
}
