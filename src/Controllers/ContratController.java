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
import models.*;
import models.DAO.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ContratController implements Initializable {
    @FXML
    private Pane msgPane;
    @FXML
    private StackPane myStackPane;
    @FXML
    private StackPane myStackUpdate;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane updatePane;
    @FXML
    private AnchorPane blur;
    @FXML
    private TableView<Contrat> table;
    @FXML
    private TableColumn<Contrat, Integer> col_numeroContrat;
    @FXML
    private TableColumn<Contrat, LocalDate>  col_dateContrat;
    @FXML
    private TableColumn<Contrat, LocalDate>  col_dateEcheance;
    @FXML
    private TableColumn<Contrat, Integer>  col_idReservation;
    @FXML
    private JFXTextField filterField;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private Label numeroContrat;
    @FXML
    private Label nomClient;
    @FXML
    private Label adresseClient;
    @FXML
    private Label idClient;
    @FXML
    private Label numeroClient;
    @FXML
    private Label matriculeVehicule;
    @FXML
    private Label marqueVehicule;
    @FXML
    private Label typeVehicule;
    @FXML
    private Label dateVehicule;
    @FXML
    private Label compteurVehicule;
    @FXML
    private Label carburantVehicule;
    @FXML
    private Label parkingVehicule;
    @FXML
    private Label dateDepart;
    @FXML
    private Label dateRetour;
    @FXML
    private Label dateContrat;
    @FXML
    private Label dateEcheance;
    @FXML
    private Label idContrat;
    @FXML
    private DatePicker dateEcheanceField;
    @FXML
    private DatePicker dateContratField;
    ContratDAO contratDAO;
    {
        try {
            contratDAO = new ContratDAO(ContratDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
    RéservationDAO reservationDAO;
    {
        try {
            reservationDAO = new RéservationDAO(RéservationDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
    ClientDAO clientDAO;
    {
        try {
            clientDAO = new ClientDAO(ClientDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
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
    ObservableList<Contrat> list = contratDAO.list();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataContrat();
    }
    private void dataContrat()
    {
        col_numeroContrat.setCellValueFactory(new PropertyValueFactory<>("NContrat"));
        col_dateContrat.setCellValueFactory(new PropertyValueFactory<>("dateContrat"));
        col_dateEcheance.setCellValueFactory(new PropertyValueFactory<>("dateEchéance"));
        col_idReservation.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        table.setItems(list);
    }
    public void returnDetail() {
        blur.setEffect(null);
        detailPane.setVisible(false);
        detailPane.toBack();
        list = contratDAO.list();
        dataContrat();
    }
    public void detailContrat(){
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
            list = contratDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le contrat à afficher!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else{
            blur.setEffect(new GaussianBlur(10));
            detailPane.setVisible(true);
            detailPane.toFront();
            Contrat contrat = contratDAO.find(table.getSelectionModel().getSelectedItem().getNContrat());
            Réservation reservation = reservationDAO.find(contrat.getIdReservation());
            numeroContrat.setText(String.valueOf(contrat.getNContrat()));
            Client client = clientDAO.find(reservation.getIdClient());
            idClient.setText(String.valueOf(client.getCodeClient()));
            nomClient.setText(client.getNomComplet());
            adresseClient.setText(client.getAdresse());
            numeroClient.setText(String.valueOf(client.getNumGsm()));
            Véhicule véhicule = véhiculeDAO.find(reservation.getIdVehicule());
            matriculeVehicule.setText(String.valueOf(véhicule.getNImmatriculation()));
            marqueVehicule.setText(véhicule.getMarque());
            typeVehicule.setText(véhicule.getType());
            carburantVehicule.setText(véhicule.getCarburant());
            compteurVehicule.setText(String.valueOf(véhicule.getCompteurKm()));
            dateVehicule.setText(String.valueOf(véhicule.getDateMiseEnCirculation()));
            Parking parking = parkingDAO.find(véhicule.getIdParking());
            parkingVehicule.setText(parking.getRue());
            dateDepart.setText(String.valueOf(reservation.getDateDépart()));
            dateRetour.setText(String.valueOf(reservation.getDateRetour()));
            dateContrat.setText(String.valueOf(contrat.getDateContrat()));
            dateEcheance.setText(String.valueOf(contrat.getDateEchéance()));
        }
    }
    public void createContrat() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createContrat.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
    }
    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = contratDAO.list();
        dataContrat();
    }
    public void search() {
        FilteredList<Contrat> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contrat -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                String numeroContrat = String.valueOf(contrat.getNContrat());
                if (numeroContrat.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Contrat> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    public void updateContrat()
    {
        //rendre le vehicule false en disponibilité pour ne pas eviter le cas de
        //erreur ( le vehicule n'est pas disponible)
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
            list = contratDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le contrat à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Contrat contrat = contratDAO.find(table.getSelectionModel().getSelectedItem().getNContrat());
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            dateContratField.setValue(contrat.getDateContrat());
            dateEcheanceField.setValue(contrat.getDateEchéance());
        }
    }
    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        list = contratDAO.list();
        dataContrat();
    }
    public void modifyContrat()
    {
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
            dataContrat();
        });
        Contrat contrat = null;
        idContrat.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNContrat()));
        if(dateContratField.getValue().compareTo(dateEcheanceField.getValue()) > 0) {
            dialogContent.setBody(new Text("Veuillez vérifier les dates"));
            dialog.show();
            return;
        }
        else {
            contrat = new Contrat(0,dateContratField.getValue(),dateEcheanceField.getValue(),table.getSelectionModel().getSelectedItem().getIdReservation());
            if (contratDAO.update(contrat, table.getSelectionModel().getSelectedItem().getNContrat()))
            {
                dialogContent.setBody(new Text("Le contrat à été modifié!"));
                dialog.show();
                return;
            }
        }
    }
    public void deleteContrat() {
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
            list = contratDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le contrat à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else {
            Contrat contrat = contratDAO.find(table.getSelectionModel().getSelectedItem().getNContrat());
            contratDAO.delete(contrat);
            dialogContent.setBody(new Text("Le contrat a été supprimé!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
    }


}