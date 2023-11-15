package GestionNoteApplication.src.main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class CommuniquerController {

    @FXML
    private AnchorPane NotifUtil;

    @FXML
    private TextField adresseIPText;

    @FXML
    private RadioButton toggleNational;

    @FXML
    private RadioButton toggleRessource;

    @FXML
    private ToggleGroup choix;

    @FXML
    void ValiderAction(ActionEvent event) {
        System.out.println(choix.getSelectedToggle());
       // if (choix.)
    }

}