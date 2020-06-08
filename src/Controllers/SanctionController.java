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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.*;
import models.DAO.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SanctionController implements Initializable {
    @FXML
    private Pane msgPane;
    @FXML
    private StackPane myStackPane;
    @FXML
    private AnchorPane blur;
    @FXML
    private TableView<Sanction> table;
    @FXML
    private TableColumn<Contrat, Integer> col_numeroSanction;
    @FXML
    private TableColumn<Contrat, LocalDate> col_nbrJoursRetard;
    @FXML
    private TableColumn<Contrat, Double> col_idContrat;
    @FXML
    private TableColumn<Contrat, Integer> col_montantAPayer;
    @FXML
    private JFXTextField filterField;

    SanctionDAO sanctionDAO;
    {
        try {
            sanctionDAO = new SanctionDAO(SanctionDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }

    ObservableList<Sanction> list = sanctionDAO.list();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataContrat();
    }

    private void dataContrat() {
        col_numeroSanction.setCellValueFactory(new PropertyValueFactory<>("idSanction"));
        col_nbrJoursRetard.setCellValueFactory(new PropertyValueFactory<>("nbrJoursRetard"));
        col_idContrat.setCellValueFactory(new PropertyValueFactory<>("idContrat"));
        col_montantAPayer.setCellValueFactory(new PropertyValueFactory<>("montantAPayer"));
        table.setItems(list);
    }

    public void search() {
        FilteredList<Sanction> filteredData = new FilteredList<>(list, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sanction -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                String numero = String.valueOf(sanction.getIdSanction());
                if (numero.toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });
        });
        SortedList<Sanction> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void deleteContrat() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        JFXButton close = new JFXButton("Close");
        dialogContent.setHeading(new Text(title));
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        msgPane.toFront();
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            blur.setEffect(null);
            list = sanctionDAO.list();
            dataContrat();
        });
        if (table.getSelectionModel().isEmpty()) {
            dialogContent.setBody(new Text("Veuillez selectionner la sanction à supprimer!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        } else {
            Sanction sanction = sanctionDAO.find(table.getSelectionModel().getSelectedItem().getIdSanction());
            sanctionDAO.delete(sanction);
            dialogContent.setBody(new Text("La sanction a été supprimé!"));
            dialog.show();
            blur.setEffect(new GaussianBlur(10));
            return;
        }
    }
}