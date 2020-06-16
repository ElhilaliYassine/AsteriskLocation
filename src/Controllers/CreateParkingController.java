package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.DAO.ParkingDAO;
import models.Parking;

public class CreateParkingController implements Initializable {
    @FXML
    private JFXTextField capaciteField;

    @FXML
    private JFXTextField rueField;

    @FXML
    private JFXTextField arrondissementField;

    @FXML
    private StackPane myStackPane;
    ParkingDAO parkingDAO;

    {
        try {
            parkingDAO = new ParkingDAO(ParkingDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //Vider les inputs
    @FXML
    void clear() {
        capaciteField.setText("");
        rueField.setText("");
        arrondissementField.setText("");
    }

    //Creation d'un nouveau parking
    @FXML
    void nouveauParking() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            clear();
        });
        if (capaciteField.getText().isEmpty() || rueField.getText().isEmpty() || arrondissementField.getText().isEmpty()) {
            dialogContent.setBody(new Text("Parking invalide!"));
            dialog.show();
            return;
        }
        Parking checker = parkingDAO.find(rueField.getText());
        if (checker.getNParking() != 0) {
            dialogContent.setBody(new Text("Le parking déjà enregistré!"));
        } else {
            try{
                Parking parking = new Parking(0, Integer.parseInt(capaciteField.getText()), rueField.getText(), arrondissementField.getText(), 0);
                if(parkingDAO.create(parking))
                    dialogContent.setBody(new Text("Le parking a été enregistré"));
                else
                    dialogContent.setBody(new Text("Le parking n'a pas été enregistré"));

            }catch (NumberFormatException e)
            {
                dialogContent.setBody(new Text("Veuillez vérifier les champs saisis!"));
            }
        }
        dialog.show();
        return;

    }
}
