package Controllers;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.DAO.ParkingDAO;
import models.DAO.VéhiculeDAO;
import models.Parking;
import models.Véhicule;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static models.DAO.DAO.connect;

public class CreateVehiculeController implements Initializable {

    @FXML
    private StackPane myStackPane;

    @FXML
    private JFXTextField matriculeField;

    @FXML
    private JFXTextField marqueField;

    @FXML
    private JFXTextField typeField;

    @FXML
    private JFXTextField carburantField;

    @FXML
    private JFXTextField compteurKmField;

    @FXML
    private JFXTextField prixField;

    @FXML
    private DatePicker dateField;

    @FXML
    private JFXComboBox<String> selectParking;

    @FXML
    private JFXRadioButton ouiRadio;

    @FXML
    private JFXRadioButton nonRadio;

    final ToggleGroup group = new ToggleGroup();

    VéhiculeDAO véhiculeDAO;

    {
        try {
            véhiculeDAO = new VéhiculeDAO(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ParkingDAO parkingDAO;

    {
        try {
            parkingDAO = new ParkingDAO(ParkingDAO.connect);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
        }
    }
    ObservableList<String> list = parkingDAO.select();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ouiRadio.setToggleGroup(group);
        nonRadio.setToggleGroup(group);
        selectParking.setItems(list);
    }
    //Ajouter un nouveau véhicule
    @FXML
    public void newVehicule() {
        String title = "Asterisk Location - Message :";
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(new Text(title));
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #4059a9; -fx-text-fill: #FFF; -fx-background-radius : 18");
        dialogContent.setActions(close);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogContent, JFXDialog.DialogTransition.BOTTOM);
        myStackPane.toFront();
        dialog.setStyle("-fx-background-radius : 18");
        close.setOnAction(e -> {
            dialog.close();
            clear();
        });
        if (matriculeField.getText().isEmpty() || typeField.getText().isEmpty() || marqueField.getText().isEmpty() || carburantField.getText().isEmpty() || prixField.getText().isEmpty() || compteurKmField.getText().isEmpty() || dateField.getValue()== null || group.getSelectedToggle()== null || selectParking.getValue()==null) {
            dialogContent.setBody(new Text("Véhicule invalide!"));
            dialog.show();
            return;
        }
        Véhicule checker = véhiculeDAO.find(Integer.parseInt(matriculeField.getText()));
        if(checker.getNImmatriculation() != 0)
        {
            dialogContent.setBody(new Text("Le véhicule déjà enregistré!"));
        }else{
            Véhicule véhicule = null;
            Parking parking = parkingDAO.find(selectParking.getValue());
            if(parking.getCapacité()-parkingDAO.nombreVehicule(parking.getNParking())>0) {
                    if (group.getSelectedToggle() == ouiRadio)
                        try{
                            véhicule = new Véhicule(Integer.parseInt(matriculeField.getText()), marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), true, Double.parseDouble(prixField.getText()));
                        }catch (NumberFormatException e)
                        {
                            dialogContent.setBody(new Text("Veuillez vérifier le champs saisis!"));
                            dialog.show();
                            return;
                        }
                    else if (group.getSelectedToggle() == nonRadio)
                        try {
                            véhicule = new Véhicule(Integer.parseInt(matriculeField.getText()), marqueField.getText(), typeField.getText(), carburantField.getText(), Double.parseDouble(compteurKmField.getText()), dateField.getValue(), parking.getNParking(), false, Double.parseDouble(prixField.getText()));
                        }catch(NumberFormatException e)
                        {
                            dialogContent.setBody(new Text("Veuillez vérifier le champs saisis!"));
                            dialog.show();
                            return;
                        }
            }else if(parking.getCapacité()-parkingDAO.nombreVehicule(parking.getNParking())<=0){
                dialogContent.setBody(new Text("Le parking est saturé"));
                dialog.show();
                return;
            }

            if (véhiculeDAO.create(véhicule))
                dialogContent.setBody(new Text("Le véhicule a été enregistré"));
            else
                dialogContent.setBody(new Text("Le véhicule n'a pas été enregistré"));
        }
            dialog.show();
            return;
    }
    //Vider les inputs
    @FXML
    public void clear() {
        matriculeField.setText("");
        marqueField.setText("");
        typeField.setText("");
        carburantField.setText("");
        compteurKmField.setText("");
        prixField.setText("");
        dateField.setValue(null);
        group.selectToggle(null);
        selectParking.setItems(list);
    }
}
