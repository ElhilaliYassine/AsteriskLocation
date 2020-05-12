package Controllers;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.DAO.ParkingDAO;
import models.DAO.VéhiculeDAO;
import models.Parking;
import models.Véhicule;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VehiculeController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane updatePane;
    @FXML
    private StackPane myStackUpdate;
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

    @FXML
    private JFXTextField matriculeField;

    @FXML
    private JFXTextField marqueField;

    @FXML
    private JFXTextField typeField;

    @FXML
    private JFXTextField carburantField;

    @FXML
    private JFXTextField compteurKmField;

    @FXML
    private DatePicker dateField;

    @FXML
    private JFXRadioButton ouiRadio;

    @FXML
    private JFXRadioButton nonRadio;
    @FXML
    private JFXComboBox<String> selectParking;
    static int nbrParking;
    final ToggleGroup group = new ToggleGroup();
    VéhiculeDAO véhiculeDAO;

    {
        try {
            véhiculeDAO = new VéhiculeDAO(VéhiculeDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
    ParkingDAO parkingDAO;

    {
        try {
            parkingDAO = new ParkingDAO(ParkingDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
    ObservableList<String> select = parkingDAO.select();

    ObservableList<Véhicule> list = véhiculeDAO.list();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            dataUser();
            ouiRadio.setToggleGroup(group);
            nonRadio.setToggleGroup(group);
            selectParking.setItems(select);
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
    }
    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = véhiculeDAO.list();
        dataUser();
    }
    public void updateVehicule() {
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
            list = véhiculeDAO.list();
            dataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le véhicule à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Véhicule véhicule = véhiculeDAO.find(table.getSelectionModel().getSelectedItem().getNImmatriculation());
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            matriculeField.setText(String.valueOf(véhicule.getNImmatriculation()));
            marqueField.setText(véhicule.getMarque());
            typeField.setText(véhicule.getType());
            if(véhicule.isDisponibilite())
                group.selectToggle(ouiRadio);
            else
                group.selectToggle(nonRadio);
            dateField.setValue(véhicule.getDateMiseEnCirculation());
            //String for better Ux
            Parking parking = parkingDAO.find(véhicule.getIdParking());
            selectParking.setValue(parking.getRue());
            carburantField.setText(véhicule.getCarburant());
            compteurKmField.setText(String.valueOf(véhicule.getCompteurKm()));
            nbrParking = véhicule.getIdParking();

        }
    }
    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        list = véhiculeDAO.list();
        dataUser();
    }
    public void modifyVehicule() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackUpdate, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        myStackUpdate.toFront();
        close.setOnAction(e -> {
            dialog.close();
        });
        Véhicule véhicule = null;
        Parking parking = parkingDAO.find(selectParking.getValue());
        if(nbrParking == parking.getNParking())
        {
            if(group.getSelectedToggle()==ouiRadio)
            {
                véhicule = new Véhicule(0, marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), true);
            } else{
                véhicule = new Véhicule(0, marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), false);
            }
            if (véhiculeDAO.update(véhicule, table.getSelectionModel().getSelectedItem().getNImmatriculation())) {
                dialogContent.setBody(new Text("Le véhicule à été modifié!"));
                dialog.show();
                return;
            }
        }
        int nombreVehicule = parkingDAO.nombreVehicule(parking.getNParking());
        if(parking.getCapacité()-nombreVehicule>0){
            if(group.getSelectedToggle()==ouiRadio)
            {
                véhicule = new Véhicule(0, marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), true);
            } else{
                véhicule = new Véhicule(0, marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), false);
            }
            if (véhiculeDAO.update(véhicule, table.getSelectionModel().getSelectedItem().getNImmatriculation())) {
                dialogContent.setBody(new Text("Le véhicule à été modifié!"));
                dialog.show();
                return;
            }
        }else if(parking.getCapacité()-nombreVehicule<=0)
        {
            dialogContent.setBody(new Text("Le parking est saturé"));
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
            list = véhiculeDAO.list();
            dataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le véhicule à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else {
            Véhicule vehicule = véhiculeDAO.find(table.getSelectionModel().getSelectedItem().getNImmatriculation());
            if(vehicule.isDisponibilite())
            {
                véhiculeDAO.delete(vehicule);
                dialogContent.setBody(new Text("Le véhicule a été supprimé!"));
            }else{
                dialogContent.setBody(new Text("Veuillez vérifier la Réservation du véhicule!"));
            }
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
    }


}