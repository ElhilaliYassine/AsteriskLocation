package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ParkingController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane updatePane,detailPane;
    @FXML
    private AnchorPane blur;
    @FXML
    private JFXTextField filterField;
    @FXML
    private Pane msgPane;
    @FXML
    private StackPane myStackPane,myStackPane1;

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
    private Label numeroParking,nombreVehicule,nombrePlace;
    @FXML
    private JFXTextField capaciteField;

    @FXML
    private JFXTextField rueField;

    @FXML
    private JFXTextField arrondissementField;
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

        for(int i=0;i<list.size();i++)
        {
            Parking selected = list.get(i);
            Parking parking = parkingDAO.find(selected.getNParking());
            parking.setNbrPlacesOccupées(parkingDAO.nombreVehicule(selected.getNParking()));
            parkingDAO.update(parking,selected.getNParking());
        }
        list = parkingDAO.list();
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
        detailPane.setVisible(false);
        detailPane.toBack();
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
            detailPane.setVisible(true);
            detailPane.toFront();
            Parking parking = parkingDAO.find(table.getSelectionModel().getSelectedItem().getNParking());
            numeroParking.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNParking()));
            dataVehicule(table.getSelectionModel().getSelectedItem().getNParking());
            nombreVehicule.setText(String.valueOf(parkingDAO.nombreVehicule(table.getSelectionModel().getSelectedItem().getNParking())));
            nombrePlace.setText(String.valueOf(parking.getCapacité()-parkingDAO.nombreVehicule(parking.getNParking())));

        }
    }
    public void createParking() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createParking.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
    }
    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        dataUser();
    }
    public void updateParking() {
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
            dataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le parking à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Parking parking = parkingDAO.find(table.getSelectionModel().getSelectedItem().getNParking());
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            capaciteField.setText(String.valueOf(parking.getCapacité()));
            rueField.setText(parking.getRue());
            arrondissementField.setText(parking.getArrondissement());

        }
    }
    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        dataUser();
    }
    public void modifyParking() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane1, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        myStackPane1.toFront();
        close.setOnAction(e -> {
            dialog.close();
        });
        Parking parking = new Parking(0,Integer.parseInt(capaciteField.getText()),rueField.getText(),arrondissementField.getText(),0);

        if (parkingDAO.update(parking, table.getSelectionModel().getSelectedItem().getNParking())) {
            dialogContent.setBody(new Text("Le parking à été modifié!"));
            dialog.show();
            return;
        }
    }
    public void deleteVehicule() {
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
            dataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le véhicule à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else {
            Parking parking = parkingDAO.find(table.getSelectionModel().getSelectedItem().getNParking());
            if(parkingDAO.nombreVehicule(parking.getNParking())==0)
            {
                parkingDAO.delete(parking);
                dialogContent.setBody(new Text("Le parking a été supprimé!"));
            }else{
                dialogContent.setBody(new Text("\nVeuillez déplacer les véhicules avant de supprimer\nle parking !"));

            }
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;

        }
    }

}