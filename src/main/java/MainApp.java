
import javafx.scene.image.Image;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        Image img = new Image("/icons/login_icon.png");

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/LOGIN.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Log in");
        stage.getIcons().add(img);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

