package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.Client;
import models.DAO.ClientDAO;
import models.DAO.ParkingDAO;
import models.Parking;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class parkingController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane rootPane1;
    @FXML
    private Button btnClose1;
    @FXML
    private JFXTextField nomCompletField;
    @FXML
    private JFXTextField adresseField;

    @FXML
    private JFXTextField numGsmField;
    @FXML
    private StackPane myStackPane1;
    @FXML
    private AnchorPane blur;
    @FXML
    private JFXTextField filterField;
    @FXML
    private Pane msgPane;
    @FXML
    private StackPane myStackPane;

    @FXML
    private TableView<Parking> table;

    @FXML
    private TableColumn<Parking, Integer> col_Nparking;

    @FXML
    private TableColumn<Parking, Integer> col_capacité;

    @FXML
    private TableColumn<Parking, String> col_Rue;

    @FXML
    private TableColumn<Parking, String> col_Arondissement;

    @FXML
    private TableColumn<Parking, Integer> col_nbrplacesOccupées;
    ParkingDAO parkingDAO;

    {
        try {
            parkingDAO = new ParkingDAO(ParkingDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

    ObservableList<Parking> list = parkingDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataUser();

    }
    private void dataUser() {
        col_Nparking.setCellValueFactory(new PropertyValueFactory<>("NParking"));
        col_capacité.setCellValueFactory(new PropertyValueFactory<>("capacité"));
        col_Rue.setCellValueFactory(new PropertyValueFactory<>("rue"));
        col_Arondissement.setCellValueFactory(new PropertyValueFactory<>("arrondissement"));
        col_nbrplacesOccupées.setCellValueFactory(new PropertyValueFactory<>("nbrPlacesOccupées"));
        table.setItems(list);
    }
    public void search() {
        FilteredList<Parking> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(parking -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (parking.getRue().toLowerCase().contains(lowerCaseFilter)) return true;
                if (parking.getArrondissement().toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Parking> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

}