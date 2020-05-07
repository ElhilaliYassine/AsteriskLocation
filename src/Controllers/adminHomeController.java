package Controllers;

import Controllers.interfaces.Window;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.DAO.UtilisateurDAO;
import models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static models.DAO.DAO.connect;

public class adminHomeController implements Initializable, Window {
    @FXML
    private Button btnClose;
    @FXML
    private Label dateTime;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Button btnUtilisateur;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button btnClient;
    @FXML
    private Button btnVehicule;
    @FXML
    private Button btnParking;
    @FXML
    private Button btnReservation;

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
        initClock();
        afficheInfos();

    }

    public void close() {
        Platform.exit();
    }

    public void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    private void logout() {
        Stage stage = (Stage) btnClose.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
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


    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy   HH : mm : ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void afficheInfos() {
        Utilisateur user = utilisateurDAO.find(loginController.username.getText());
        name.setText(user.getNomComplet());
        email.setText(user.getEmail());

    }

    @FXML
    private void user() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/utilisateur.fxml"));
        rootPane.getChildren().setAll(pane);
        btnUtilisateur.setStyle("-fx-background-color : #1620A1;");
        btnClient.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnVehicule.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnParking.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnReservation.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
    }

    @FXML
    private void client() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/client.fxml"));
        rootPane.getChildren().setAll(pane);
        btnClient.setStyle("-fx-background-color : #1620A1;");
        btnUtilisateur.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnVehicule.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnParking.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnReservation.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
    }

    @FXML
    private void vehicule() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/vehicule.fxml"));
        rootPane.getChildren().setAll(pane);
        btnVehicule.setStyle("-fx-background-color : #1620A1;");
        btnUtilisateur.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnClient.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnParking.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnReservation.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
    }

    @FXML
    private void parking() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/parking.fxml"));
        rootPane.getChildren().setAll(pane);
        btnParking.setStyle("-fx-background-color : #1620A1;");
        btnUtilisateur.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnClient.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnVehicule.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnReservation.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
    }
    @FXML
    private void reservation() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/reservation.fxml"));
        rootPane.getChildren().setAll(pane);
        btnReservation.setStyle("-fx-background-color : #1620A1;");
        btnUtilisateur.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnClient.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnVehicule.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
        btnParking.setStyle("{-fx-background-color : #05071F;}:hover{fx-background-color : #10165F;}");
    }

}
