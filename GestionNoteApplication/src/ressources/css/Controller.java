// Controller.java

package GestionNoteApplication.src.ressources.css;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {
    @FXML
    private ComboBox<String> myComboBox;

    public void initialize() {
        // Initialise la ComboBox avec des éléments
        initializeComboBox();
    }

    private void initializeComboBox() {
        // Crée une liste d'éléments
        ObservableList<String> items = FXCollections.observableArrayList(
                "Option 1",
                "Option 2",
                "Option 3"
        );

        // Ajoute les éléments à la ComboBox
        myComboBox.setItems(items);

        // Vous pouvez également ajouter des éléments individuellement
        // myComboBox.getItems().addAll("Option 4", "Option 5");
    }
}