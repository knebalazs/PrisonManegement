import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    private String selectedPrison;
    private String currentUser;

    @FXML
    private ListView<Integer> PrisonerID_list;
    @FXML
    private ListView<String> PrisonerLN_list;
    @FXML
    private ListView<String> PrisonerRD_list;
    @FXML
    private ListView<String> PrisonerCrime_list;
    @FXML
    private ListView<String> PrisonerED_list;
    @FXML
    private ListView<String> PrisonerFN_list;
    @FXML
    private ListView<Integer> PrisonerCN_list;

    @FXML
    private TextField PrisonerID;
    @FXML
    private TextField Prisoner_FN;
    @FXML
    private TextField Prisoner_LN;
    @FXML
    private DatePicker EntranceDate;
    @FXML
    private DatePicker ReleaseDate;
    @FXML
    private TextField Cell_Number;
    @FXML
    private ChoiceBox<String> Crime;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private Label selectedPrisonLabel;
    @FXML
    private Button logOutButton;

    private final List<Integer> pids = new ArrayList<>();
    private final List<String> fnlist = new ArrayList<>();
    private final List<String> lnlist = new ArrayList<>();
    private final List<String> rdlist = new ArrayList<>();
    private final List<String> edlist = new ArrayList<>();
    private final List<String> crimelist = new ArrayList<>();
    private final List<Integer> celnum = new ArrayList<>();



    @FXML
    void addPrisoner() throws IOException {
        int Id = Integer.parseInt(PrisonerID.getText());
        String fName = Prisoner_FN.getText();
        String lName = Prisoner_LN.getText();
        String entranceDate = String.valueOf(EntranceDate.getValue());
        String releaseDate = String.valueOf(ReleaseDate.getValue());
        String cellNumber = String.valueOf(Integer.parseInt(Cell_Number.getText()));
        String crime = Crime.getValue();

        BufferedWriter prisonerFile = new BufferedWriter(new FileWriter("src/data/prisoners.txt", true));
        prisonerFile.append(selectedPrison + "," + Id + "," + fName + "," + lName + "," + entranceDate + "," + releaseDate  + "," + cellNumber + "," + crime + "\n");
        prisonerFile.close();
        refreshPrisonerList();

        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
        alertwindow.setTitle("Information");
        alertwindow.setContentText("Prisoner added successfully");
        alertwindow.showAndWait();


    }
    @FXML
    void deletePrisoner() throws IOException {
        boolean prisonerWasFound = false;
        int Id = Integer.parseInt(PrisonerID.getText());
        String fName = Prisoner_FN.getText();
        String lName = Prisoner_LN.getText();
        String entranceDate = String.valueOf(EntranceDate.getValue());
        String releaseDate = String.valueOf(ReleaseDate.getValue());
        String cellNumber = String.valueOf(Integer.parseInt(Cell_Number.getText()));
        String crime = Crime.getValue();

        File inputFile = new File("src/data/prisoners.txt");
        File tempFile = new File("src/data/prisonersTemp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = (selectedPrison + "," + Id + "," + fName + "," + lName + "," + entranceDate + "," + releaseDate  + "," + cellNumber + "," + crime);
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)){
                prisonerWasFound = true;
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        if (prisonerWasFound){
            Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
            alertwindow.setTitle("Information");
            alertwindow.setContentText("Prisoner deleted successfully");
            alertwindow.showAndWait();
        }
        else{
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);
            alertwindow.setTitle("Information");
            alertwindow.setContentText("Prisoner was not found!");
            alertwindow.showAndWait();
        }
        writer.close();
        reader.close();
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        refreshPrisonerList();
        System.out.println(successful);

    }

    @FXML
    private void changePrison(){

    }

    @FXML
    private void logOut() throws IOException {
        Image img = new Image("/icons/login_icon.png");
        Stage stage = new Stage();

        Stage stage2 = (Stage) logOutButton.getScene().getWindow();
        stage2.close();


        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/LOGIN.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Log in");
        stage.getIcons().add(img);
        stage.setScene(scene);
        stage.show();

    }


    private void fillListOfPrisoners() throws IOException {
        List<String> prisoners = getListOfPrisonersWithSelectedPrison(selectedPrison);
        for (String row:prisoners) {
            String[] columns = row.split(",");
            pids.add(Integer.parseInt(columns[1]));
            fnlist.add(columns[2]);
            lnlist.add(columns[3]);
            edlist.add(columns[4]);
            rdlist.add(columns[5]);
            celnum.add(Integer.parseInt(columns[6]));
            crimelist.add(columns[7]);
        }


    }

    List<String> getListOfPrisonersWithSelectedPrison(String prison) throws IOException {
        BufferedReader in = null;
        FileReader fr = null;
        List<String> data = new ArrayList<String>();

        try {
            fr = new FileReader("src/data/prisoners.txt");
            in = new BufferedReader(fr);
            String str;
            while ((str = in.readLine()) != null) {
                if(str.substring(0, str.indexOf(",")).equals(prison))
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

    public void refreshPrisonerList() throws IOException {
        pids.clear();
        fnlist.clear();
        lnlist.clear();
        edlist.clear();
        rdlist.clear();
        celnum.clear();
        crimelist.clear();
        PrisonerID_list.getItems().clear();
        PrisonerFN_list.getItems().clear();
        PrisonerLN_list.getItems().clear();
        PrisonerED_list.getItems().clear();
        PrisonerRD_list.getItems().clear();
        PrisonerCN_list.getItems().clear();
        PrisonerCrime_list.getItems().clear();

        fillListOfPrisoners();
        PrisonerID_list.getItems().addAll(pids);
        PrisonerFN_list.getItems().addAll(fnlist);
        PrisonerLN_list.getItems().addAll(lnlist);
        PrisonerED_list.getItems().addAll(edlist);
        PrisonerRD_list.getItems().addAll(rdlist);
        PrisonerCN_list.getItems().addAll(celnum);
        PrisonerCrime_list.getItems().addAll(crimelist);
    }

    public void setCrimes() throws IOException {
        BufferedReader in = null;
        FileReader fr = null;
        List<String> data = new ArrayList<String>();

        try {
            fr = new FileReader("src/data/crimes.txt");
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
        Crime.getItems().addAll(data);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = LoginController.username;
        selectedPrison = LoginController.selectedPrison;
        loginStatusLabel.setText(currentUser);
        selectedPrisonLabel.setText(selectedPrison);

        try {
            setCrimes();
            refreshPrisonerList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
