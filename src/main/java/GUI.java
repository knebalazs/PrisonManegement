import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    private String selectedPrison;

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

    private final List<Integer> pids = new ArrayList<>();
    private final List<String> fnlist = new ArrayList<>();
    private final List<String> lnlist = new ArrayList<>();
    private final List<String> rdlist = new ArrayList<>();
    private final List<String> edlist = new ArrayList<>();
    private final List<String> crimelist = new ArrayList<>();
    private final List<Integer> celnum = new ArrayList<>();



    @FXML
    void addPrisoner(){
        //TODO
    }
    @FXML
    void deletePrisoner(){
        //TODO
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPrison = LoginController.selectedPrison;
        try {
            fillListOfPrisoners();
            PrisonerID_list.getItems().addAll(pids);
            PrisonerFN_list.getItems().addAll(fnlist);
            PrisonerLN_list.getItems().addAll(lnlist);
            PrisonerED_list.getItems().addAll(edlist);
            PrisonerRD_list.getItems().addAll(rdlist);
            PrisonerCN_list.getItems().addAll(celnum);
            PrisonerCrime_list.getItems().addAll(crimelist);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
