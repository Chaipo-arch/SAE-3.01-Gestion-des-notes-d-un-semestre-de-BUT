package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.Cryptage;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.Server;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.File;
import java.io.FileNotFoundException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


/**
 * Classe controller de l'ihm, permettant l'importation des Parametres National ou des Parametres Ressources
 * Elle lance les methodes de cryptages et initialise le thread et la communnication client/server
 * @author Enzo.Cluzel, Robin.Britelle
 */
public class ImportationDistanceController implements Initializable{

    private InetAddress ip = null;
    @FXML 
    private CheckBox nationalToggle;
    
    @FXML
    private Button annulerButton;

    @FXML
    private Label connexion;

    @FXML
    private Button btnAdresseIP;

    @FXML
    private  CheckBox ressourceToggle;
    
    private final String STYLE = "-fx-font-size: 32px";
    private final String STYLE2 = "-fx-font-size: 25px";


    /**
     * Recupére l'addresse ip de l'ordinateur 
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            
        }
       
    }
    private int i = 0;
   
    /**
     * Cree une fonction que le thread va utiliser, puis lance le thread et affiche les labels de connexion
     * Le thread ajoute des fichiers qui vont être envoyé selon ce qui est coché, puis le serveur est crée 
     * et connecte un autre ordinateur selon le nombre de fichiers voulu, et recoit les fichers demandés
     * une fois reçus, les fichiers sont lues et crée les instances selon leur contenu
     * une reponse correspondante aux fichiers lue et envoyé a l'ordinateur qui a communiquer
     * @param event click sur le button
     */
    @FXML
    void importationDistanceButton(ActionEvent event) {
        // Vérifie si l'un des deux choix est sélectionné
        if (ressourceToggle.isSelected() || nationalToggle.isSelected()) {
            // Affiche les éléments de connexion a l'utilisateur
            connexion.setVisible(true);
            annulerButton.setVisible(true);

            // Crée un thread pour gérer l'importation/exportation 
            t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ArrayList<String> fichier = new ArrayList<>();

                        // Si le toggle "National" est sélectionné, ajoute le fichier "NationalExporte.csv" au tableau
                        if (nationalToggle.isSelected()) {
                            System.out.println("Le fichiers : NationalExporte.csv à était crée");
                            fichier.add("NationalExporte.csv");
                        }

                        // Si le toggle "Ressource" est sélectionné, ajoute le fichier "RessourceExporte.csv" au tableau
                        if (ressourceToggle.isSelected()) {
                            System.out.println("Le fichiers : RessourceExporte.csv à était crée");
                            fichier.add("RessourceExporte.csv");
                        }

                        // Crée un serveur
                        Server.createServer();
                        
                        boolean reussi = false;
                        boolean correct = true;

                   
                        while (!Thread.interrupted() && !reussi) {
                            
                            Server.connexion();
                            boolean ok;
                            ok = Server.cle();
                            System.out.println(Cryptage.bOUa);
                            String codeBob = Cryptage.bOUa + "";
                            Server.reponse(codeBob);


                            Server.closeClient();

                            for (int i = 0; i < fichier.size() && correct; i++) {
                                correct = Server.connexion();
                                if (correct) {
                                    System.out.println(fichier.get(i));
                                    reussi = Server.receiveCSVFile(fichier.get(i));

                                    // Verifie si ce n'est pas le dernier fichier, et ferme le client en consequence
                                    if (fichier.size() != i + 1) {
                                        Server.closeClient();
                                    } else if (reussi) {
                                        System.out.println("Fichier " + fichier.get(i) + " reçu");
                                    } else {
                                        System.out.println("Fichiers non reçus");
                                    }
                                }
                            }

                            // En cas de problème de connexion, ferme le client
                            if (!correct) {
                                System.out.println("Problème de connexion");
                                Server.closeClient();
                            }
                        }

                        // Si l'importation est réussie
                        if (reussi) {
                            System.out.println("Envoie en cours");
                            String message = "CSV reçu";

                            try {
                                // Traite les fichiers reçus en fonction des toggles sélectionnés
                                if (nationalToggle.isSelected()) {
                                    ParametrageNationalPrototype pNP = new ParametrageNationalPrototype(new File("NationalExporte.csv"));
                                    pNP.parse();
                                }
                                if (ressourceToggle.isSelected()) {
                                    ParametrageRessourcePrototype pRP = new ParametrageRessourcePrototype(new File("RessourceExporte.csv"));
                                    pRP.parse();
                                }

                            } catch (IOException | MauvaisFormatFichierException | EvaluationException | NoteException ex) {
                                // En cas d'erreur lors du traitement des fichiers, récupère le message d'erreur
                                System.out.println(ex.getMessage());
                                message = ex.getMessage();
                            }

                            System.out.println(message);

                            // Envoie la réponse au client
                            Server.reponse(message);
                            Server.closeClient();
                        }

                        // Annule l'action d'attente pour le Server
                        annulerAttenteAction(null);
                    } catch (IOException ex) {
                        // En cas d'erreur d'entrée/sortie
                    }
                }
            });

            // Lance le thread
            t1.start();
        } else {
            // Affiche un message si aucun toggle n'est sélectionné
            NotificationController.popUpMessage("Merci de sélectionner le fichier que vous voulez recevoir lors de l'importation", "");
        }
    }

    /**
     * Arrete le thread, et le serveur 
     * @param event 
     */
    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        Server.closeServer();
        t1.interrupt();
        
        
        
    }
    
    
    /**
     * Methodes Permettant d'afficher son adress IP
     * @param event 
     */
    @FXML
    void changeText(ActionEvent event) {
        if (btnAdresseIP.getText().equals("cliquer pour voir Mon adresse IP :")) {
            btnAdresseIP.setText(ip.getHostAddress());
            btnAdresseIP.setStyle(STYLE);
        } else {
            btnAdresseIP.setText("cliquer pour voir Mon adresse IP :");
            btnAdresseIP.setStyle(STYLE2);
        }
    }
}
