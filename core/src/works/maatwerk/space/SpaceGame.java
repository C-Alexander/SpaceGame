package works.maatwerk.space;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;
import works.maatwerk.space.models.Faction;
import works.maatwerk.space.models.User;
import works.maatwerk.space.networking.runnables.GetFactionsRunnable;

import java.util.List;

public class SpaceGame extends Game {
    private AccountManager accountManager;
    private List<Faction> factions;

    @Override
	public void create () {
        accountManager = new AccountManager(this);
        new Thread(new GetFactionsRunnable(this)).start();
        VisUI.load();

        setScreen(new IdentificationScreen(this));
    }

	@Override
	public void dispose () {
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

    public void setFactions(List<Faction> factions) {
        this.factions = factions;

        User user = getAccountManager().getCurrentUser();
        setFactionForUser(user);
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public void setFactionForUser(User user) {
        if (user != null && factions != null && factions.size() > 0)
        user.setFaction(factions.stream().filter(faction -> faction.getId().equals(user.getFactionId())).findFirst().get());
    }
}
