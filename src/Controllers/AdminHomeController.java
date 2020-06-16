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

public class AdminHomeController implements Initializable, Window {
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
    @FXML
    private Button btnContrat;
    @FXML
    private Button btnFacture;
    @FXML
    private Button btnSanction;
    @FXML
    private Button btnDashboard;

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
        try {
            dashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Fermer l'application
    public void close() {
        Platform.exit();
    }

    //Minimiser la fenetre
    public void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    //Se déconnecter de la session
    @FXML
    private void logout() {
        Stage stage = (Stage) btnClose.getScene().getWindow();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
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


    //Horloge Heure + date
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy   HH : mm : ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //Afficher les infos de l'utilisateur connecté
    private void afficheInfos() {
        Utilisateur user = utilisateurDAO.find(LoginController.username.getText());
        name.setText(user.getNomComplet());
        email.setText(user.getEmail());
    }

    //Style boutton menu

    @FXML
    private void user() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/utilisateur.fxml"));
        rootPane.getChildren().setAll(pane);
        btnUtilisateur.setStyle("-fx-background-color : #278ef4;");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");


    }

    @FXML
    private void client() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/client.fxml"));
        rootPane.getChildren().setAll(pane);
        btnClient.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");


    }

    @FXML
    private void vehicule() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/vehicule.fxml"));
        rootPane.getChildren().setAll(pane);
        btnVehicule.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");


    }

    @FXML
    private void parking() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/parking.fxml"));
        rootPane.getChildren().setAll(pane);
        btnParking.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");


    }

    @FXML
    private void reservation() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/reservation.fxml"));
        rootPane.getChildren().setAll(pane);
        btnReservation.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");

    }

    @FXML
    private void contrat() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/contrat.fxml"));
        rootPane.getChildren().setAll(pane);
        btnContrat.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");

    }

    @FXML
    private void facture() throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/facture.fxml"));
        rootPane.getChildren().setAll(pane);
        btnFacture.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");

    }

    @FXML
    private void sanction() throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/sanction.fxml"));
        rootPane.getChildren().setAll(pane);
        btnSanction.setStyle("-fx-background-color : #278ef4;");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnDashboard.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");

    }

    @FXML
    private void dashboard() throws IOException
    {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/dashboard.fxml"));
        rootPane.getChildren().setAll(pane);
        btnDashboard.setStyle("-fx-background-color : #278ef4;");
        btnSanction.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnUtilisateur.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnClient.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnVehicule.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnParking.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnReservation.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnContrat.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
        btnFacture.setStyle("{-fx-background-color : #278ef4;}:hover{fx-background-color : #0c7be9;}");
    }
}
