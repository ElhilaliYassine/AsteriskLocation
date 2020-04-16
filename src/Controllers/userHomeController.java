package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controllers.LoginController;

public class userHomeController implements Initializable {
    @FXML
    private Button btnClose;
    @FXML
    private Label dateTime;
    @FXML
    private Label name;
    @FXML
    private Label email;

    private Utilisateur user ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();
        afficheInfos();

    }
    @FXML
    private void Close(){
        Platform.exit();
    }
    @FXML
    private void Minimize(MouseEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void Logout()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        scene.setFill(Color.TRANSPARENT);
    }


    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy   HH : mm : ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    private void afficheInfos()
    {
        Utilisateur user = UtilisateurDAO.find(LoginController.username.getText());
        name.setText(user.getNomComplet());
        email.setText(user.getEmail());
    }

}
