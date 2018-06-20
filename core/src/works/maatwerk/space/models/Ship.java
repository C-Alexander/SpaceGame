package works.maatwerk.space.models;

import com.badlogic.gdx.math.Vector2;

public class Ship {
    private String id;
    private int hull;
    private int shield;
    private int armor;
    private Vector2 location;
    private User captain;
    private Vector2 destination;

    public Ship(String id, int hull, int shield, int armor, Vector2 location, User captain) {
        this.id = id;
        this.hull = hull;
        this.shield = shield;
        this.armor = armor;
        this.location = location;
        this.captain = captain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public Vector2 getLocation() {
        return location;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public User getCaptain() {
        return captain;
    }

    public void setCaptain(User captain) {
        this.captain = captain;
    }

    public void setDestination(Vector2 destination) {
        this.destination = destination;
    }

    public Vector2 getDestination() {
        return destination;
    }
}
