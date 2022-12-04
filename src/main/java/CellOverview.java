import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class CellOverview implements Initializable {


    DAO database = new DAO();
    @FXML
    private AnchorPane prison;

    public static int selectedCellNumber ;
    private Cell cell;
    private List<Cell> cells = new ArrayList<>();

    public CellOverview() throws SQLException {
    }


    @FXML
    private void openCell(javafx.event.ActionEvent event) throws IOException {
        Object node = event.getSource();
        Button b = (Button) node;
        selectedCellNumber = Integer.parseInt(b.getText());
        System.out.println(selectedCellNumber);
        cell = cells.get(selectedCellNumber-1);
        if (cell.getCellMembers().isEmpty()) {
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Warning");
            alertwindow.setContentText("Empty Cell");
            alertwindow.showAndWait();

        } else {
            Image img = new Image("/icons/list_icon.png");
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/CELL.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setTitle("Prisoners");
            stage.getIcons().add(img);
            stage.setScene(scene);
            stage.show();
        }

    }



    @FXML
    private void transferPrisoner(){

    }

    @FXML
    private void removePrisoner(){

    }

    protected void addHoverEffect(){
        int cellCounter = 0;
        AtomicReference<String> originalStyle = new AtomicReference<>("-fx-background-color: rgba(128,128,128,0.5)");
        for (int i = 1; i < prison.getChildren().size(); i++) {
            GridPane currentblock = (GridPane) prison.getChildren().get(i);
            for (int j = 0; j < currentblock.getChildren().size(); j++){
                Node currentCell = currentblock.getChildren().get(j);
                cellCounter +=1;
                currentCell.hoverProperty().addListener(observable -> {
                    if (currentCell.isHover()) {
                        originalStyle.set(currentCell.getStyle());
                        currentCell.setStyle("-fx-background-color: rgba(128,128,128,0.5)");
                    }
                    else
                        currentCell.setStyle(originalStyle.get());
                });

                for (Cell c : cells){
                    if (c.getCellID() == cellCounter)
                        if (!c.getCellMembers().isEmpty())
                            currentCell.setStyle("-fx-background-color: rgba(50,205,50,0.5)");

                }
            }
        }
    }

    protected void updateCells() throws SQLException {
        List<Prisoner> prisoners = database.getPrisonerList();
        int numberOfCells = 24;
        for (int i = 1; i <= numberOfCells; i++){
            Cell c = new Cell();
            c.setCellID(i);
            if (i < 18)
                c.setMaxCapacity(2);
            else
                c.setMaxCapacity(1);
            for (Prisoner p : prisoners)
                if (p.getCellNum() == i)
                    c.addPrisonerToCell(p);
            cells.add(c);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            updateCells();
            addHoverEffect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
