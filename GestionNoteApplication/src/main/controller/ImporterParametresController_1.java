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
        changerPage("ImporterParametres.fxml");
    }

    @FXML
    void DistanceActionButton(ActionEvent event) throws IOException {
        changerPage("");

    }
    public void changerPage(String page) throws IOException {
        File file = new File("src/IHM/"+page);
        if(file.exists()) {
            fxml = FXMLLoader.load(getClass().getResource(page));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }

}
