package Controllers;

import Controllers.interfaces.Window;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DAO.UtilisateurDAO;
import models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static models.DAO.DAO.connect;

public class loginController implements Initializable, Window {

    static JFXTextField username;
    @FXML
    private JFXTextField usernameField;

    @FXML
    private StackPane myStackPane;

    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginButton;
    @FXML
    private AnchorPane fadePane;
    @FXML
    private ProgressBar bar;

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
        fade();
        /*loginButton.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.ENTER) handleButtonLogin();
        });*/
    }

    public void fade()
    {
        /*Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(8), e-> {
                    FadeTransition fadeTransition = new FadeTransition();
                    fadeTransition.setDuration(Duration.millis(1000));
                    fadeTransition.setNode(fadePane);
                    fadeTransition.setFromValue(1);
                    fadeTransition.setToValue(0);
                    fadeTransition.setOnFinished(a-> fadePane.setVisible(false));
                    fadeTransition.play();
                    bar.setVisible(false);

                }, new KeyValue(bar.progressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //fadePane.toFront();
*/
    }
    @FXML
    private void handleButtonLogin() {


        String title = "Asterisk Location - Message :";


        JFXDialogLayout dialogContent = new JFXDialogLayout();

        dialogContent.setHeading(new Text(title));

        JFXButton close = new JFXButton("Close");

        close.setButtonType(JFXButton.ButtonType.RAISED);

        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);

        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");

        close.setOnAction(e -> dialog.close());
        if (!usernameField.getText().matches("[a-zA-Z0-9_]{4,}") || usernameField.getText() == "") {
            dialogContent.setBody(new Text("Invalid Username !"));
            dialog.show();
            return;
        }
        if (passwordField.getText().isEmpty()) {
            dialogContent.setBody(new Text("Invalid Password !"));
            dialog.show();
            return;
        }

        Utilisateur User = null;
        int status;
        if (connect == null)
            status = -1;
        else {
            User = utilisateurDAO.find(usernameField.getText());
            status = checkLogin(User.getPassword());
            username = usernameField;
        }

        switch (status) {
            case 0: {
                Stage stage = (Stage) usernameField.getScene().getWindow();

                Parent root = null;
                try {
                    if (User.getCodeUtilisateur() == 1) {
                        root = FXMLLoader.load(getClass().getResource("../view/adminHome.fxml"));


                    } else {
                        root = FXMLLoader.load(getClass().getResource("../view/userHome.fxml"));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                scene.setFill(Color.TRANSPARENT);

            }
            break;
            case 1: {
                dialogContent.setBody(new Text("Invalid Username or Password !"));
                dialog.show();
            }
            break;
            default: {
                dialogContent.setBody(new Text("Connection Failed !"));
                dialog.show();
            }
        }
    }

    private int checkLogin(String name) {
        if (name.equals(passwordField.getText())) {
            return 0;
        }
        return 1;
    }

    public void close() {
        Platform.exit();
    }

    public void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


}