package Controllers;

import com.jfoenix.controls.*;
import com.mysql.jdbc.Connection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.DAO.UtilisateurDAO;
import models.Utilisateur;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static models.DAO.DAO.connect;

public class createUserController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField adresseField;

    @FXML
    private JFXTextField telephoneField;

    @FXML
    private StackPane myStackPane;

    UtilisateurDAO utilisateurDAO;

    {
        try {
            utilisateurDAO = new UtilisateurDAO(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void nouveauUtilisateur() throws SQLException {

        String title = "Asterisk Location - Message :" ;


        JFXDialogLayout dialogContent = new JFXDialogLayout();

        dialogContent.setHeading(new Text(title));

        JFXButton close = new JFXButton("Close");

        close.setButtonType(JFXButton.ButtonType.RAISED);

        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);

        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");


      close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent __) {
                dialog.close();
                clear();
            }
        });
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || adresseField.getText().isEmpty() ||telephoneField.getText().isEmpty() ||emailField.getText().isEmpty())
        {
            dialogContent.setBody(new Text("Utilisateur invalide !"));
            dialog.show();
            return;

        }
        Utilisateur checker = utilisateurDAO.find(usernameField.getText());
        if(checker.getCodeUtilisateur()!= 0) {
            dialogContent.setBody(new Text("Utilisateur déjà enregistré"));
            dialog.show();
            return;

        }else
        {
            Utilisateur user = new Utilisateur(0,usernameField.getText(),adresseField.getText(),Integer.parseInt(telephoneField.getText()),"",passwordField.getText(),emailField.getText());
            utilisateurDAO.create(user);
            dialogContent.setBody(new Text("Utilisateur est enregistré"));
            dialog.show();
            return;

        }
    }
    @FXML
    public void clear()
    {
         usernameField.setText("");
         passwordField.setText("");
         emailField.setText("");
         adresseField.setText("");
         telephoneField.setText("");

    }

}
