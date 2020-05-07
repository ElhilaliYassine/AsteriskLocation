package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class reservationController {

    @FXML
    private Pane msgPane;

    @FXML
    private StackPane myStackPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnClose;

    @FXML
    private AnchorPane loadPane;

    @FXML
    private AnchorPane rootPane1;

    @FXML
    private Button btnClose1;

    @FXML
    private JFXTextField nomCompletField;

    @FXML
    private JFXTextField adresseField;

    @FXML
    private JFXTextField numGsmField;

    @FXML
    private StackPane myStackPane1;

    @FXML
    private AnchorPane blur;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> col_codeReservation;

    @FXML
    private TableColumn<?, ?> col_dateDepart;

    @FXML
    private TableColumn<?, ?> col_dateRetour;

    @FXML
    private TableColumn<?, ?> col_idClient;

    @FXML
    private TableColumn<?, ?> col_idVehicule;

    @FXML
    private JFXTextField filterField;

}