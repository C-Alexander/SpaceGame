package works.maatwerk.space.models;

import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private final String id;
    private final String name;
    public final List<Ship> ships = new ArrayList<>();

    public Map(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Map(JsonValue map) {
        this.id = map.getString("id");
        this.name = map.getString("name");

        createShipsFromJson(map.get("ships"));
    }

    public void createShipsFromJson(JsonValue ships) {
        for (JsonValue ship : ships) {
            Ship newShip = new Ship(ship);
            this.ships.add(newShip);
        }
    }
}
