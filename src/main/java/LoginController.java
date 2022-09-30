import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;


public class LoginController implements Initializable {

    public static String logInTime;
    public static String username;
    public static String password;

    @FXML
    private ChoiceBox<String> prisonChoiceBox;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    public static String selectedPrison;


    Image icon = new Image("/icons/prison_icon.png");


    @FXML
    void onLogin(ActionEvent event) throws IOException {
        username = usernameField.getText();
        password = passwordField.getText();
        logInTime=LocalDate.now()+" "+ LocalTime.now();

        if (prisonChoiceBox.getValue()==null){
            Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
            alertwindow.setTitle("Warning");
            alertwindow.setContentText("Please choose a prison to continue");
            alertwindow.showAndWait();
        }else {
            try {
                selectedPrison = prisonChoiceBox.getValue();

                if(!isValidUsername(username))
                {
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                    alertwindow.setTitle("Hibás belépés");
                    alertwindow.setContentText("Felhasználó nem található");
                    alertwindow.show();

                }else if (isValidAccount(username,password)){
                    try {

                        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);

                        alertwindow.setTitle("Logged in successfully");
                        alertwindow.setContentText("You logged in!");
                        alertwindow.showAndWait();

                        Stage stage2 = (Stage) loginButton.getScene().getWindow();
                        stage2.close();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GUI.fxml"));
                        Parent par1 =fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setResizable(true);
                        stage.setTitle("Prison management");
                        stage.getIcons().add(icon);
                        stage.setScene(new Scene(par1));
                        stage.show();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else{
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                    alertwindow.setTitle("Hibás belépés");
                    alertwindow.setContentText("Hibás felhasználónév vagy jelszó");
                    alertwindow.show();

                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onRegister(ActionEvent event) throws IOException{
        try {
            Image adminRegIcon = new Image("/icons/register_admin.png");
            Stage regStage = new Stage();

            FXMLLoader regFxmlLoader = new FXMLLoader(getClass().getResource("/fxml/REGISTER.fxml"));
            Parent root = regFxmlLoader.load();
            Scene scene = new Scene(root);
            regStage.setTitle("Register new account");
            regStage.getIcons().add(adminRegIcon);
            regStage.setScene(scene);
            regStage.show();

        } catch (IOException e) {
            System.out.println("cant find the fxml ");
        }
    }

    private boolean isValidUsername(String username) throws IOException {
        List<String> usernames = readFile("src/data/users.txt");
        return usernames.stream().anyMatch(o -> o.equals(username));
    }


    private String getPasswordByUserName(String username) throws IOException {

        List<String> passwords = readFile("src/data/passwords.txt");
        for (String line : passwords) {
            String currentUser;
            if (line.substring(line.indexOf(",") + 1).equals(username)){
                return  line.substring(0, line.indexOf(","));
            }
        }
        return null;
    }

    private boolean isValidAccount(String username, String password) throws IOException {

        if (getPasswordByUserName(username).equals(password)) return true;

        return false;
    }


    public List<String> readFile(String fileName) throws IOException {
        BufferedReader in = null;
        FileReader fr = null;
        List<String> data = new ArrayList<String>();

        try {
            fr = new FileReader(fileName);
            in = new BufferedReader(fr);
            String str;
            while ((str = in.readLine()) != null) {
                data.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
            fr.close();
        }
        return data;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> prisons;
        try {
            prisons = readFile("src/data/prisons.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prisonChoiceBox.getItems().addAll(prisons);

    }
}


