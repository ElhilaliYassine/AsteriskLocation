package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.DAO.ParkingDAO;
import models.DAO.VéhiculeDAO;
import models.Parking;
import models.Véhicule;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class vehiculeController implements Initializable {
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
    private TableView<Véhicule> table;

    @FXML
    private TableColumn<Véhicule, Integer> col_matricule;

    @FXML
    private TableColumn<Véhicule, String> col_marque;

    @FXML
    private TableColumn<Véhicule, String> col_type;

    @FXML
    private TableColumn<Véhicule, String> col_carburant;

    @FXML
    private TableColumn<Véhicule, Double> col_KM;

    @FXML
    private TableColumn<Véhicule, LocalDate> col_dateMise;

    @FXML
    private TableColumn<Véhicule, Integer> col_Parking;

    @FXML
    private TableColumn<Véhicule, Boolean> col_disponibilite;
    VéhiculeDAO véhiculeDAO;

    {
        try {
            véhiculeDAO = new VéhiculeDAO(VéhiculeDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

    ObservableList<Véhicule> list = véhiculeDAO.list();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            dataUser();
    }
    private void dataUser() {
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("NImmatriculation"));
        col_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_carburant.setCellValueFactory(new PropertyValueFactory<>("carburant"));
        col_KM.setCellValueFactory(new PropertyValueFactory<>("compteurKm"));
        col_dateMise.setCellValueFactory(new PropertyValueFactory<>("dateMiseEnCirculation"));
        col_Parking.setCellValueFactory(new PropertyValueFactory<>("idParking"));
        col_disponibilite.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        table.setItems(list);
    }
    public void search() {
        FilteredList<Véhicule> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(véhicule -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (véhicule.getMarque().toLowerCase().contains(lowerCaseFilter)) return true;
                if (véhicule.getType().toLowerCase().contains(lowerCaseFilter)) return true;
                String matricule = String.valueOf(véhicule.getNImmatriculation());
                if (matricule.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Véhicule> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    public void createVehicule() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createVehicule.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
        btnClose.setVisible(true);
        btnClose.toFront();
    }
    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = véhiculeDAO.list();
        dataUser();
    }

}