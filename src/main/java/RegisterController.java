
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {

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


    @FXML
    void regSave(javafx.event.ActionEvent event) throws IOException {

        if (isUsernameAlredyRegistered(regUsername.getText())){
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning!");
            alertwindow.setContentText("Username already exists!");
            alertwindow.showAndWait();
        }else if (regPassword.getText().equals(regPasswordAgain.getText()) && !isUsernameAlredyRegistered(regUsername.getText())){
            BufferedWriter usersFile = new BufferedWriter(new FileWriter("src/data/users.txt", true));
            usersFile.append(regUsername.getText() + "\n");
            usersFile.close();

            BufferedWriter passwordsFile = new BufferedWriter(new FileWriter("src/data/passwords.txt", true));
            passwordsFile.append(regPassword.getText() + "," + regUsername.getText() + "\n");
            passwordsFile.close();

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
    void regCancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) cancelRegButton.getScene().getWindow();
        stage.close();
    }


    boolean isUsernameAlredyRegistered(String username) throws IOException {
        BufferedReader in = null;
        FileReader fr = null;

        try {
            fr = new FileReader("src/data/users.txt");
            in = new BufferedReader(fr);
            String str;
            while ((str = in.readLine()) != null) {
                if(str.equals(username))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
            fr.close();
        }
        return false;
    }
}
