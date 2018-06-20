package works.maatwerk.space;

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

public class LoginWindow extends VisWindow {

    public final VisValidatableTextField usernameField;
    private final VisTextButton registerButton;
    private final VisTextButton loginButton;
    private final VisValidatableTextField passwordField;
    private final SpaceGame spaceGame;
    private final IdentificationScreen identificationScreen;
    public final VisLabel errorLabel;

    public LoginWindow(SpaceGame spaceGame, IdentificationScreen identificationScreen) {
        super("Login", false);
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
        this.passwordField = new VisValidatableTextField();
        passwordField.setPasswordMode(true);

        loginButton = new VisTextButton("Login");
        registerButton = new VisTextButton("Register");

        FormValidator validator = new FormValidator(loginButton, errorLabel);
        validator.notEmpty(usernameField, "Please fill in your username");
        validator.notEmpty(passwordField, "Please fill in your password");

        buttonTableBuilder.append(registerButton);
        buttonTableBuilder.append(loginButton);
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
                    fadeOut(0.3f);
                    identificationScreen.getStage().addActor(identificationScreen.getRegisterWindow().fadeIn(0.3f));
                }
            });
            loginButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    new Thread(new LoginRunnable(spaceGame, identificationScreen, new User(usernameField.getText(), passwordField.getText()))).start();
                }
            });
        }
}
