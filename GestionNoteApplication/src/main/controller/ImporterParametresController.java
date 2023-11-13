package GestionNoteApplication.src.main.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ImporterParametresController {

    @FXML
    private ToggleGroup choix;

    @FXML
    private Label nomFichier;

    @FXML
    private RadioButton localToggle;

    @FXML
    private RadioButton distanceToggle;

    @FXML
    void importerFichier(ActionEvent event) throws IOException {
        System.out.println(choix.getSelectedToggle());
        System.out.println(localToggle.isSelected());
        if(localToggle.isSelected()) {
            String nomDossier = null;
            Desktop.getDesktop().open(new File("C:\\folder"));
        }
    }

    @FXML
    void choixValiderAction(ActionEvent event) {

    }

}
