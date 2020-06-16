package Controllers;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Contrat;
import models.DAO.ContratDAO;
import models.DAO.FactureDAO;
import models.DAO.RéservationDAO;
import models.DAO.VéhiculeDAO;
import models.Facture;
import models.Réservation;
import models.Véhicule;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateFactureController implements Initializable {
    @FXML
    private StackPane myStackPane;
    @FXML
    private DatePicker dateFactureField;
    @FXML
    private JFXComboBox<Integer> selectContrat;
    ContratDAO contratDAO;
    {
        try {
            contratDAO = new ContratDAO(ContratDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

    FactureDAO factureDAO;
    {
        try {
            factureDAO = new FactureDAO(FactureDAO.connect);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
    VéhiculeDAO véhiculeDAO;
    {
        try {
            véhiculeDAO = new VéhiculeDAO(VéhiculeDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }


    ObservableList<Integer> listContrats = factureDAO.listStrings();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectContrat.setItems(listContrats);
    }

    //Vider Les inputs
    @FXML
    public void clear() {
        selectContrat.setItems(listContrats);
        dateFactureField.setValue(null);
    }

    //Ajouter une nouvelle facture
    @FXML
    public void newFacture() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        myStackPane.toFront();
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            clear();
        });
        if (dateFactureField.getValue() == null || selectContrat.getValue() == null) {
            dialogContent.setBody(new Text("facture invalide!"));
            dialog.show();
            return;
        } else if (factureDAO.containsContratId(selectContrat.getValue())) {
            dialogContent.setBody(new Text("Réservation déja transformé \nà une facture!"));
            dialog.show();
            return;
        } else {
            Contrat contrat = contratDAO.find(selectContrat.getSelectionModel().getSelectedItem());
            Facture facture = new Facture(0, dateFactureField.getValue(), montantAPayer(contrat.getNContrat()), contrat.getNContrat());
            if (factureDAO.create(facture)) {
                dialogContent.setBody(new Text("La facture a été enregistré"));
            } else {
                dialogContent.setBody(new Text("La facture n'a pas été enregistré"));
            }
            dialog.show();
            return;
        }
    }

    //calculer le nombre de jours le client a reservé le vehicule
    private double getNbrJours(int idContrat) {
        Contrat contrat = contratDAO.find(idContrat);
        Réservation reservation = reservationDAO.find(contrat.getIdReservation());
        LocalDate dateDepart = reservation.getDateDépart();
        LocalDate dateRetour = reservation.getDateRetour();
        int diff = dateDepart.getDayOfYear()-dateRetour.getDayOfYear() ;
        return Math.abs(diff);
    }

    //Calculer le montant à payer prixVehicule * nombre de jours
    private double montantAPayer(int idContrat) {
        Contrat contrat = contratDAO.find(idContrat);
        Réservation reservation = reservationDAO.find(contrat.getIdReservation());
        Véhicule véhicule = véhiculeDAO.find(reservation.getIdVehicule());
        return véhicule.getPrix()*getNbrJours(contrat.getNContrat());
    }
}
