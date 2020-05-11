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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.ClientDAO;
import models.DAO.ParkingDAO;
import models.DAO.RéservationDAO;
import models.DAO.VéhiculeDAO;
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
    private AnchorPane rootPane;

    @FXML
    private Button btnClose;

    @FXML
    private AnchorPane loadPane;

    @FXML
    private Rectangle rectangleAnnuler,rectangleValidee,rectangleNonValide,rectangleTous;

    @FXML
    private AnchorPane rootPane1;

    @FXML
    private Button btnClose1;

    @FXML
    private StackPane myStackPane1;

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
    private Button btnClose2;
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

    ObservableList<Réservation> list = reservationDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataReservation();

    }
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
    public void returnDetail() {
        blur.setEffect(null);
        detailPane.setVisible(false);
        detailPane.toBack();
        list = reservationDAO.list();
        dataReservation();

    }
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
    public void createReservation() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createReservation.fxml"));
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
        list = reservationDAO.list();
        dataReservation();
    }
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
    public void reservationAnnule()
    {
        rectangleAnnuler.setVisible(true);
        rectangleValidee.setVisible(false);
        rectangleNonValide.setVisible(false);
        rectangleTous.setVisible(false);
        list = reservationDAO.listReservation("annuler");
        dataReservation();

    }
    public void reservationValide()
    {
        rectangleValidee.setVisible(true);
        rectangleNonValide.setVisible(false);
        rectangleTous.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.listReservation("validé");
        dataReservation();
    }
    public void reservationNonValide()
    {
        rectangleNonValide.setVisible(true);
        rectangleTous.setVisible(false);
        rectangleValidee.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.listReservation("non validé");
        dataReservation();
    }
    public void reservationlist()
    {
        rectangleTous.setVisible(true);
        rectangleNonValide.setVisible(false);
        rectangleValidee.setVisible(false);
        rectangleAnnuler.setVisible(false);
        list = reservationDAO.list();
        dataReservation();
    }

}