package Controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.ClientDAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static models.DAO.DAO.connect;

public class CreateClientController implements Initializable {
    @FXML
    private JFXTextField nomCompletField;
    @FXML
    private JFXTextField adresseField;
    @FXML
    private JFXTextField numGsmField;
    @FXML
    private StackPane myStackPane;
    ClientDAO clientDAO;
    {
        try {
            clientDAO = new ClientDAO(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    //Creation d'un nouveau Client
    @FXML
    public void newClient() {
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
        if (nomCompletField.getText().isEmpty() || adresseField.getText().isEmpty() || numGsmField.getText().isEmpty()) {
            dialogContent.setBody(new Text("Client invalide!"));
            dialog.show();
            return;
        }
        Client checker = clientDAO.find(nomCompletField.getText());
        if (checker.getCodeClient() != 0) {
            dialogContent.setBody(new Text("Le client déjà enregistré!"));
        } else {
            Client client=null;
            try {
                client = new Client(0, nomCompletField.getText(), adresseField.getText(), Integer.parseInt(numGsmField.getText()));
            } catch (NumberFormatException e) {
                dialogContent.setBody(new Text("Veuillez vérifier les champs saisis"));
                dialog.show();
                return;
            }
            clientDAO.create(client);
            dialogContent.setBody(new Text("Le client a été enregistré"));
        }
        dialog.show();
        return;
    }

    //methode pour vider les inputs
    @FXML
    public void clear() {
        nomCompletField.setText("");
        numGsmField.setText("");
        adresseField.setText("");
    }
}