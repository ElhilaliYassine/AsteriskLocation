package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Utilisateur;
import models.DAO.UtilisateurDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static models.DAO.DAO.connect;

public class utilisateurController implements Initializable {

    @FXML
    private TableView<Utilisateur> table;

    @FXML
    private TableColumn<Utilisateur, String> col_codeUtilisateur;

    @FXML
    private TableColumn<Utilisateur, String> col_nomComplet;

    @FXML
    private TableColumn<Utilisateur, String> col_password;

    @FXML
    private TableColumn<Utilisateur, String> col_email;

    @FXML
    private TableColumn<Utilisateur, String> col_numeroGsm;

    @FXML
    private TableColumn<Utilisateur, String>col_adresse;

    @FXML
    private AnchorPane blur;

    @FXML
    private AnchorPane loadPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnClose;

    @FXML
    private JFXTextField filterField;

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

    ObservableList<Utilisateur> list = utilisateurDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataUser();



    }
    public void DataUser()
    {
        col_codeUtilisateur.setCellValueFactory(new PropertyValueFactory<>("codeUtilisateur"));
        col_nomComplet.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_numeroGsm.setCellValueFactory(new PropertyValueFactory<>("numGsm"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        table.setItems(list);
    }
    @FXML
    public void createUser() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createUser.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
        btnClose.setVisible(true);
        btnClose.toFront();

    }
    @FXML
    public void btnReturn()
    {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = utilisateurDAO.list();
        DataUser();


    }
    public void search()
    {

        FilteredList<Utilisateur> filteredData = new FilteredList<>(list, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Utilisateur -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Utilisateur.getNomComplet().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (Utilisateur.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (Utilisateur.getAdresse().toLowerCase().contains(lowerCaseFilter))
                {
                    return true;
                }
                return false;
            });
        });
        SortedList<Utilisateur> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
    public void deleteUser()
    {

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
                blur.setEffect(null);
                list = utilisateurDAO.list();
                DataUser();

            }
        });

        if(table.getSelectionModel().isEmpty())
        {
            dialogContent.setBody(new Text("Selectionner l'utilisateur à supprimer !"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }else{
            Utilisateur user = utilisateurDAO.find(table.getSelectionModel().getSelectedItem().getNomComplet());
            utilisateurDAO.delete(user);
            dialogContent.setBody(new Text("L'utilisateur a été supprimé !"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;

        }
        
    }

}

