package works.maatwerk.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.building.CenteredTableBuilder;
import com.kotcrab.vis.ui.building.OneColumnTableBuilder;
import com.kotcrab.vis.ui.building.OneRowTableBuilder;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import works.maatwerk.space.actors.BackgroundActor;
import works.maatwerk.space.actors.FactionFlagActor;
import works.maatwerk.space.actors.ShipActor;
import works.maatwerk.space.listeners.MapClickListener;
import works.maatwerk.space.listeners.setDestinationListener;
import works.maatwerk.space.models.Map;
import works.maatwerk.space.models.Ship;
import works.maatwerk.space.models.User;
import works.maatwerk.space.windows.FactionWindow;

import java.util.ArrayList;
import java.util.List;

public class GameUI extends Stage {
    private final SpaceGame spaceGame;
    private final PlayingScreen playingScreen;
    private final AccountManager accountManager;
    private Table playerInfoTable;
    private final Group tacticalScreen;
    private VisLabel selectedShipName;
    private Group rightMenu;
    private Vector2 selectedMapLocation;
    private VisLabel xCoordinatesName;
    private VisLabel yCoordinatesName;
    private Group leftMenu;
    private final List<ShipActor> shipActors = new ArrayList<>();
    private FactionFlagActor factionFlagActor;

    public GameUI(SpaceGame spaceGame, PlayingScreen playingScreen, AccountManager accountManager) {
        super();
        this.spaceGame = spaceGame;
        this.playingScreen = playingScreen;
        this.accountManager = accountManager;
        selectedMapLocation = new Vector2(0, 0);

        initializeLeftMenu();
        initializeRightMenu();

        tacticalScreen = new Group();
        initializeTacticalScreen();
        initializeGridBackground(tacticalScreen);
        addActor(tacticalScreen);

        Gdx.input.setInputProcessor(tacticalScreen.getStage());

        this.setDebugAll(true);
    }

    private void initializeRightMenu() {
        rightMenu = new Group();

        Table selectedInfo = initializeSelectedInfo();

        rightMenu.setSize(150, 400);
        selectedInfo.setSize(rightMenu.getWidth(), rightMenu.getHeight());
        rightMenu.addActor(selectedInfo);
        rightMenu.setPosition(Gdx.graphics.getWidth() - rightMenu.getWidth(), Gdx.graphics.getHeight() - rightMenu.getHeight());
        rightMenu.setVisible(false);

        addActor(rightMenu);
    }

    private Table initializeSelectedInfo() {
        CenteredTableBuilder centeredTableBuilder = new CenteredTableBuilder();
        OneColumnTableBuilder selectedInfoBuilder = new OneColumnTableBuilder();

        selectedShipName = new VisLabel("None");
        Table coordinatesTable = initializeCoordinatesTable();

        factionFlagActor = new FactionFlagActor("", null, this);
        selectedInfoBuilder.append(factionFlagActor);
        selectedInfoBuilder.append(selectedShipName);
        selectedInfoBuilder.append(coordinatesTable);
        VisTextButton goToButton = new VisTextButton("Go To");
        goToButton.addListener(new setDestinationListener(this));
        selectedInfoBuilder.append(goToButton);
        centeredTableBuilder.append(selectedInfoBuilder.build());

        return centeredTableBuilder.build();
    }

    private Table initializeCoordinatesTable() {
        OneRowTableBuilder coordinatesBuilder = new OneRowTableBuilder();
        float coordinateScale = 1f;
        xCoordinatesName = new VisLabel("X: 0");
        xCoordinatesName.setFontScale(coordinateScale);
        coordinatesBuilder.append(xCoordinatesName);
        VisLabel spacingCoordinates = new VisLabel(", ");
        spacingCoordinates.setFontScale(coordinateScale);
        coordinatesBuilder.append(spacingCoordinates);
        yCoordinatesName = new VisLabel("Y: 0");
        yCoordinatesName.setFontScale(coordinateScale);
        coordinatesBuilder.append(yCoordinatesName);
        return coordinatesBuilder.build();
    }

    private void initializeLeftMenu() {
        leftMenu = new Group();
        leftMenu.setSize(150, 400);
        leftMenu.setPosition(0, Gdx.graphics.getHeight() - leftMenu.getHeight());

        CenteredTableBuilder centeredTable = initializePlayerInfoTable();
        centeredTable.append(playerInfoTable);
        Table build = centeredTable.build();
        build.setSize(leftMenu.getWidth(), leftMenu.getHeight());
        leftMenu.addActor(build);
        addActor(leftMenu);
    }

