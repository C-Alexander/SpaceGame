package works.maatwerk.space;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import works.maatwerk.space.models.Ship;
import works.maatwerk.space.models.User;

public class AccountManager {

    private User currentUser;
    private Ship currentShip;
    private SpaceGame spaceGame;

    public AccountManager(SpaceGame spaceGame) {

        this.spaceGame = spaceGame;
    }

    public void setSessionFromResponse(Net.HttpResponse response) {
        JsonReader jsonReader = new JsonReader();
        JsonValue body = jsonReader.parse(response.getResultAsString());

        String username = body.getString("username");
        String password = body.getString("password");
        Float credits = body.getFloat("credits");
        String factionId = body.getString("factionId");
        String token = body.getString("token");

        setCurrentUser(new User(username, password, credits, token, factionId));

        spaceGame.setFactionForUser(getCurrentUser());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Ship getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(Ship currentShip) {
        this.currentShip = currentShip;
    }
}
