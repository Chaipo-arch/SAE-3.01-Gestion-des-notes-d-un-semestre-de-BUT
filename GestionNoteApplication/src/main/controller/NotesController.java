package GestionNoteApplication.src.main.controller;

import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Stockage;
import javafx.scene.control.TextField;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Server;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

/**
 * Cette classe contrôle l'interface utilisateur associée à la gestion des notes.
 * Elle implémente l'interface Initializable pour initialiser les composants lors du chargement.
 * Elle permet de gérer les interractions de l'utilisateurs avec la fonctionnalité 
 * d'ajout de notes et également les cas d'erreur associé et d'en avertir l'utilisateur
 * @author Alexandres Brouzes, Robin Britelle, Ahmed Bribach
 */
public class NotesController implements Initializable{

    Parent fxml;
    
    @FXML
    private AnchorPane contenuPage;
   
    @FXML
    private Button ValiderChangementButton;

    @FXML
    private Label NomRessource;

    @FXML
    private Label Type;

    @FXML
    private Button AjouterBoutonID;

    @FXML
    private Label LabelNotificationID;

    @FXML
    private GridPane GridPaneR;

    @FXML
    private Button SupprimerBoutonID;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label NomRessource1;

    @FXML
    private Label Type1;
   
    @FXML
    private GridPane Grid;
    private GridPane gridSave;
    int nbRow = 1;
    Evaluation evaluationAAjouter;
   
     // Listes pour stocker les éléments graphiques et les évaluations associées
    private ArrayList<Button> but = new ArrayList();
    private ArrayList<Evaluation> evaluationList = new ArrayList();
   
    // Variables pour stocker les valeurs saisies ou sélectionnées
    private String typeString ="";
    private String dateString ="";
    private double coefficientDouble = 0.0;
    private double noteDouble = 0.0;
   
    // Variables pour gérer les vérifications des données saisies
    private int noteCorrect = 0;
    private boolean typeCorrect = false;
    private boolean dateCorrect = false;
    private boolean coefficientCorrect = false;
    
    // Variables pour gérer les différents états d'erreur
    private boolean flagErreur1 = false;
    private boolean flagErreur2 = false;
    private boolean flagErreur3 = false;
    
    // Messages d'erreur associés à chaque type d'erreur
    private String messageErreur1 = "Erreur : Le type d'une évaluation est manquant";
    private String messageErreur2 = "Erreur : Le total des coefficients d'une ressource doit être compris entre 0 et 100";
    private String messageErreur3 = "WARNING : Attention, certaines évaluations ne possèdent pas de note, elles ne seront pas prises en compte dans le calcul des moyennes";
    
    // Instance de Stockage pour gérer les données
    Stockage stock = Stockage.getInstance();
    
    // Liste pour sauvegarder l'état précédent du GridPane
    ArrayList<Node> sauvegardeGrid = new ArrayList();
    
   
    
