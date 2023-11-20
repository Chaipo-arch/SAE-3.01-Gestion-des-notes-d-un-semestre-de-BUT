package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.modele.Server;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ImportationDistanceController implements Initializable{


    @FXML
    private ToggleGroup choix;
    @FXML
    private Button annulerButton;

    @FXML
    private Label connexion;

    @FXML
    private Label adresseIP;



    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            
        }
       adresseIP.setText("Votre adresse ip : " + ip.getHostAddress());
    }
    private int i = 0;
    
    @FXML
    void importationDistanceButton(ActionEvent event) {
        
        
            
            connexion.setVisible(true);
            annulerButton.setVisible(true);
             t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.createServer();
                boolean reussi = false;
                while(!Thread.interrupted() && !reussi) {
                    reussi = Server.receiveCSVFile();
                    i++;
                    System.out.println(i);
                }
                annulerAttenteAction(null);
                try { 
                    ParametrageNationalPrototype pNP = new ParametrageNationalPrototype(new File("NationalExporte.csv"));
                    pNP.parse();
                } catch (IOException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MauvaisFormatFichierException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EvaluationException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoteException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
            
    
            /*if(Fichier == CSV) {
                try {
                    System.out.println("Votre FIchier est un fichier de CSV");
                    /TODO verification du Format csv pour l'app
                    //TODO ajout des
                    try {
                        paN = new ParametrageNationalPrototype(file);
                    } catch (EvaluationException ex) {
                        System.out.println("erreur");
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
                    System.out.println("ok");
                    ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(file);
                    try {
                        paR.parse();
                    } catch (EvaluationException ex) {
                        Logger.getLogger(ImporterParametresController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    
                } catch (MauvaisFormatFichierException ex) {
                    System.out.println(ex.getMessage());
                }
            }*/
            
            
            
            
             // Méthode pour vérifier si le fichier est un CSV
            /*boolean isCSV(File file) {
                String extension = getFileExtension(file);
                return extension != null && extension.equals("csv");
            }

            // Obtenir l'extension du fichier
            private String getFileExtension(File file) {
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');
                if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
                    return fileName.substring(lastDotIndex + 1).toLowerCase();
                }
                return null;
            }

            // Utilisation de FileChooser pour sélectionner un fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner un fichier CSV");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                if (isCSV(selectedFile)) {
                    System.out.println("Le fichier est un fichier CSV.");
                } else {
                    System.out.println("Le fichier n'est pas un fichier CSV.");
                }
            } else {
                System.out.println("Aucun fichier sélectionné.");
            }
            */
            t1.start();
            
             System.out.println(t1.getName());
             
            
      
     
            System.out.println("eza");
        
       
    }

    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        Server.closeServer();
        t1.interrupt();
        
        
    }

    

}
