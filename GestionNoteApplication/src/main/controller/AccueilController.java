
/*
 *
 */


package GestionNoteApplication.src.main.controller;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.GestionNote;
import GestionNoteApplication.src.main.java.package1.Server;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Contrôleur FXML pour la page d'accueil.
 * Gère les fonctionnalités de la page d'accueil de l'application.
 * Implémente Initializable pour l'initialisation du contrôleur.
 * @author enzo.cluzel, robin.britelle
 */
public class AccueilController implements Initializable {
   
    public static AccueilController accueil ;
    @FXML
    private TextField UserTextField;

    @FXML
    private Label userLabel;

    @FXML
    private AnchorPane idaccueil;

    @FXML
    private Label BtnAccueil;
    
    private Stage fenetreActive;

    /**
     * Définit la fenêtre active.
     * @param fenetre La fenêtre active
     */
    public void setFenetre(Stage fenetre) {
        fenetreActive = fenetre;
    }

    @FXML
    private Label textePresentation;
   
    @FXML
    private AnchorPane contenuPage;
   
   
    Parent fxml;
    private ArrayList<Node> sauvegardeAccueil;
    
    @FXML
    private Label TextTitre;
   
    static boolean mesNoteCourant;
    
    /**
     * Initialise la classe du contrôleur.
     * @param url L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine
     * @param rb Les ressources à utiliser par ce contrôleur
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation des variables
        accueil = this;
        mesNoteCourant = false;
       
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
            try {
                // Récupèration des données
                GestionNote.recupererDonnees();
                
                UserTextField.setText(Stockage.getInstance().getUserName());
                textePresentation.setText("Bienvenue " + UserTextField.getText() + " sur votre application de Gestion de Note");
            } catch (IOException ex) {
               // Gestion de l'exception d'entrée/sortie
            } catch (ClassNotFoundException ex) {
               // Gestion de l'exception de classe non trouvée
            }
       sauvegardeAccueil = new ArrayList();
       sauvegardeAccueil.addAll(contenuPage.getChildren());
    }
   
    @FXML
    void AccueilActionBouton() {
        // Vérifie si des évaluations sont en cours
        if (mesNoteCourant) {
            // Affiche une boîte de dialogue de confirmation pour abandonner les évaluations non sauvegardées
            Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. Voulez-vous vraiment abandonner vos évaluations non sauvegardées ?", "");

            // Vérifie si l'utilisateur a confirmé l'abandon des évaluations
            if (result.isPresent() && result.get() == ButtonType.OK) {
                TextTitre.setText("Application Gestion Notes");

                // Arrête le serveur si actif
                if (t1 != null && t1.isAlive()) {
                    Server.closeServer();
                    t1.interrupt();
                }

                // Charge la sauvegarde de la page d'accueil et désactive les évaluations en cours
                contenuPage.getChildren().setAll(sauvegardeAccueil);
                mesNoteCourant = false;
            }
        } else {
            // Affiche la page d'accueil par défaut
            TextTitre.setText("Application Gestion Notes");

            // Arrête le serveur si actif
            if (t1 != null && t1.isAlive()) {
                Server.closeServer();
                t1.interrupt();
            }

            // Charge la sauvegarde de la page d'accueil
            contenuPage.getChildren().setAll(sauvegardeAccueil);
        }
    }
   
    @FXML
    void mesNotesActionBtn() {
        try {
            // Vérifie si aucune évaluation n'est en cours
            if (!mesNoteCourant) {
                // Affiche la page "Mes Notes"
                TextTitre.setText("Mes Notes");
                changerPage("notes.fxml");
            }
        } catch (IOException ex) {
            // Gère les exceptions liées à la lecture de la page "Mes Notes"
        }
    }
    
    
    @FXML
    void AjouterEvaluationActionButton() {
        try {
            if (mesNoteCourant) {
                // Affiche une boîte de dialogue de confirmation pour abandonner les évaluations non sauvegardées
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. Voulez-vous vraiment abandonner vos évaluations non sauvegardées ?", "");

                // Vérifie si l'utilisateur a confirmé l'abandon des évaluations
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    TextTitre.setText("Vos Moyennes");

                    // Charge la page "Vos Moyennes" et désactive les évaluations en cours
                    changerPage("calculerMoyenne.fxml");
                    mesNoteCourant = false;
                }
            } else {
                // Charge la page "Vos Moyennes" par défaut
                changerPage("calculerMoyenne.fxml");
                TextTitre.setText("Vos Moyennes");
            }
        } catch (IOException ex) {
            // Gère les exceptions liées à la lecture de la page "Vos Moyennes"
        }
    }

   

   

    @FXML
    void ParametreActionButton() {
        try {
            // Vérifie si des évaluations sont en cours
            if (mesNoteCourant) {
                // Affiche une boîte de dialogue de confirmation pour abandonner les évaluations non sauvegardées
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. Voulez-vous vraiment abandonner vos évaluations non sauvegardées ?", "");

                // Vérifie si l'utilisateur a confirmé l'abandon des évaluations
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    TextTitre.setText("Parametres");

                    // Charge la page "Parametres" 
                    changerPage("Parametres.fxml");
                    mesNoteCourant = false;
                }
            } else {
                // Charge la page "Parametres" par défaut
                TextTitre.setText("Parametres");
                changerPage("Parametres.fxml");
            }
        } catch (IOException ex) {
            // Gère les exceptions liées à la lecture de la page "Parametres"
        }
    }

    @FXML
    void QuitterActionButton() {
        Optional<ButtonType> result;

        // Vérifie si des modifications sur la page ont eu lieu
        if (mesNoteCourant) {
            // Affiche une boîte de dialogue de confirmation pour abandonner les évaluations non sauvegardées et quitter l'application
            result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. Voulez-vous vraiment abandonner vos évaluations non sauvegardées et quitter l'application ?", "");

            mesNoteCourant = false;
        } else {
            // Affiche une boîte de dialogue pour confirmer la sortie de l'application
            result = NotificationController.popUpChoix("", "Souhaitez-vous quitter l'application ?");
        }

        // Vérifie si l'utilisateur a confirmé la sortie de l'application
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Arrête le serveur s'il est actif
            if (t1 != null && t1.isAlive()) {
                Server.closeServer();
                t1.interrupt();
            }

            // Enregistre les données et ferme l'application
            GestionNote.enregistrerDonnees();
            System.exit(0);
        }
    }


   
    /**
    * Methodes permettant de changer la page courantes
    * @param page le nom du fichier FXML de la page à charger
    * @throws IOException si une erreur survient lors de la lecture du fichier FXML
    */
    public void changerPage(String page) throws IOException {
        // Vérifie si la page à charger est la page d'accueil
        if (page.equals("accueil.fxml")) {
            // Charge la page d'accueil en appelant la méthode AccueilActionBouton()
            AccueilActionBouton();
        } else {
            // Vérifie si le serveur est actif et l'arrête le cas échéant
            if (t1 != null && t1.isAlive()) {
                Server.closeServer();
                t1.interrupt();
            }

            // Crée un objet File pour la page à charger
            File file = new File("src/GestionNoteApplication/src/ressources/fxml/" + page);

            // Vérifie si le fichier de la page existe
            if (file.exists()) {
                // Construit le chemin relatif de la page à charger
                String changementPage = "../../ressources/fxml/" + page;

                // Affiche le chemin absolu du fichier de la page
                System.out.println(file.getAbsolutePath());

                // Charge le fichier FXML dans le contenu de la page
                fxml = FXMLLoader.load(getClass().getResource(changementPage));
                contenuPage.getChildren().removeAll();
                contenuPage.getChildren().setAll(fxml);
            }
        }
    }
  
    @FXML
    void userTextChanged() {
        // Met à jour le nom d'utilisateur dans le Stockage
        Stockage.getInstance().setUserName(UserTextField.getText());
        // Met à jour le texte de présentation
        textePresentation.setText("Bienvenue " + UserTextField.getText() + " sur votre application de gestion de notes");
    }
    
    @FXML
    void NoticeClick() {
        try {
            // Configure la page de retour et la position sur la page d'accueil
            NoticeController.pageRetour = "accueil.fxml";
            NoticeController.y = 0;
            // Affiche la page de la Notice
            TextTitre.setText("Notice");
            changerPage("Notice.fxml");
        } catch (IOException ex) {
            // Gère les exceptions liées à la lecture de la page Notice
        }
    }
}

