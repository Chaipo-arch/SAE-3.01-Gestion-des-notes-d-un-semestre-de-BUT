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
        changerPage("communiquer.fxml");
    }

    @FXML
    void reinitialiserActionButton() {
        Stockage.getInstance().supprimerDonnees();
        ParametrageNationalPrototype.flag = true;
    }

    @FXML
    void importerActionButton() throws IOException {
        changerPage("ImporterParametres.fxml");
    }
     public void changerPage(String page) throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        if(file.exists()) {
             String changementPage = "../../ressources/fxml/"+page;
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }

}
