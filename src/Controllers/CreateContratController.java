package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.*;
import models.DAO.ContratDAO;
import models.DAO.RéservationDAO;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateContratController implements Initializable {
    @FXML
    private StackPane myStackPane;
    @FXML
    private DatePicker dateEcheanceField;
    @FXML
    private DatePicker dateContratField;
    @FXML
    private JFXComboBox<String> selectReservation;
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
    ObservableList<String> listReservations = reservationDAO.selectValableReservations();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectReservation.setItems(listReservations);
    }
    @FXML
    public void clear() {
        selectReservation.setItems(listReservations);
        dateContratField.setValue(null);
        dateEcheanceField.setValue(null);
    }
    @FXML
    public void newContrat() {
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
        if (dateContratField.getValue()== null || dateEcheanceField.getValue()== null || selectReservation.getValue()==null) {
            dialogContent.setBody(new Text("contrat invalide!"));
            dialog.show();
            return;
        }else if(dateContratField.getValue().compareTo(dateEcheanceField.getValue()) > 0) {
            dialogContent.setBody(new Text("Veuillez vérifier les dates"));
            dialog.show();
            return;
        }else if(contratDAO.containsReservationId(Integer.parseInt(selectReservation.getValue())))
        {
            dialogContent.setBody(new Text("Réservation déja transformé à un contrat!"));
            dialog.show();
            return;
        }
        else{
            Contrat contrat = new Contrat(0,dateContratField.getValue(),dateEcheanceField.getValue(),Integer.parseInt(selectReservation.getValue()));
            if(contratDAO.create(contrat))
            {
                dialogContent.setBody(new Text("Le contrat a été enregistré"));
            }else{
                dialogContent.setBody(new Text("Le contrat n'a pas été enregistré"));
            }
            dialog.show();
            return;
        }
    }

}
