package GestionNoteApplication.src.main.controller;


import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


public class ImporterParametresController_1 {

    
    Parent fxml;
    @FXML
    private AnchorPane contenuPage;
    
    @FXML
    void LocalActionButton(ActionEvent event) throws IOException {
        changerPage("ImporterParametres_1.fxml");
    }

    @FXML
    void DistanceActionButton(ActionEvent event) throws IOException {
        changerPage("importationDistance.fxml");

    }
    public void changerPage(String page) throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        if(file.exists() && file.isFile()) {
            String changementPage = "../../ressources/fxml/"+page;
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }

}
