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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class FactureController implements Initializable {
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
    private TableView<Facture> table;
    @FXML
    private TableColumn<Contrat, Integer> col_numeroFacture;
    @FXML
    private TableColumn<Contrat, LocalDate> col_dateFacture;
    @FXML
    private TableColumn<Contrat, Double> col_montantAPayer;
    @FXML
    private TableColumn<Contrat, Integer> col_idContrat;
    @FXML
    private JFXTextField filterField;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private Label numeroFacture;
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
    private Label prixVehicule;
    @FXML
    private Label montantAPayer;
    @FXML
    private Label dateDepart;
    @FXML
    private Label dateRetour;
    @FXML
    private Label dateFacture;
    @FXML
    private Label dateContrat;
    @FXML
    private Label dateEcheance;
    @FXML
    private Label idFacture;
    @FXML
    private DatePicker dateFactureField;
    @FXML
    private JFXTextField montantAPayerField;

    FactureDAO factureDAO;

    {
        try {
            factureDAO = new FactureDAO(FactureDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

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

    ObservableList<Facture> list = factureDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataContrat();
    }

    private void dataContrat() {
        col_numeroFacture.setCellValueFactory(new PropertyValueFactory<>("NFacture"));
        col_dateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        col_montantAPayer.setCellValueFactory(new PropertyValueFactory<>("MontantAPayer"));
        col_idContrat.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        table.setItems(list);
    }

    public void returnDetail() {
        blur.setEffect(null);
        detailPane.setVisible(false);
        detailPane.toBack();
        list = factureDAO.list();
        dataContrat();
    }

    public void detailContrat() {
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
            list = factureDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la facture à afficher!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            blur.setEffect(new GaussianBlur(10));
            detailPane.setVisible(true);
            detailPane.toFront();
            Facture facture = factureDAO.find(table.getSelectionModel().getSelectedItem().getNFacture());
            dateFacture.setText(String.valueOf(facture.getDateFacture()));
            montantAPayer.setText(String.valueOf(facture.getMontantAPayer()));
            Contrat contrat = contratDAO.find(facture.getIdContrat());
            Réservation reservation = reservationDAO.find(contrat.getIdReservation());
            numeroFacture.setText(String.valueOf(facture.getNFacture()));
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
            prixVehicule.setText(String.valueOf(véhicule.getPrix()));
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
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createFacture.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
    }

    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = factureDAO.list();
        dataContrat();
    }

    public void search() {
        FilteredList<Facture> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(facture -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                String numero = String.valueOf(facture.getNFacture());
                if (numero.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Facture> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void updateContrat() {
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
            list = factureDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la facture à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Facture facture = factureDAO.find(table.getSelectionModel().getSelectedItem().getNFacture());
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            dateFactureField.setValue(facture.getDateFacture());
            montantAPayerField.setText(String.valueOf(facture.getMontantAPayer()));
        }
    }

    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        list = factureDAO.list();
        dataContrat();
    }

    public void modifyContrat() {
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
        Facture facture = null;
        idFacture.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNFacture()));
        facture = new Facture(0, dateFactureField.getValue(), Double.parseDouble(montantAPayerField.getText()), table.getSelectionModel().getSelectedItem().getIdContrat());
        if (factureDAO.update(facture, table.getSelectionModel().getSelectedItem().getNFacture())) {
            dialogContent.setBody(new Text("La facture à été modifié!"));
            dialog.show();
            return;
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
            list = factureDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la facture à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Facture facture = factureDAO.find(table.getSelectionModel().getSelectedItem().getNFacture());
            factureDAO.delete(facture);
            dialogContent.setBody(new Text("La facture a été supprimé!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
    }
}