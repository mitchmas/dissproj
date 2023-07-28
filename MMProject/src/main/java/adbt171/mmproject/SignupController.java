package adbt171.mmproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    //prepare fxml buttons and textfields to be used
    @FXML
    private Button go_login;

    @FXML
    private Button signup_button;

    @FXML
    private TextField user_field;

    @FXML
    private TextField pass_field;

    @FXML
    private TextField pass_conf_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        go_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //switch back to login page upon button request. adapted from https://www.youtube.com/watch?v=ltX5AtW9v30
                DBConn.changeScene(actionEvent, "login.fxml","Log in!",null);
            }
        });

        signup_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //error catching to ensure all table values are filled.
                if (!user_field.getText().trim().isEmpty() && !pass_field.getText().trim().isEmpty() && !pass_conf_field.getText().trim().isEmpty()) {
                    //initiate sign up. adapted from https://www.youtube.com/watch?v=ltX5AtW9v30
                    DBConn.signUp(actionEvent, user_field.getText().trim(), pass_field.getText().trim(), pass_conf_field.getText().trim());
                } else {
                    System.out.println("All information must be filled in.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("ERROR! Please fill in all fields.");
                    alert.show();
                }
            }
        });
    }
}