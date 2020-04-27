package Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class vehiculeController implements Initializable {

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
    private JFXTextField filterField;

    @FXML
    private Pane msgPane;

    @FXML
    private StackPane myStackPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}