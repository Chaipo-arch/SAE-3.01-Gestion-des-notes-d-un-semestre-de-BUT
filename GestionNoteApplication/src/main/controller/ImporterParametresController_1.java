package GestionNoteApplication.src.main.controller;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * Contrôleur pour l'importation des paramètres.
 * Gère le changement de page pour l'importation locale ou à distance des paramètres.
 * @author Enzo Cluzel
 */
public class ImporterParametresController_1 {

    Parent fxml;

    @FXML
    private AnchorPane contenuPage;

    /**
     * Charge la page d'importation locale.
     *
     * @param event Événement déclencheur de l'action
     * @throws IOException En cas d'erreur lors du chargement de la page
     */
    @FXML
    void LocalActionButton(ActionEvent event) throws IOException {
        changerPage("ImporterParametres_1.fxml");
    }

    /**
     * Charge la page d'importation à distance.
     *
     * @param event Événement déclencheur de l'action
     * @throws IOException En cas d'erreur lors du chargement de la page
     */
    @FXML
    void DistanceActionButton(ActionEvent event) throws IOException {
        changerPage("importationDistance.fxml");
    }

    /**
     * Change la page affichée
     * Charge la page spécifiée en paramètre.
     *
     * @param page Nom du fichier FXML de la page à charger
     * @throws IOException En cas d'erreur lors du chargement de la page
     */
    public void changerPage(String page) throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/" + page);
        if (file.exists()) {
            String changementPage = "../../ressources/fxml/" + page;
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }
}
