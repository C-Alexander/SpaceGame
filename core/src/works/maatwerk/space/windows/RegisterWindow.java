package works.maatwerk.space.windows;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.building.CenteredTableBuilder;
import com.kotcrab.vis.ui.building.OneRowTableBuilder;
import com.kotcrab.vis.ui.building.StandardTableBuilder;
import com.kotcrab.vis.ui.util.form.FormValidator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import works.maatwerk.space.IdentificationScreen;
import works.maatwerk.space.SpaceGame;
import works.maatwerk.space.models.User;
import works.maatwerk.space.networking.runnables.RegisterRunnable;

public class RegisterWindow extends VisWindow {

    private final VisTextButton loginButton;
    private final VisTextButton registerButton;
    private final VisValidatableTextField usernameField;
    private final VisValidatableTextField passwordField;
    public final VisLabel errorLabel;
    private final SpaceGame spaceGame;
    private final IdentificationScreen identificationScreen;

    public RegisterWindow(SpaceGame spaceGame, IdentificationScreen identificationScreen) {
        super("Register", true);
        this.spaceGame = spaceGame;
        this.identificationScreen = identificationScreen;

        this.setWidth(500);
        this.centerWindow();
        this.fadeIn(0.5f);

        CenteredTableBuilder centeredTableBuilder = new CenteredTableBuilder();
        StandardTableBuilder detailsTableBuilder = new StandardTableBuilder();
        OneRowTableBuilder buttonTableBuilder = new OneRowTableBuilder();

        errorLabel = new VisLabel();

        VisLabel usernameLabel = new VisLabel("Username: ");
        usernameField = new VisValidatableTextField();
        VisLabel passwordLabel = new VisLabel("Password: ");
        passwordField = new VisValidatableTextField();
        passwordField.setPasswordMode(true);

        loginButton = new VisTextButton("Return to Login");
        registerButton = new VisTextButton("Register");

        FormValidator validator = new FormValidator(registerButton, errorLabel);
        validator.notEmpty(usernameField, "Please fill in your username");
        validator.notEmpty(passwordField, "Please fill in your password");

        buttonTableBuilder.append(loginButton);
        buttonTableBuilder.append(registerButton);
        detailsTableBuilder.append(usernameLabel);
        detailsTableBuilder.append(usernameField);
        detailsTableBuilder.row();
        detailsTableBuilder.append(passwordLabel);
        detailsTableBuilder.append(passwordField);
        centeredTableBuilder.append(errorLabel);
        centeredTableBuilder.row();
        centeredTableBuilder.append(detailsTableBuilder.build());
        centeredTableBuilder.row();
        centeredTableBuilder.append(buttonTableBuilder.build());

        add(centeredTableBuilder.build());

        setupListeners();
    }

    private void setupListeners() {
        registerButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new Thread(new RegisterRunnable(spaceGame, identificationScreen, new User(usernameField.getText(), passwordField.getText()))).start();
            }
        });
        loginButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fadeOut(0.3f);
                identificationScreen.getStage().addActor(identificationScreen.getLoginWindow().fadeIn(0.3f));
            }
        });
    }
}