    private CenteredTableBuilder initializePlayerInfoTable() {
        CenteredTableBuilder centeredTable = new CenteredTableBuilder();
        OneColumnTableBuilder playerInfoTableBuilder = new OneColumnTableBuilder();
        User user = accountManager.getCurrentUser();
        playerInfoTableBuilder.append(new FactionFlagActor(user.getFaction().getFlag(),
                new FactionWindow(user.getFaction()), this));
        playerInfoTableBuilder.append(new VisLabel(user.getUsername()));
        OneRowTableBuilder creditsTableBuilder = new OneRowTableBuilder();
        VisLabel creditsCount = new VisLabel(spaceGame.getAccountManager().getCurrentUser().getCredits().toString());
        creditsTableBuilder.append(creditsCount);
        creditsTableBuilder.append(new VisLabel("Cr"));
        playerInfoTableBuilder.append(creditsTableBuilder.build());
        playerInfoTable = playerInfoTableBuilder.build();
        return centeredTable;
    }

    private void initializeTacticalScreen() {
        tacticalScreen.setDebug(true, true);
        tacticalScreen.setSize(650, Gdx.graphics.getHeight() - 100);
        tacticalScreen.setPosition(leftMenu.getWidth() + 10, Gdx.graphics.getHeight() - tacticalScreen.getHeight());
        tacticalScreen.setColor(Color.BLUE);
        tacticalScreen.addListener(new MapClickListener(this));
    }

    private void initializeGridBackground(Group tacticalScreen) {
        Texture grid = new Texture(Gdx.files.internal("grid.gif"));
        grid.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        tacticalScreen.addActor(new BackgroundActor(new TextureRegion(grid, (int) tacticalScreen.getWidth(), (int) tacticalScreen.getHeight())));
    }

    public void replaceSolarSystem(Map map) {
        for (Ship ship : map.ships) {
            if (ship.getCaptain().getUsername().equalsIgnoreCase(spaceGame.getAccountManager().getCurrentUser().getUsername()))
                spaceGame.getAccountManager().setCurrentShip(ship);
            ShipActor shipActor = new ShipActor(ship, this, spaceGame);
            shipActors.add(shipActor);
            tacticalScreen.addActor(shipActor);
        }
    }

    public void selectedShipActor(ShipActor shipActor) {
        setMapLocation((int) shipActor.getX(), (int) shipActor.getY());

        ShipActor selectedShipActor = shipActor;
        factionFlagActor.setFlagFromName(selectedShipActor.getShip().getCaptain().getFaction().getFlag());
        factionFlagActor.setVisible(true);
        selectedShipName.setText(shipActor.getShip().getCaptain().getUsername());

    }

    public void selectedMapPosition(float x, float y) {
        setMapLocation((int) x, (int) y);

        factionFlagActor.setVisible(false);
        selectedShipName.setText("Space");
    }

    private void setMapLocation(int x, int y) {
        if (!rightMenu.isVisible()) rightMenu.setVisible(true);

        selectedMapLocation.x = x;
        selectedMapLocation.y = y;

        updateCoordinatesText();
    }

    private void updateCoordinatesText() {
        xCoordinatesName.setText("X: " + (int) selectedMapLocation.x);
        yCoordinatesName.setText("Y: " + (int) selectedMapLocation.y);

    }

    public Vector2 getSelectedMapLocation() {
        return selectedMapLocation;
    }

    public void setSelectedMapLocation(Vector2 selectedMapLocation) {
        this.selectedMapLocation = selectedMapLocation;
    }

    public void setDestination() {
        playingScreen.setDestination(selectedMapLocation);
    }

    public void updateDestination(String shipId, Vector2 destination) {
        ((ShipActor) tacticalScreen.findActor(shipId)).getShip().setDestination(destination);
    }

    public void moveAllShips() {
        for (ShipActor shipActor : shipActors) {
            Vector2 location = shipActor.getShip().getLocation();
            Vector2 destination = shipActor.getShip().getDestination();


            if (location != destination) {
                location.lerp(destination, 0.1f);

                location.x = (int) location.x;
                location.y = (int) location.y;

                shipActor.setPosition((int) location.x, (int) location.y);
            }
        }
    }
}
