package Controllers;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.*;
import models.Parking;
import models.Réservation;
import models.Véhicule;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

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
    private Rectangle rectangleAnnuler,rectangleValidee,rectangleNonValide,rectangleTous;

    @FXML
    private AnchorPane updatePane;

    @FXML
    private AnchorPane blur;

    @FXML
    private TableView<Réservation> table;

    @FXML
    private TableColumn<Réservation, Integer> col_codeReservation;

    @FXML
    private TableColumn<Réservation, LocalDate>  col_dateDepart;

    @FXML
    private TableColumn<Réservation, LocalDate>  col_dateRetour;

    @FXML
    private TableColumn<Réservation, Integer>  col_idClient;

    @FXML
    private TableColumn<Réservation, Integer> col_idVehicule;
    @FXML
    private TableColumn<Réservation, LocalDate> col_dateReservation;

    @FXML
    private JFXTextField filterField;
    @FXML
    private AnchorPane detailPane;
    @FXML
    private Label numeroReservation;
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
    private Label dateRetour,etatReservation;
    @FXML
    private Label dateReservation;
    @FXML
    private DatePicker dateDepartField;

    @FXML
    private DatePicker dateReservationField;
    @FXML
    private DatePicker dateRetourField;
    @FXML
    private JFXComboBox<String> selectVehicule;

    @FXML
    private JFXComboBox<String> selectClient;

    @FXML
    private JFXComboBox<String> selectEtat;

    @FXML
    private Label idReservation;

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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    static int vehiculeMatricule;
    ObservableList<String> listVehicule = véhiculeDAO.select();
    ObservableList<String> listClient = clientDAO.select();
    ObservableList<String> listEtat = select();
    ObservableList<Integer> listMatricule = véhiculeDAO.selectMatricule();

    ObservableList<Réservation> list = reservationDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataReservation();
        reservationlist();
        selectClient.setItems(listClient);
        selectVehicule.setItems(listVehicule);
        selectEtat.setItems(listEtat);
    }
    //Creer une comboBox contenant les differents états d'une réservation
    public ObservableList<String> select() {
        ObservableList<String> listEtat = FXCollections.observableArrayList();
        listEtat.add("validé");
        listEtat.add("non validé");
        listEtat.add("annuler");
        return listEtat;
    }
    //Afficher la base donnée Réservation
    private void dataReservation()
    {
        col_codeReservation.setCellValueFactory(new PropertyValueFactory<>("codeRéservation"));
        col_dateDepart.setCellValueFactory(new PropertyValueFactory<>("dateDépart"));
        col_dateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
        col_idClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        col_idVehicule.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
        col_dateReservation.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        table.setItems(list);
    }
    //Fermer la pane détail réservation
    public void returnDetail() {
        blur.setEffect(null);
        detailPane.setVisible(false);
        detailPane.toBack();
        list = reservationDAO.list();
        dataReservation();

    }
    //Afficher les détails de la réservation sélectionnée
    public void detailReservation(){
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
            list = reservationDAO.list();
            dataReservation();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la réservation à afficher!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else{
            blur.setEffect(new GaussianBlur(10));
            detailPane.setVisible(true);
            detailPane.toFront();
            Réservation reservation = reservationDAO.find(table.getSelectionModel().getSelectedItem().getCodeRéservation());
            numeroReservation.setText(String.valueOf(reservation.getCodeRéservation()));
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
            dateReservation.setText(String.valueOf(reservation.getDateReservation()));
            etatReservation.setText(reservation.getEtatReservation());
            if(reservation.getEtatReservation().equals("annuler"))
            {
                etatReservation.setText("Reservation annulée");
                etatReservation.setStyle("-fx-text-fill :  #e73535");
            }else if(reservation.getEtatReservation().equals("validé"))
            {
                etatReservation.setText("Reservation validée");
                etatReservation.setStyle("-fx-text-fill : green");
            }else if(reservation.getEtatReservation().equals("non validé"))
            {
                etatReservation.setText("Reservation non validée");
                etatReservation.setStyle("-fx-text-fill :  #f2a51a");
            }
        }
    }
    //Ouvrir la vue de la création d'une nouvelle réservation
    public void createReservation() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createReservation.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
    }
    //Fermer la vue Creation d'une réservation
    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = reservationDAO.list();
        dataReservation();
    }
    //Chercher une réservation par son code
    public void search() {
        FilteredList<Réservation> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(réservation -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                String codeReservation = String.valueOf(réservation.getCodeRéservation());
                if (codeReservation.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Réservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    //Table des réservation annuler
    public void reservationAnnule()
    {
        rectangleAnnuler.setVisible(true);
        rectangleValidee.setVisible(false);
        rectangleNonValide.setVisible(false);
        rectangleTous.setVisible(false);
        list = reservationDAO.listReservation("annuler");
        dataReservation();

    }
    //Table des réservations validées
    public void reservationValide()
    {
        rectangleValidee.setVisible(true);
        rectangleNonValide.setVisible(false);
        rectangleTous.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.listReservation("validé");
        dataReservation();
    }
    //TAble des réservations non validées
    public void reservationNonValide()
    {
        rectangleNonValide.setVisible(true);
        rectangleTous.setVisible(false);
        rectangleValidee.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.listReservation("non validé");
        dataReservation();
    }
    //Table de toutes les réservations
    public void reservationlist()
    {
        rectangleTous.setVisible(true);
        rectangleNonValide.setVisible(false);
        rectangleValidee.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.list();
        dataReservation();
    }
    //Afficher la pane update afin de modifier la réservation sélectionner
    public void updateReservation()
    {
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
            list = reservationDAO.list();
            dataReservation();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la réservation à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Réservation reservation = reservationDAO.find(table.getSelectionModel().getSelectedItem().getCodeRéservation());
            idReservation.setText(String.valueOf(reservation.getCodeRéservation()));
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            dateReservationField.setValue(reservation.getDateReservation());
            dateDepartField.setValue(reservation.getDateDépart());
            dateRetourField.setValue(reservation.getDateRetour());
            Véhicule vehicule = véhiculeDAO.find(reservation.getIdVehicule());
            selectVehicule.setValue(vehicule.getMarque()+" - "+vehicule.getType());
            Client client = clientDAO.find(reservation.getIdClient());
            selectClient.setValue(client.getNomComplet());
            selectEtat.setValue(reservation.getEtatReservation());
            vehiculeMatricule = vehicule.getNImmatriculation();
        }

    }
    //fermer update pane
    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        list = reservationDAO.list();
        dataReservation();
    }
    //Modifier Réservation
    public void modifyReservation()
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
        });
        Réservation reservation = null;
        Client client = clientDAO.find(selectClient.getValue());
        Véhicule vehicule = véhiculeDAO.find(listMatricule.get(selectVehicule.getSelectionModel().getSelectedIndex()));
        if(vehicule.getNImmatriculation()==vehiculeMatricule)
        {
            if(dateDepartField.getValue().compareTo(dateRetourField.getValue()) > 0) {
                dialogContent.setBody(new Text("Veuillez vérifier les dates"));
                dialog.show();
                return;
            }
            reservation = new Réservation(0,dateReservationField.getValue(),dateDepartField.getValue(),dateRetourField.getValue(),client.getCodeClient(),vehicule.getNImmatriculation(),selectEtat.getValue());
            if (reservationDAO.update(reservation, table.getSelectionModel().getSelectedItem().getCodeRéservation())) {
                vehiculeMatricule = reservation.getIdVehicule();
                dialogContent.setBody(new Text("La Réservation a été modifié!"));
                dialog.show();
                return;
            }else{
                dialogContent.setBody(new Text("La Réservation n'a pas été modifié!"));
                dialog.show();
                return;
            }
        }
        if(!vehicule.isDisponibilite())
        {
            dialogContent.setBody(new Text("Le véhicule n'est pas disponible!"));
            dialog.show();
            return;
        }
        if(dateDepartField.getValue().compareTo(dateRetourField.getValue()) > 0) {
            dialogContent.setBody(new Text("Veuillez vérifier les dates"));
            dialog.show();
            return;
        }else {
            reservation = new Réservation(0,dateReservationField.getValue(),dateDepartField.getValue(),dateRetourField.getValue(),client.getCodeClient(),vehicule.getNImmatriculation(),selectEtat.getValue());
            if (reservationDAO.update(reservation, table.getSelectionModel().getSelectedItem().getCodeRéservation()))
            {
                //rendre l'ancien vehicule sa disponibilité à true
                Véhicule vehiculeUpdated = véhiculeDAO.find(vehiculeMatricule);
                vehiculeUpdated.setDisponibilite(true);
                véhiculeDAO.update(vehiculeUpdated,vehiculeUpdated.getNImmatriculation());
                //rendre le nouveau vehicule utilisé sa disponiblité en true
                vehiculeMatricule = reservation.getIdVehicule();
                vehiculeUpdated = véhiculeDAO.find(vehiculeMatricule);
                vehiculeUpdated.setDisponibilite(false);
                véhiculeDAO.update(vehiculeUpdated,vehiculeUpdated.getNImmatriculation());
                dialogContent.setBody(new Text("La Réservation a été modifié!"));
                dialog.show();
                return;
            }
        }
    }
    //Supprimer Réservation
    public void deleteReservation() {
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
            list = reservationDAO.list();
            dataReservation();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le véhicule à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else {
            Réservation reservation = reservationDAO.find(table.getSelectionModel().getSelectedItem().getCodeRéservation());
            if(contratDAO.containsReservationId(reservation.getCodeRéservation()))
            {
                dialogContent.setBody(new Text("Veuillez supprimer le contrat de la réservation sélectionnée!"));
            }else{
                reservationDAO.delete(reservation);
                Véhicule vehiculeDeleted = véhiculeDAO.find(reservation.getIdVehicule());
                vehiculeDeleted.setDisponibilite(true);
                véhiculeDAO.update(vehiculeDeleted,vehiculeDeleted.getNImmatriculation());
                dialogContent.setBody(new Text("La Réservation a été supprimé!"));
            }
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;

        }
    }


}