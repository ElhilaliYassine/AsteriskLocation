package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Client;
import models.DAO.ClientDAO;
import models.Utilisateur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    @FXML
    private TableView<Client> table;

    @FXML
    private TableColumn<Client, Integer> col_codeClient;
    @FXML
    private TableColumn<Client, String> col_nomComplet;
    @FXML
    private TableColumn<Client, String> col_adresse;
    @FXML
    private TableColumn<Client, Integer> col_numGsm;
    @FXML
    private AnchorPane blur;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane updatePane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private StackPane myStackPane, myStackUpdate;
    @FXML
    private JFXTextField nomCompletField;
    @FXML
    private JFXTextField adresseField;
    @FXML
    private JFXTextField numGsmField;
    @FXML
    private JFXTextField uriImageField;
    @FXML
    private Label idClient;
    ClientDAO clientDAO;

    {
        try {
            clientDAO = new ClientDAO(ClientDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

    ObservableList<Client> list = clientDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataUser();
    }

    private void DataUser() {
        col_codeClient.setCellValueFactory(new PropertyValueFactory<>("codeClient"));
        col_nomComplet.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_numGsm.setCellValueFactory(new PropertyValueFactory<>("numGsm"));
        table.setItems(list);
    }

    public void createClient() throws IOException {
        blur.setEffect(new GaussianBlur(10));
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../view/createClient.fxml"));
        loadPane.getChildren().setAll(pane);
        rootPane.setVisible(true);
        rootPane.toFront();
    }

    public void btnReturn() {
        blur.setEffect(null);
        rootPane.setVisible(false);
        rootPane.toBack();
        list = clientDAO.list();
        DataUser();
    }

    public void search() {
        FilteredList<Client> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (client.getNomComplet().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getAdresse().toLowerCase().contains(lowerCaseFilter)) return true;
                String numGsmString = String.valueOf(client.getNumGsm());
                if (numGsmString.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void deleteClient() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            blur.setEffect(null);
            list = clientDAO.list();
            DataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le client à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
        else {
            Client user = clientDAO.find(table.getSelectionModel().getSelectedItem().getNomComplet());
            clientDAO.delete(user);
            dialogContent.setBody(new Text("Le client a été supprimé!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
    }

    public void updateClient() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            blur.setEffect(null);
            list = clientDAO.list();
            DataUser();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner le client à modifier!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Client client = clientDAO.find(table.getSelectionModel().getSelectedItem().getNomComplet());
            idClient.setText(String.valueOf(client.getCodeClient()));
            blur.setEffect(new GaussianBlur(10));
            updatePane.setVisible(true);
            updatePane.toFront();
            nomCompletField.setText(client.getNomComplet());
            adresseField.setText(client.getAdresse());
            numGsmField.setText(String.valueOf(client.getNumGsm()));
        }
    }

    public void returnUpdate() {
        blur.setEffect(null);
        updatePane.setVisible(false);
        updatePane.toBack();
        list = clientDAO.list();
        DataUser();
    }

    public void modifyClient() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackUpdate, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
        });
        Client client = new Client(0, nomCompletField.getText(), adresseField.getText(), Integer.parseInt(numGsmField.getText()), "");
        if (clientDAO.update(client, table.getSelectionModel().getSelectedItem().getCodeClient())) {
            dialogContent.setBody(new Text("Le client a été modifié!"));
            dialog.show();
            return;
        }
    }
}
