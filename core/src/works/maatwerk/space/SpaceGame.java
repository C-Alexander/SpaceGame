package works.maatwerk.space;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.kotcrab.vis.ui.VisUI;
import works.maatwerk.space.models.Faction;
import works.maatwerk.space.models.User;
import works.maatwerk.space.networking.runnables.GetFactionsRunnable;

public class SpaceGame extends Game {
    private AccountManager accountManager;
    private ObjectMap<String, Faction> factions;

    @Override
	public void create () {
        accountManager = new AccountManager(this);
        new Thread(new GetFactionsRunnable(this)).start();
        VisUI.load();

        setScreen(new IdentificationScreen(this));
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void startGame() {
        this.setScreen(new PlayingScreen(this));
    }

    public ObjectMap<String, Faction> getFactions() {
        return factions;
    }

    public void setFactions(ObjectMap<String, Faction> factions) {
        this.factions = factions;
    }

    public void setFactionForUser(User user) {
        if (user != null && user.getFactionId() != null && factions != null && factions.size > 0)
            user.setFaction(factions.get(user.getFactionId()));
        else Gdx.app.error("SpaceGame", "Trying to get a Faction for a User, but value or factions was Null");
    }
}