    // Méthode d'initialisation appelée lors du chargement de l'interface utilisateur
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Nettoie le GridPane et récupère les identifiants des ressources depuis Stockage
        Grid.getChildren().clear();
        ArrayList<String> ids = Stockage.getInstance().getRessourcesId();
        
        
        // Initialise la liste déroulante avec les identifiants des ressources disponibles
        if(stock.ressources.size() != 0) {
            ObservableList<String> items =  FXCollections.observableArrayList();
            for(String id : ids) {
                items.add(id);
            }
            nbRow = 1;
            Grid.getChildren().clear();
            but.clear();
            comboBox.setItems(items);
            comboBox.getSelectionModel().selectFirst();
            comboBox.setOnAction(e -> comboBoxAction());
            restauration();
            setupGridListeners();
           
        }  else {
            // Affiche un message d'erreur si aucune ressource n'est disponible
            NotificationController.popUpMessage("Il est nécessaire d'impoter votre "
                    + "Programme Nationnal pour utiliser cette fonctionnalité."
                    + "Rendez vous dans :  Parametres > Importer ",
                    "Erreur Parametres d'application manquants");
        }
    }
    
    
    /**
    * Valide les données saisies par l'utilisateur dans l'interfaces de "mes Notes"
    *
    * @param event L'événement déclencheur de l'action.
    * @throws NoteException      En cas d'erreur de note.
    * @throws EvaluationException En cas d'erreur dans l'évaluation.
    */
    @FXML
    void ValiderAction(ActionEvent event) throws NoteException, EvaluationException {

        // Efface les évaluations existantes pour une ressource sélectionnée
        for (Ressource r : stock.ressources) {
            if (r.getIdentifiant().equals(comboBox.getValue())) {
                r.getEvaluation().clear();
            }
        }

        // Vérifie si des ressources sont disponibles dans stock.ressources
        if (stock.ressources.size() != 0) {
            // Initialisation des variables pour le calcul des coefficients
            double totalCoefficient = 0.0;
            Note noteATester = null;
            boolean ajoutEvaluationPossible = true;

            // Parcours des éléments du Grid
            for (int i = 0; i < Grid.getChildren().size(); i++) {
                if (Grid.getChildren().get(i) instanceof TextField) {
                    TextField textFieldAnalyser = (TextField) Grid.getChildren().get(i);
                    int colIndex = GridPane.getColumnIndex(textFieldAnalyser);

                    // Vérification du type d'évaluation
                    if (colIndex == 0) {
                        // Récupère et vérifie le type d'évaluation
                        // Si le type est manquant, affiche une erreur visuelle
                        // et enregistre le flag d'erreur correspondant
                        typeString = textFieldAnalyser.getText();
                        if (typeString.equals("") || typeString == null) {
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                            if (!flagErreur3) {
                                flagErreur3 = true;
                            }
                        } else {
                            typeCorrect = true;
                            textFieldAnalyser.setStyle(STYLE);
                        }
                    }

                    // Gestion de la date de l'évaluation
                    if (colIndex == 1) {
                        // Récupère et affiche la date, aucun traitement d'erreur requis ici
                        dateString = textFieldAnalyser.getText();
                        textFieldAnalyser.setStyle(STYLE + "-fx-background-color: white;");
                    }

                    // Vérification du coefficient de l'évaluation
                    if (colIndex == 2) {
                        try {
                            // Récupère et valide le coefficient
                            coefficientDouble = Double.parseDouble(textFieldAnalyser.getText().replace(',', '.'));

                            // Vérifie si le coefficient est valide et l'ajoute au total des coefficients
                            // Affiche une erreur visuelle sur le TextField si le coefficient est invalide
                            if (!Evaluation.isCoefficient(coefficientDouble)) {
                                textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                            } else {
                                coefficientCorrect = true;
                                totalCoefficient += coefficientDouble;
                                textFieldAnalyser.setStyle(STYLE);
                            }
                        } catch (Exception e) {
                            // Capture des exceptions de conversion et affichage d'une erreur visuelle
                            // Enregistre le flag d'erreur correspondant
                            if (!flagErreur1) {
                                flagErreur1 = true;
                            }
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                        }
                    }

                    // Gestion de la note de l'évaluation
                    if (colIndex == 3) {
                        try {
                            // Vérifie si la note est vide ou invalide
                            // Affiche une erreur visuelle si la note est vide
                            if (textFieldAnalyser.getText() == null || textFieldAnalyser.getText().equals("")) {
                                noteCorrect = 1;
                                textFieldAnalyser.setStyle(STYLE + "-fx-border-color: orange;" + "-fx-border-width: 2px;");
                            } else {
                                noteATester = new Note(Double.parseDouble(textFieldAnalyser.getText().replace(',', '.')));
                                if (Note.isNote(noteATester.getNote())) {
                                    noteCorrect = 2;
                                    noteDouble = Double.parseDouble(textFieldAnalyser.getText());
                                    textFieldAnalyser.setStyle(STYLE);
                                } else {
                                    textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                                }
                            }
                        } catch (Exception e) {
                            // Capture des exceptions de conversion et affichage d'une erreur visuelle
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                        }
                    }
                } else if (typeCorrect && coefficientCorrect && noteCorrect != 0) {
                    // Création d'une nouvelle évaluation si toutes les données sont correctes
                    // et ajout de cette évaluation à la ressource sélectionnée
                    // Gestion des erreurs d'évaluation incorrecte
                    if (noteCorrect == 2) {
                        // Crée une note et une évaluation pour l'ajouter à la ressource
                        Note noteAAjouter = new Note(noteDouble);
                        evaluationAAjouter = new Evaluation(NomRessource.getText(), noteAAjouter,
                                typeString, coefficientDouble, dateString);

                        NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Vos données ont été insérées");
                    } else {
                        // Crée une évaluation avec une note invalide et enregistre l'erreur correspondante
                        evaluationAAjouter = new Evaluation(NomRessource.getText(), new Note(-1),
                                typeString, coefficientDouble, dateString);
                        if (!flagErreur2) {
                            flagErreur2 = true;
                        }
                    }

                    // Ajoute l'évaluation à la ressource si le total des coefficients est valide
                    if (totalCoefficient <= 100 && totalCoefficient > 0) {
                        for (Ressource r : stock.ressources) {
                            if (r.getIdentifiant().equals(comboBox.getValue())) {
                                if (!r.ajouterEvaluation(evaluationAAjouter)) {
                                    String messageErreurSimi = "Des évaluations sont similaires" + STYLE;
                                    NotificationController.popUpMessage(messageErreurSimi, "");
                                }
                            }
                        }
                        sauvegardeGrid();
                        AccueilController.mesNoteCourant = false;
                    }

                    // Réinitialise les flags de données correctes et incorrectes
                    noteCorrect = 0;
                    typeCorrect = false;
                    coefficientCorrect = false;
                }
            }

            // Gestion des messages d'erreur en fonction des flags d'erreur, permet d'eviter une redondance dans 
            // l'affichage des alertes
            if (flagErreur1 && flagErreur2 && flagErreur3) {
                NotificationController.popUpMessage(
                        messageErreur1 + "\n" + messageErreur2 + "\n" + messageErreur3,
                        "Des données sont absentes ou incorrectes");
            } else if (flagErreur1 && flagErreur3) {
                NotificationController.popUpMessage(
                        "" + messageErreur1 + "\n" + messageErreur2,
                        "Des données sont absentes ou incorrectes");
            } else if (flagErreur1) {
                NotificationController.popUpMessage(
                        "" + messageErreur2,
                        "Des données sont absentes ou incorrectes");
            } else if (flagErreur3) {
                NotificationController.popUpMessage(
                        "" + messageErreur1,
                        "Des données sont absentes ou incorrectes");
            } else if (flagErreur2) {
                NotificationController.popUpMessage(
                        "" + messageErreur3,
                        "Attention");
            }

            // Met en évidence visuellement les champs avec des coefficients invalides
            if (totalCoefficient > 100 || totalCoefficient < 0) {
                for (int j = 2; j < Grid.getChildren().size(); j += 5) {
                    Grid.getChildren().get(j).setStyle(STYLE + "-fx-border-color: red;");
                }
            }

        } else {
            // Affiche un message si aucune ressource n'est disponible
            NotificationController.popUpMessage(
                    "L'ajout d'évaluations est impossible tant que vos paramètres n'ont pas été importés :"
                            + "\nRendez-vous dans Paramètres > Importer",
                    "");
        }

        // Réinitialisation des flags d'erreur après traitement
        flagErreur1 = false;
        flagErreur2 = false;
        flagErreur3 = false;
    }  
    
    
   
    private final String STYLE = "-fx-padding: 5px 10px;" + // Padding haut et bas 5px, gauche et droite 10px
            "-fx-pref-height: 25px;" + // Hauteur préférée
            "-fx-pref-width: 150px;" + // Largeur préférée
            "-fx-border-color: black;" + // Couleur de la bordure
            "-fx-border-width: 2px;"; // Épaisseur de la bordure
   
    private final String STYLE_SUP =
                "-fx-background-color: #ff6347;"+
                "-fx-text-fill: white;"+
                "-fx-padding: 5px 10px;"+
                "-fx-min-width: 100px;";
    
    
    
    /**
    * Action déclenchée lors de l'ajout d'une évaluation.
    *
    * @param event L'événement déclencheur de l'action.
    */
    @FXML
    void AjouterEvaluationAction(ActionEvent event) {
        // Crée un bouton "Supprimer" associé à la nouvelle évaluation
        Button supprimer = new Button("Supprimer");
        supprimer.setId(nbRow + ""); // Attribue un identifiant unique
        but.add(supprimer); // Ajoute le bouton à la liste pour le suivi des actions

        // Action associée au bouton "Supprimer"
        
        supprimer.setOnAction(events -> {
        try {
            SupprimerAction(but.indexOf(supprimer));
        } catch (Exception e) {
            // Gérer l'exception ici, par exemple, imprimer le message d'erreur
            e.printStackTrace();
        }
    });
    
        supprimer.setStyle(STYLE_SUP); // Applique le style CSS approprié

        // Création de champs de texte pour l'évaluation avec un style CSS spécifique
        TextField textField1 = new TextField();
        textField1.setStyle("-fx-background-color: #f2f2f2;" + STYLE);

        TextField textField2 = new TextField();
        textField2.setStyle("-fx-background-color: #f2f2f2;" + STYLE);

        TextField textField3 = new TextField();
        textField3.setStyle("-fx-background-color: #f2f2f2;" + STYLE);

        TextField textField4 = new TextField();
        textField4.setStyle("-fx-background-color: #f2f2f2;" + STYLE);

        // Ajout des champs de texte et du bouton "Supprimer" à la nouvelle ligne du Grid
        Grid.addRow(nbRow, textField1, textField2, textField3, textField4, supprimer);
        nbRow++; // Incrémente le compteur pour l'ajout ultérieur

        // Met à jour la sauvegarde de la grille
        modification(sauvegardeGrid);
    }

    

    /**
    * Action déclenchée lors de la suppression d'une évaluation.
    *
    * @param supprimerId Identifiant de l'évaluation à supprimer.
    */
    private void SupprimerAction(int supprimerId) throws NoteException{    
        Ressource r = getRessource(); // Récupère la ressource concernée
        
                // Vérifie si l'identifiant correspond à celui de l'évaluation à supprimer
                
        for(int i=0;i < stock.competences.size();i++){
            for(int indice = 0 ; indice <stock.competences.get(i).getRessources().size() ; indice++){
                if(stock.competences.get(i).getRessources().get(indice).getIdentifiant() == r.getIdentifiant()){

                    stock.ressources.get(indice).getEvaluation().get(supprimerId).setNote(-1.0);
                    System.out.println(stock.ressources.get(indice).getEvaluation().get(supprimerId));
                }
            }
        }
                
           

        // Supprime visuellement la ligne correspondante à l'évaluation
        Grid.getChildren().remove(supprimerId * 5, supprimerId * 5 + 5);
        but.remove(supprimerId); // Retire le bouton de la liste de suivi

        // Crée une copie de la grille pour réorganiser les données
        ArrayList<Node> copieGrid = new ArrayList<>();
        for (int i = 0; i < Grid.getChildren().size(); i++) {
            copieGrid.add(Grid.getChildren().get(i));
        }

        Grid.getChildren().clear(); // Efface la grille actuelle
        nbRow = 1; // Réinitialise le compteur de ligne
        // Réinsère les éléments de la copie de la grille dans la grille
        for (int i = 0; i < copieGrid.size(); i += 5) {
            Grid.addRow(nbRow, copieGrid.get(i), copieGrid.get(i + 1), copieGrid.get(i + 2), copieGrid.get(i + 3), copieGrid.get(i + 4));
            nbRow++;
        }

        modification(sauvegardeGrid); // Met à jour la sauvegarde de la grille
    }



    /**
    * Action déclenchée lors du changement de sélection dans la ComboBox.
    */
    public void comboBoxAction() {
        modification(sauvegardeGrid); // Effectue une modification dans la grille et sauvegarde les changements

        // Vérifie si une action n'a pas encore etait sauvegarder
        if (AccueilController.mesNoteCourant) {
            // Affiche une boîte de dialogue pour confirmer l'abandon des modifications non sauvegardées
            Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?", "");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Si l'utilisateur confirme l'abandon, réinitialise la grille avec les données précédentes
                if (stock.ressources.size() != 0) {
                    nbRow = 1;
                    Grid.getChildren().clear();
                    but.clear();
                    restauration();
                }
            }
        } else {
            // Si aucune note n'est en cours d'édition, réinitialise simplement la grille avec les données précédentes
            if (stock.ressources.size() != 0) {
                nbRow = 1;
                Grid.getChildren().clear();
                but.clear();
                restauration();
            }
        }

        AccueilController.mesNoteCourant = false; // Indique qu'il n'y a pas de modifications en cours sur la page
    }
    
    
    /**
    * Restaure les données des évaluations dans la grille pour une ressource sélectionnée.
    */
    public void restauration(){
        boolean nonPasse = true;
        for(Ressource r : stock.ressources) {
            if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
                NomRessource.setText(r.getLibelle()); // Met à jour le nom de la ressource dans l'IHM
                Type.setText(r.getType()); // Met à jour le type de la ressource dans l'IHM
                
                if(r.getEvaluation().size()!= 0){ //Cree et affiche les differentes évalutation enregistrer dans la Ressource
                    for(Evaluation e : r.getEvaluation()){
                        nonPasse = false;
                        Button supprimer = new Button("supprimer");
                        supprimer.setId(nbRow+"");
                        TextField textField1 = new TextField(e.getType());
                        textField1.setStyle(STYLE);        
                        TextField textField2 = new TextField(e.getDate());
                        textField2.setStyle(STYLE);    
                        TextField textField3 = new TextField(e.getCoefficient()+"");
                        textField3.setStyle(STYLE);
                        TextField textField4;
                        if(e.getNote() == -1){
                            textField4 = new TextField("");
                        } else {
                            textField4 = new TextField(e.getNote()+"");
                        }                      
                        textField4.setStyle(STYLE);
                        supprimer.setStyle(STYLE_SUP);
                        Grid.addRow(nbRow,textField1,textField2,textField3,textField4,supprimer);
                        but.add(supprimer);
                       supprimer.setOnAction(events -> {
                        try {
                            SupprimerAction(but.indexOf(supprimer));
                        } catch (Exception b) {
                            // Gérer l'exception ici, par exemple, imprimer le message d'erreur
                            b.printStackTrace();
                        }
                    });
                        nbRow ++;
                       
                    }
                }
            }
        }  
        sauvegardeGrid();
    }
    
    
    
    /**
    * Vérifie s'il y a eu des modifications dans la grille par rapport à une sauvegarde précédente.
    *
    * @param sauvegardeGrid La liste des nœuds de sauvegarde de la grille précédente
    */
    public void modification(ArrayList<Node> sauvegardeGrid) {
        boolean resultat = false; 

        // Vérifie si la taille de la grille a changé par rapport à la sauvegarde précédente
        if (Grid.getChildren().size() != sauvegardeGrid.size()) {
            resultat = true; 
        } else {
            // Parcourt les nœuds de la grille actuelle et de la sauvegarde pour comparer les valeurs
            for (int i = 0; i < Grid.getChildren().size(); i++) {
                // Vérifie si le nœud est un champ de texte
                if (sauvegardeGrid.get(i) instanceof TextField) {
                    // Compare le texte du champ de texte actuel avec celui de la sauvegarde
                    if (!((TextField) Grid.getChildren().get(i)).getText().equals(((TextField) sauvegardeGrid.get(i)).getText())) {
                        resultat = true; // Met à jour le statut de modification à vrai
                    }
                } else {
                    // Compare les identifiants des nœuds s'ils ne sont pas des champs de texte
                    if (!Grid.getChildren().get(i).getId().equals(sauvegardeGrid.get(i).getId())) {
                        resultat = true; 
                    }
                }
            }
        }

        AccueilController.mesNoteCourant = resultat; 
    }
    
    
    
    /**
    * Sauvegarde l'état actuel de la grille dans la variable 'sauvegardeGrid'.
    * Parcourt les éléments de la grille et les stocke dans une liste.
    */
   public void sauvegardeGrid(){
       ArrayList<Node> sauvegarde = new ArrayList();
       int nbRowSauvegarde = 1;
       for(int i = 0; i < Grid.getChildren().size(); i++){
           if(Grid.getChildren().get(i) instanceof TextField){
               // Crée un nouveau champ de texte avec la valeur actuelle du champ dans la grille
               TextField tx = new TextField(((TextField)Grid.getChildren().get(i)).getText());
               sauvegarde.add(tx); 
           } else {
               sauvegarde.add(Grid.getChildren().get(i)); 
           }
       }   
       sauvegardeGrid = sauvegarde; // Met à jour la variable de sauvegarde globale
   }
    
    
    /**
    * Récupère la ressource associée à la valeur sélectionnée dans la comboBox et la return
    * @return La ressource correspondante ou null si non trouvée.
    */
   public Ressource getRessource(){
       boolean nonPasse = true;  
       for(Ressource r : stock.ressources) {
           if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
               return r; 
           }
       }
       return null; // Aucune ressource correspondante trouvée
   }

    
    /**
    * Vérifie les modifications dans la grille et déclenche une action si détectée.
    */
    private void setupGridListeners() {
        for (Node node : Grid.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.textProperty().addListener((observable) -> {
                    int row = Grid.getRowIndex(textField);
                    modification(sauvegardeGrid);
                });
            }
        }
    }

    
    /**
    * Gère le clic sur le bouton "Notice".
    * Vérifie les modifications sur la page des évaluations et redirige vers la page de la notice si nécessaire.
    */
    @FXML
    void NoticeClick() {
        try {
            NoticeController.pageRetour = "notes.fxml";
            NoticeController.y = 6.3;
            modification(sauvegardeGrid); 
            if(AccueilController.mesNoteCourant){
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    AccueilController.accueil.changerPage("Notice.fxml");
                }
            } else {
                AccueilController.accueil.changerPage("Notice.fxml");
            }
        } catch (IOException ex) {
            // Gère les exceptions d'entrée/sortie (IOException)
        }
    }

    
    
}