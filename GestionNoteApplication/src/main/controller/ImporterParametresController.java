package GestionNoteApplication.src.main.controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;


import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.util.Optional;
import javafx.scene.control.ButtonType;



/**
 * Class de Controlleur de l'IHM, permettant l'importation des parametres en local
 * elle verifie les fichiers en appelant ParametrageNational.java ou ParametrageRessource.java
 * Affichage de Notification utilisateur en foocntions des cas d'erreurs
 * @author Enzo.Cluzel, Robin.Britelle
 */
public class ImporterParametresController {

       @FXML
    private RadioButton nationalToggle;

    @FXML
    private ToggleGroup choix;

    @FXML
    private Label nomFichier;

    private File file;
    @FXML
    private RadioButton ressourceToggle;

    @FXML
    void importerFichier(ActionEvent event) throws IOException {
       
        
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("csv","*.csv");
        ex.getExtensions();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(ex);
        File selectedFile = fileChooser.showOpenDialog(null);
           
            if (selectedFile != null) {
                file = selectedFile;
                nomFichier.setText(selectedFile.getName());
                
            
            }
        
    }

    /**
    * Gère l'action de validation du choix d'importation.
    * Vérifie les options sélectionnées et les fichiers choisis pour l'importation,
    * affiche des messages d'erreur ou procède à l'importation en conséquence.
    *
    * @param event Événement déclencheur de l'action
    */
    @FXML
    void choixValiderAction(ActionEvent event) {
        
        if (!nationalToggle.isSelected() && !ressourceToggle.isSelected() || file == null) {
            NotificationController.popUpMessage("Merci de cocher une option et de sélectionner le fichier que vous souhaitez importer ", "Erreur Importation");
        }

        if (nationalToggle.isSelected() && file != null) {
            // Demande une confirmation pour la suppression des données précédentes
            Optional<ButtonType> result = NotificationController.popUpChoix("L'importation de votre Nouveau Programme va supprimer toutes les données précédemment enregistrées. Souhaitez-vous continuer ?", "");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // Supprime les données précédentes
                    Stockage.getInstance().supprimerDonnees();
                    ParametrageNationalPrototype paN = null;
                    try {
                        paN = new ParametrageNationalPrototype(file);
                    } catch (EvaluationException ex) {
                        System.out.println("erreur2");
                    }
                    try {
                        // Parse les données
                        paN.parse();
                        
                        // Affiche une notification de réussite
                        NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Importation Réussie");
                    } catch (NoteException ex) {
                        System.out.println("erreur");
                    }
                } catch (IOException ex) {

                } catch (MauvaisFormatFichierException ex) {
                    NotificationController.popUpMessage(ex.getMessage(), ex.getTitre());
                    System.out.println(ex.getMessage());
                }
            }
        }

        if (ressourceToggle.isSelected() && file != null) {
            try {
                // Crée un objet ParametrageRessourcePrototype avec le fichier sélectionné
                ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(file);
                try {
                    // Parse les données
                    paR.parse();
                    
                    // Affiche une notification de réussite
                    NotificationController NotificationController = new NotificationController();
                    NotificationController.showNotification("Votre programme Nationnal a bien été importé");
                } catch (EvaluationException ex) {
                    Logger.getLogger(ImporterParametresController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException | NoteException ex) {

            } catch (MauvaisFormatFichierException ex) {
                NotificationController.popUpMessage(ex.getMessage(), ex.getTitre());
            }
        }
    }
}