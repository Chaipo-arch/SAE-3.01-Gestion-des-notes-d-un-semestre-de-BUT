package GestionNoteApplication.src.main.controller;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.modele.Stockage;

public class ParametreController {
    
    Parent fxml;
    @FXML
    private AnchorPane contenuPage;

    @FXML
    void communiquerActionButton() throws IOException {
        changerPage("fxml/communiquer.fxml");
    }

    @FXML
    void reinitialiserActionButton() {
        Stockage.getInstance().supprimerDonnees();
        ParametrageNationalPrototype.flag = true;
    }

    @FXML
    void importerActionButton() throws IOException {
        changerPage("fxml/ImporterParametres.fxml");
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
