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
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;


import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;

public class ImporterParametresController {

       @FXML
    private RadioButton nationalToggle;

    @FXML
    private ToggleGroup choix;

    @FXML
    private Label nomFichier;

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
                nomFichier.setText(selectedFile.getName());
                
            
            }
        
    }

    @FXML
    void choixValiderAction(ActionEvent event){
            if(nationalToggle.isSelected()) {
                try {
                    System.out.println("ok");
                    ParametrageNationalPrototype paN = new ParametrageNationalPrototype(nomFichier.getText());
                    paN.parse();
                } catch (IOException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    System.out.println(ex.getMessage());
                }
            } 
            if(ressourceToggle.isSelected()) {
                try {
                    System.out.println("ok");
                    ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(nomFichier.getText());
                    paR.parse();
                } catch (IOException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
        }
    

}
