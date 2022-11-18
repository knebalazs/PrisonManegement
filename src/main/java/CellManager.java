import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CellManager implements Initializable {

    DAO database = new DAO();

    @FXML
    private AnchorPane prison;

    private List<Cell> cells;

    public CellManager() throws SQLException {
    }


    private void addHoverEffect(){
        for (int i = 1; i < prison.getChildren().size(); i++) {
            GridPane currentblock = (GridPane) prison.getChildren().get(i);
            for (int j = 0; j < currentblock.getChildren().size(); j++){
                Node currentCell = currentblock.getChildren().get(j);
                currentCell.hoverProperty().addListener(observable -> {
                    if (currentCell.isHover())
                        currentCell.setStyle("-fx-background-color: rgba(255,255,0,0.5)");
                    else
                        currentCell.setStyle("-fx-background-color: transparent");
                    });
            }
        }
    }

    private void updateCells() throws SQLException {
        List<Prisoner> prisoners = database.getPrisonerList();
        for (Prisoner p : prisoners){
            for (Cell c : cells){
                //if()
            }
            Cell cell = new Cell();
            cell.setCellID(p.getCellNum());
            cell.addPrisonerToCell(p);
            cells.add(cell);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addHoverEffect();
    }
}
