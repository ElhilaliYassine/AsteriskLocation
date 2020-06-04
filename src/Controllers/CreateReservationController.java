package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.ClientDAO;
import models.DAO.RéservationDAO;
import models.DAO.VéhiculeDAO;
import models.Parking;
import models.Réservation;
import models.Véhicule;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateReservationController implements Initializable {
    @FXML
    private StackPane myStackPane;
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
    RéservationDAO reservationDAO;
    {
        try {
            reservationDAO = new RéservationDAO(RéservationDAO.connect);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ObservableList<String> listVehicule = véhiculeDAO.select();
    ObservableList<String> listClient = clientDAO.select();
    ObservableList<String> listEtat = select();
    ObservableList<Integer> listMatricule = véhiculeDAO.selectMatricule();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectClient.setItems(listClient);
        selectVehicule.setItems(listVehicule);
        selectEtat.setItems(listEtat);
    }
    public ObservableList<String> select() {
        ObservableList<String> listEtat = FXCollections.observableArrayList();
        listEtat.add("validé");
        listEtat.add("non validé");
        listEtat.add("annuler");
        return listEtat;
    }
    @FXML
    public void clear() {
        selectClient.setItems(listClient);
        selectVehicule.setItems(listVehicule);
        selectEtat.setItems(listEtat);
        dateDepartField.setValue(null);
        dateReservationField.setValue(null);
        dateRetourField.setValue(null);
    }
    @FXML
    public void newReservation() {
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
        if (dateRetourField.getValue()== null || dateDepartField.getValue()== null || dateReservationField.getValue()== null || selectEtat.getValue()==null || selectClient.getValue()==null) {
            dialogContent.setBody(new Text("Réservation invalide!"));
            dialog.show();
            return;
        }
        Client client = clientDAO.find(selectClient.getValue());
        Véhicule vehicule = véhiculeDAO.find(listMatricule.get(selectVehicule.getSelectionModel().getSelectedIndex()));
        if(reservationDAO.checker(client.getCodeClient(),vehicule.getNImmatriculation(),dateReservationField.getValue(),dateDepartField.getValue(),dateRetourField.getValue()))
        {
            dialogContent.setBody(new Text("Réservation déjà enregistrée!"));
            dialog.show();
            return;
        }
        if(!vehicule.isDisponibilite())
        {
            dialogContent.setBody(new Text("La véhicule n'est pas disponible!"));
            dialog.show();
            return;
        }else if(dateDepartField.getValue().compareTo(dateRetourField.getValue()) > 0) {
            dialogContent.setBody(new Text("Veuillez vérifier les dates"));
            dialog.show();
            return;
        }else{
            Réservation reservation = new Réservation(0,dateReservationField.getValue(),dateDepartField.getValue(),dateRetourField.getValue(),client.getCodeClient(),vehicule.getNImmatriculation(),selectEtat.getValue());
            if(reservationDAO.create(reservation))
            {
                dialogContent.setBody(new Text("La réservation a été enregistré"));
                vehicule.setDisponibilite(false);
                véhiculeDAO.update(vehicule,vehicule.getNImmatriculation());
            }else{
                dialogContent.setBody(new Text("La réservation n'a pas été enregistré"));
            }
            dialog.show();
            return;
        }
    }
}
