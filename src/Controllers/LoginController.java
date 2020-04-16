package Controllers;

     import com.jfoenix.controls.*;
     import javafx.application.Platform;
     import javafx.fxml.FXML;
     import javafx.fxml.FXMLLoader;
     import javafx.fxml.Initializable;
     import javafx.event.*;
     import javafx.geometry.Rectangle2D;
     import javafx.scene.Node;
     import javafx.scene.Parent;
     import javafx.scene.Scene;
     import javafx.scene.control.Button;
     import javafx.scene.input.MouseEvent;
     import javafx.scene.layout.HBox;
     import javafx.scene.layout.StackPane;
     import javafx.scene.paint.Color;
     import javafx.scene.shape.Rectangle;
     import javafx.scene.text.Text;
     import javafx.stage.Screen;
     import javafx.stage.Stage;
     import javafx.stage.StageStyle;
     import models.DAO.UtilisateurDAO;
     import models.Utilisateur;

     import javax.swing.*;
     import java.io.IOException;
     import java.net.URL;
     import java.util.ResourceBundle;
     import java.util.logging.Level;
     import java.util.logging.Logger;

     import static models.DAO.DAO.connect;

public class LoginController implements Initializable {

        static JFXTextField username;
        @FXML
        private JFXTextField usernameField;

         @FXML
         private StackPane myStackPane;

         @FXML
         private JFXPasswordField passwordField;

         @Override
         public void initialize(URL url, ResourceBundle resourceBundle) {

         }
             @FXML
             private void handleButtonLogin(ActionEvent event) {


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
                     }
                 });
                 if (!usernameField.getText().matches("[a-zA-Z0-9_]{4,}")) {
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
                 if(connect == null)
                    status = -1;
                 else {
                     User = UtilisateurDAO.find(usernameField.getText());
                     status = checkLogin(User.getPassword());
                     username = usernameField;
                 }

                 switch (status) {
                     case 0: {
                         Stage stage = (Stage) usernameField.getScene().getWindow();

                         Parent root = null;
                         try {
                             if(User.getCodeUtilisateur()==1) {
                                 root = FXMLLoader.load(getClass().getResource("../view/adminHome.fxml"));


                             }else
                             {
                                 root = FXMLLoader.load(getClass().getResource("../view/userHome.fxml"));
                             }
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
                     break;
                     case 1:
                     {
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

     private int checkLogin(String name)
     {
         if (name.equals(passwordField.getText()))
         {
             return 0;
         }
         return 1;
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



}