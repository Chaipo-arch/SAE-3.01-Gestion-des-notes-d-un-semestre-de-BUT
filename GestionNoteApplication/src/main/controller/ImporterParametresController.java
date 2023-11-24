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

    /**
     * Affiche une nouvelle fenetre à l'utilisateur qui lui permet 
     * de choisir un fichier CSV, le fichier si bon sera ensuite affiché dans le label nomFichier
     * @param event click sur le button
     * @throws IOException 
     */
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
     * Selon le choix de l'utilsateur :
     * - si est coché national, crée un parametrageNational
     * - si est coché Ressource, crée un parametrageRessource
     * Ces parametrage seront ensuite lue
     * @param event le click sur le bouton
     */
    @FXML
    void choixValiderAction(ActionEvent event){
            if(nationalToggle.isSelected()) {
                try {
                    ParametrageNationalPrototype paN = null;
                    try {
                        paN = new ParametrageNationalPrototype(file);
                    } catch (EvaluationException ex) {
                        System.out.println("erreur2");
                    }
                    try {
                        paN.parse();
                    } catch (NoteException ex) {
                        System.out.println("erreur");
                    }
                } catch (IOException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    System.out.println(ex.getMessage());
                }
            } 
            if(ressourceToggle.isSelected()) {
                try {
                    ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(file);
                    try {
                        paR.parse();
                        System.out.println(Stockage.getInstance().evaluations);
                    } catch (EvaluationException ex) {
                        Logger.getLogger(ImporterParametresController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException|NoteException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
        }
    

}