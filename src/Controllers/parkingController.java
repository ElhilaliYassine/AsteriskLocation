package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.ClientDAO;
import models.DAO.ParkingDAO;
import models.Parking;
import models.Véhicule;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class parkingController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane rootPane1,rootPane2;
    @FXML
    private Button btnClose1,btnClose2;
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
    @FXML
    private TableView<Véhicule> tableVehicule;

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
    private TableColumn<Véhicule, Boolean> col_disponibilite;

    @FXML
    private Label numeroParking,nombreVehicule;
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
    private void dataVehicule(int i)
    {
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("NImmatriculation"));
        col_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_carburant.setCellValueFactory(new PropertyValueFactory<>("carburant"));
        col_KM.setCellValueFactory(new PropertyValueFactory<>("compteurKm"));
        col_dateMise.setCellValueFactory(new PropertyValueFactory<>("dateMiseEnCirculation"));
        col_disponibilite.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        ObservableList<Véhicule> vehiculeParking =  parkingDAO.vehiculeParking(i);
        tableVehicule.setItems(vehiculeParking);
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
    public void returnDetail() {
        blur.setEffect(null);
        rootPane2.setVisible(false);
        rootPane2.toBack();
        list = parkingDAO.list();
        dataUser();
    }
    public void detailParking(){
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        msgPane.toFront();
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            blur.setEffect(null);
            list = parkingDAO.list();
            dataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le parking à afficher!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else{
            blur.setEffect(new GaussianBlur(10));
            rootPane2.setVisible(true);
            rootPane2.toFront();
            numeroParking.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNParking()));
            dataVehicule(table.getSelectionModel().getSelectedItem().getNParking());
            nombreVehicule.setText(String.valueOf(parkingDAO.nombreVehicule(table.getSelectionModel().getSelectedItem().getNParking())));
        }
    }

}