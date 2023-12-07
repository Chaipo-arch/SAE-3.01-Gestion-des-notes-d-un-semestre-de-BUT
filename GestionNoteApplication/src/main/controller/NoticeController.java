/*
 * NoticeController.java
 */
package GestionNoteApplication.src.main.controller;

import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.Server;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Contrôleur pour la gestion de la page de notice dans l'application de gestion de notes.
 * Initialise et gère les éléments de la page de notice.
 * @author enzo.cluzel
 */
public class NoticeController implements Initializable {
    
    /**
     * Initialise la classe du contrôleur.
     * @param url L'URL utilisée pour initialiser le contrôleur.
     * @param rb  ResourceBundle utilisé pour localiser l'objet racine.
     */
    public void initialize(URL url, ResourceBundle rb) {
        setScene();
    }
    
    Stage noticeStage;
    public static double y;

    @FXML
    private ScrollPane scroll;

    @FXML
    private AnchorPane contenu2;

    @FXML
    private Button btnRetourNotice;

    @FXML
    private AnchorPane contenuPage;

    public static String pageRetour;
    Parent fxml;
    
    /**
     * Configure la scène pour afficher la page de notice.
     */
    public void setScene(){
        scroll.setVvalue(y);
    }
    
    /**
     * Ouvre le fichier CSV de paramétrage national lorsqu'on clique sur le bouton correspondant.
     * @throws IOException Si une erreur d'entrée/sortie survient lors de l'ouverture du fichier.
     */
    @FXML
    void askNationalCsv() throws IOException {
        Desktop.getDesktop().open(new File("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCorrect.csv"));
    }

    /**
     * Ouvre le fichier CSV de paramétrage des ressources lorsqu'on clique sur le bouton correspondant.
     * @throws IOException Si une erreur lors de l'ouverture du fichier.
     */
    @FXML
    void askRessourceCsv() throws IOException {
        Desktop.getDesktop().open(new File("src/GestionNoteApplication/src/ressources/csv/ParametrageRessourceCorrect.csv"));
    }
    
    /**
     * Gère le clic sur le bouton "Retour" pour revenir à la page précédente.
     * @param event L'événement de clic associé au bouton "Retour".
     * @throws IOException Si une erreur d'entrée/sortie survient lors du changement de page.
     */
    @FXML
    void RetourAction(ActionEvent event) throws IOException {
        AccueilController.accueil.changerPage(pageRetour);
    }

}