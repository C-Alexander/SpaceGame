package works.maatwerk.space;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AccountManager {

    private User currentUser;
    private Ship currentShip;

    public void setSessionFromResponse(Net.HttpResponse response) {
        JsonReader jsonReader = new JsonReader();
        JsonValue body = jsonReader.parse(response.getResultAsString());

        String username = body.getString("username");
        String password = body.getString("password");
        Float credits = body.getFloat("credits");
        String token = body.getString("token");

        setCurrentUser(new User(username, password, credits, token));
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
