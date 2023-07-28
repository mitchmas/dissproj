package adbt171.mmproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //enable interactive and usable buttons/textfields
    @FXML
    private Button go_signup;

    @FXML
    private TextField user_field;

    @FXML
    private TextField pass_field;

    @FXML
    private Button login_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        go_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //switch stage to sign up on sign up button pressed. adapted from https://www.youtube.com/watch?v=ltX5AtW9v30
                DBConn.changeScene(actionEvent, "signup.fxml", "Sign up!", null);
            }
        });

        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //initiate login method in the database interaction class. adapted from https://www.youtube.com/watch?v=ltX5AtW9v30
                DBConn.logIn(actionEvent, user_field.getText(), pass_field.getText());
            }
        });
    }
}