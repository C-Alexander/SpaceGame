package works.maatwerk.space;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;

public class SpaceGame extends Game {
    private AccountManager accountManager;

    @Override
	public void create () {
        accountManager = new AccountManager();

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
}
