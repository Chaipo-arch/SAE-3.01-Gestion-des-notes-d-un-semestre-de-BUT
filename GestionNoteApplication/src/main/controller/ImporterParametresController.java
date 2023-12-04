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

    @FXML
    void choixValiderAction(ActionEvent event){
        
        
            if(!nationalToggle.isSelected() && !ressourceToggle.isSelected() || file==null) {
                NotificationController.popUpMessage("Merci de cocher une option et de selectionner le fichier que vous souhaitez importer ", "Erreur Importation");
            }
            if(nationalToggle.isSelected() && file != null) {
                
                    Optional<ButtonType> result = NotificationController.popUpChoix("L'imporation de votre Nouveau Programme vas supprimer toutes les données précédemment enregistrer souhaitez vous continuer ?","");
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            System.out.println("Reini");
                            Stockage.getInstance().supprimerDonnees();
                            System.out.println("ok");
                            ParametrageNationalPrototype paN = null;
                            try {
                                paN = new ParametrageNationalPrototype(file);
                            } catch (EvaluationException ex) {
                                System.out.println("erreur2");
                            }
                            try {
                                paN.parse();
                                NotificationController NotificationController = new NotificationController();
                                NotificationController.showNotification("Importation Réussie");
                            } catch (NoteException ex) {
                                System.out.println("erreur");
                            }
                        } catch (IOException ex) {

                        } catch (MauvaisFormatFichierException ex) {
                            NotificationController.popUpMessage(ex.getMessage(),ex.getTitre());
                            System.out.println(ex.getMessage());
                        }
                    } 
                
            }
            if(ressourceToggle.isSelected() && file != null) {
                try {
                    System.out.println("ok");
                    ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(file);
                    try {
                        paR.parse();
                        System.out.println(Stockage.getInstance().evaluations);
                         NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Votre programme Nationnal a bien été importé");
                    } catch (EvaluationException ex) {
                        Logger.getLogger(ImporterParametresController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException|NoteException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    NotificationController.popUpMessage(ex.getMessage(),ex.getTitre());
                }
            }
            
        }
    

}