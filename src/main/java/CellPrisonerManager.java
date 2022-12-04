import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CellPrisonerManager implements Initializable {

    @FXML
    private Label cellNumberLabel;
    @FXML
    private Label prisoner1;
    @FXML
    private Label prisoner2;

    private DAO database = new DAO();

    @FXML
    private ChoiceBox<String> prisonerInCellChoicebox;

    private Prisoner selectedPrisoner;

    public CellPrisonerManager() throws SQLException {
    }

    @FXML
    private void findNewCell() throws IOException {
        if(Objects.equals(prisonerInCellChoicebox.getValue(), CellOverview.cell.getCellMembers().get(0).getFirstName() + " " + CellOverview.cell.getCellMembers().get(0).getLastName()))
            selectedPrisoner = CellOverview.cell.getCellMembers().get(0);
        else
            selectedPrisoner = CellOverview.cell.getCellMembers().get(1);

        GUI.prisonerSecurityLevel = selectedPrisoner.getSecurityLevel();

        Image img = new Image("/icons/prison_icon.png");
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/FIND_CELL.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Find Cell");
        stage.getIcons().add(img);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void transferPrisoner() throws SQLException, IOException {
        if(Objects.equals(prisonerInCellChoicebox.getValue(), CellOverview.cell.getCellMembers().get(0).getFirstName() + " " + CellOverview.cell.getCellMembers().get(0).getLastName()))
            selectedPrisoner = CellOverview.cell.getCellMembers().get(0);
        else
            selectedPrisoner = CellOverview.cell.getCellMembers().get(1);

        database.removePrisoner(selectedPrisoner.getUniqueID());

        database.addNewPrisoner(selectedPrisoner.getUniqueID(),selectedPrisoner.getFirstName(),selectedPrisoner.getLastName(),selectedPrisoner.getAge(),
                selectedPrisoner.getSex(), selectedPrisoner.getEntranceDate(), selectedPrisoner.getReleaseDate(), selectedPrisoner.getSecurityLevel(),
                CellManager.selectedCellNumber, Arrays.toString(selectedPrisoner.getCrimes()));

        Stage stage = (Stage) prisonerInCellChoicebox.getScene().getWindow();
        stage.close();

        Image icon = new Image("/icons/prison_icon.png");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GUI.fxml"));
        Parent par1 =fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setResizable(true);
        stage2.setTitle("Prison management");
        stage2.getIcons().add(icon);
        stage2.setScene(new Scene(par1));
        stage2.show();
    }

    @FXML
    private void removePrisoner() throws SQLException, IOException {
        if(Objects.equals(prisonerInCellChoicebox.getValue(), CellOverview.cell.getCellMembers().get(0).getFirstName() + " " + CellOverview.cell.getCellMembers().get(0).getLastName()))
            selectedPrisoner = CellOverview.cell.getCellMembers().get(0);
        else
            selectedPrisoner = CellOverview.cell.getCellMembers().get(1);
        database.removePrisoner(selectedPrisoner.getUniqueID());
        Stage stage = (Stage) prisonerInCellChoicebox.getScene().getWindow();
        stage.close();

        Image icon = new Image("/icons/prison_icon.png");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GUI.fxml"));
        Parent par1 =fxmlLoader.load();
        Stage stage2 = new Stage();
        stage2.setResizable(true);
        stage2.setTitle("Prison management");
        stage2.getIcons().add(icon);
        stage2.setScene(new Scene(par1));
        stage2.show();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Cell currentCell = CellOverview.cell;
        Prisoner prisonerInCell1 = currentCell.getCellMembers().get(0);
        String prisoner1Name = prisonerInCell1.getFirstName() + " " + prisonerInCell1.getLastName();
        String prisoner2Name = "Empty";
        prisonerInCellChoicebox.getItems().addAll(prisoner1Name);
        if (currentCell.getUsedCapacity() > 1) {
            Prisoner prisonerInCell2 = currentCell.getCellMembers().get(1);
            prisoner2Name = prisonerInCell2.getFirstName() + "" + prisonerInCell2.getLastName();
            prisonerInCellChoicebox.getItems().addAll(prisoner2Name);
        }

        cellNumberLabel.setText("Cell " + String.valueOf(currentCell.getCellID()));
        prisoner1.setText(prisoner1Name);
        prisoner2.setText(prisoner2Name);



    }
}
