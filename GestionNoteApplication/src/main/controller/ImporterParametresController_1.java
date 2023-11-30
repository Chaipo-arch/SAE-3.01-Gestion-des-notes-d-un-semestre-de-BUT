/*
 * ImporterParametresController_1.java
 */
package GestionNoteApplication.src.main.controller;


import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * Controlleur permettant d'acceder à toutes les pages concernant l'importation de parametrage que ce soit en local ou a distance.
 * @author enzo.cluzel
 */
public class ImporterParametresController_1 {

    /** Utilisé lors du changement du contenue de la page pour appeler la page fxml  */
    Parent fxml;

    /** Afficher le contenue de la page par la nouvelle appelé */
    @FXML
    private AnchorPane contenuPage;
    
    /**
     * Donne la page correspondant aux button a changerPage 
     */
    @FXML
    void LocalActionButton(ActionEvent event) throws IOException {
        changerPage("ImporterParametres_1.fxml");
    }

    /**
     * Donne la page correspondant aux button a changerPage 
     */
    @FXML
    void DistanceActionButton(ActionEvent event) throws IOException {
        changerPage("importationDistance.fxml");

    }
    
     /**
     * Change la page selon le button appuyé sur le menu
     * L'anchor pane de la page est modifié pour prendre celle du fxml demandé
     * @param page, la page demandée
     * @throws IOException si la page demandée est cherché mais n'est pas trouvé
     */
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