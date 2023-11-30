/*
 * ParametreController.java
 */
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.GestionNote;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
/**
 * Controlleur donnant accés à toutes les pages concernant l'importation, 
 * l'exportation et la reinitialisation de l'application
 * @enzo.cluzel
 */
public class ParametreController {
    
    /** Utilisé lors du changement du contenue de la page pour appeler la page fxml  */
    Parent fxml;
    
    /** Afficher le contenue de la page par la nouvelle appelé */
    @FXML
    private AnchorPane contenuPage;

    /**
     * Donne la page correspondant aux button a changerPage 
     */
    @FXML
    void communiquerActionButton() throws IOException {
        changerPage("communiquer.fxml");
    }

    /**
     * Reinitialse les donnees du stockage si l'utilisateur confirme son choix 
     * Une notification apparaitra si le choix a était confirmé
     */
    @FXML
    void reinitialiserActionButton() {
        NotificationController NotificationController = new NotificationController();
        Optional<ButtonType> result = NotificationController.popUp("Reinitialisation données","Réinitialiser vos données ?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stockage.getInstance().supprimerDonnees();
            NotificationController.showNotification("Vos données ont bien était réinitialiser");
        }
        ParametrageNationalPrototype.flag = true;
    }

    /**
     * Donne la page correspondant aux button a changerPage
     */
    @FXML
    void importerActionButton() throws IOException {
        changerPage("ImporterParametres.fxml");
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