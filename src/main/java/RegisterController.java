
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class RegisterController {

    DAO database = new DAO();

    @FXML
    private Button cancelRegButton;

    @FXML
    private Button saveRegButton;

    @FXML
    private PasswordField regPassword;

    @FXML
    private PasswordField regPasswordAgain;

    @FXML
    private TextField regUsername;

    public RegisterController() throws SQLException {
    }


    @FXML
    void regSave() throws SQLException {

        if (isUsernameAlreadyRegistered(regUsername.getText())){
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning!");
            alertwindow.setContentText("Username already exists!");
            alertwindow.showAndWait();
        }else if (regPassword.getText().equals(regPasswordAgain.getText()) && !isUsernameAlreadyRegistered(regUsername.getText())){
            database.registerNewUser(regUsername.getText(), regPassword.getText());

            Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
            alertwindow.setTitle("Information");
            alertwindow.setContentText("Registered successfully");
            alertwindow.showAndWait();

            Stage stage = (Stage) saveRegButton.getScene().getWindow();
            stage.close();

        } else if(regUsername.getText().equals("")){
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning!");
            alertwindow.setContentText("Please type a username!");
            alertwindow.showAndWait();
        }else if (regPassword.getText().equals("") || regPasswordAgain.getText().equals("")){
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning!");
            alertwindow.setContentText("Please fill all password fields");
            alertwindow.showAndWait();
        }else if (!regPassword.getText().equals(regPasswordAgain.getText())){
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning!");
            alertwindow.setContentText("Password aren't match!");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void regCancel() {
        Stage stage = (Stage) cancelRegButton.getScene().getWindow();
        stage.close();
    }


    boolean isUsernameAlreadyRegistered(String username) throws SQLException {
        List<String> usernames = database.getRegisteredUsers();
        return usernames.stream().anyMatch(o -> o.equals(username));
    }
}
