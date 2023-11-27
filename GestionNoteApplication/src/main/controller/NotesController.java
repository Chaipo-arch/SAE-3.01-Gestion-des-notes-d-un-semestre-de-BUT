package GestionNoteApplication.src.main.controller;

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
import java.util.HashMap;
import javafx.scene.Node;


public class NotesController implements Initializable{

    
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

    int nbRow = 1;
    
    TextField type = new TextField();
    TextField date = new TextField();
    TextField poids = new TextField();
    TextField note = new TextField();
    
    private ArrayList<Button> but = new ArrayList();
    private ArrayList<Evaluation> evaluationList = new ArrayList();
    
    private String typeString ="";
    private String dateString ="";
    private double coefficientDouble = 0.0;
    private double noteDouble = 0.0;
    
    private int noteCorrect = 0;
    private boolean typeCorrect = false;
    private boolean dateCorrect = false;
    private boolean coefficientCorrect = false;
    Stockage stock = Stockage.getInstance();
    ArrayList<Node> sauvegardeGrid = new ArrayList();
    HashMap<Ressource, ArrayList<Node>> map;
    /**
     * Initializes the controller class.
     * @param url
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//Stockage.getInstance().supprimerDonnees();
        Grid.getChildren().clear();
        //System.out.println(" 1");
        ArrayList<String> ids = Stockage.getInstance().getRessourcesId();
        if(Stockage.getInstance().ressources.size() != 0) {
            //System.out.println(" 2");
            
            ObservableList<String> items =  FXCollections.observableArrayList();
            for(String id : ids) {
                items.add(id);
            }
            comboBox.setItems(items);
            comboBox.getSelectionModel().selectFirst();
            comboBox.setOnAction(e -> comboBoxAction());
            comboBoxAction();
            
        }  else {
            NotificationController.popUpMessage("Il est nécessaire d'impoter votre "
                    + "Programme Nationnal pour utiliser cette fonctionnalité."
                    + "Rendez vous dans Parametres > Importer ", 
                    "Erreur Parametres d'application manquants");
        }
    }
    @FXML
    void ValiderAction(ActionEvent event) throws NoteException, EvaluationException {
        LabelNotificationID.setText("");
        for(Ressource r : stock.ressources) {
            if(r.getIdentifiant().equals(comboBox.getValue())) {
            	r.getEvaluation().clear();
            }
        }
        evaluationList.clear();
        if(Stockage.getInstance().ressources.size() != 0) {
            double totalCoefficient = 0.0;
            Note noteATester = null;
            boolean ajoutEvaluationPossible = true;
            for (int i = 0; i < Grid.getChildren().size(); i++) {
                if (Grid.getChildren().get(i) instanceof TextField) {
                    TextField textFieldAnalyser = (TextField) Grid.getChildren().get(i);

                    int colIndex = GridPane.getColumnIndex(textFieldAnalyser);

                    if (colIndex == 0){
                        typeString = textFieldAnalyser.getText();
                        if(typeString.equals("")||typeString == null){      
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                        } else { 
                            typeCorrect = true;
                            textFieldAnalyser.setStyle(STYLE);    
                        }
                    }
                    if (colIndex == 1){
                        dateString = textFieldAnalyser.getText();
                        /*if(dateString.equals("")||dateString == null){
                            textFieldAnalyser.setStyle("-fx-background-color: red;");
                        } else { 
                            dateCorrect = true;*/
                        textFieldAnalyser.setStyle(STYLE + "-fx-background-color: white;");
                        //}
                    }
                    if (colIndex == 2){
                        try {
                            coefficientDouble = Double.parseDouble(textFieldAnalyser.getText());
                            if(!Evaluation.isCoefficient(coefficientDouble)){
                                textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                            } else {
                                coefficientCorrect = true;
                                totalCoefficient += coefficientDouble;
                                textFieldAnalyser.setStyle(STYLE);
                            }
                        } catch (Exception e ){
                            //System.out.println(e);
                            LabelNotificationID.setText("Un ou plusieur coefficient ne correcponde pas au champs attendu");
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                        }  
                    }
                    if (colIndex == 3){                   
                        try {
                            if(textFieldAnalyser.getText() == null 
                                    || textFieldAnalyser.getText().equals("")){
                                noteCorrect = 1;
                                textFieldAnalyser.setStyle(STYLE + "-fx-border-color: orange;" + "-fx-border-width: 2px;");
                            } else {
                                noteATester = new Note(Double.parseDouble(textFieldAnalyser.getText()));
                                if(Note.isNote(noteATester.getNote())){
                                    noteCorrect = 2;
                                    noteDouble = Double.parseDouble(textFieldAnalyser.getText());
                                    textFieldAnalyser.setStyle(STYLE);
                                } else {
                                    textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                                }
                            }
                        } catch (Exception e ){
                            //System.out.println(e);
                            textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");   
                        }  
                    }   
                } else if(typeCorrect && coefficientCorrect && noteCorrect != 0) {
                    if(noteCorrect == 2){
                        Note noteAAjouter = new Note(noteDouble);
                        evaluationList.add(new Evaluation(NomRessource.getText(),noteAAjouter 
                                               ,typeString,coefficientDouble,dateString));
                        NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Vos données ont bien était insérer");
                        
                    } else {
                        evaluationList.add(new Evaluation(NomRessource.getText(),new Note(-1) 
                                               ,typeString,coefficientDouble,dateString));
                        NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Vos données ont était insérer");
                        NotificationController.popUpMessage("Certaine Ligne ne seront pas prise en comptes lors du calcul de moyenne car des "
                                + "information sont manquantes (Note et/ou Date)", "Avertissement");
                        //LabelNotificationID.setText("Attention certaines évaluation ne seront pas pris en compte lors du calcul de la moyenne");
                         
                    }  
                    noteCorrect = 0;
                    typeCorrect = false;
                    //dateCorrect = false;
                    coefficientCorrect = false;
                    
                    
                } else{
                    ajoutEvaluationPossible = false;
                }
            }
            if(ajoutEvaluationPossible){
                //System.out.println(totalCoefficient);
                if(totalCoefficient == 100){
                    for(Ressource r : stock.ressources) {
                        if(r.getIdentifiant().equals(comboBox.getValue())) {
                            for(int i = 0; i <Grid.getChildren().size(); i++){
                                sauvegardeGrid.add(Grid.getChildren().get(i));
                            }
                            r.getEvaluation().clear();
                            for(Evaluation e : evaluationList) {
                                if(!r.ajouterEvaluation(e)){
                                    LabelNotificationID.setText("Erreur des evaluation sont similaire" + STYLE);
                                    //TODO quand 2 éval sont identiques
                    		}
                            }                           
                        }
                    }
                }else{            	
                    for(int i = 2; i < Grid.getChildren().size(); i+= 5){
                        Grid.getChildren().get(i).setStyle("-fx-border-color: red;" + STYLE);
                    }
                    LabelNotificationID.setText("total des coefficient != de 100");  
                }
            }
        } else {
            NotificationController.popUpMessage("Il est nécessaire d'impoter votre "
                    + "Programme Nationnal pour utiliser cette fonctionnalité."
                    + "Rendez vous dans Parametres > Importer ", 
                    "Erreur Parametres d'application manquants"
            );
            LabelNotificationID.setText("Impossible d'ajout� les évaluations, veuillez importé un paramétrage");  
        }   
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
    @FXML
    void AjouterEvaluationAction(ActionEvent event) {
        Button supprimer = new Button("Supprimer");
        supprimer.setId(nbRow+"");
        but.add(supprimer);
        supprimer.setOnAction(events -> SupprimerAction(but.indexOf(supprimer)));
        
        supprimer.setStyle(STYLE_SUP);
        //supprimer.getStyleClass().add("button-supprimer"); // Ajout de la classe CSS au bouton

        // Création des champs de texte avec la classe CSS associée
        //TODO utilisation d'une classe CSS plutot que insertion dans le code a Favoriser
        TextField textField1 = new TextField();
        textField1.setStyle("-fx-background-color: #f2f2f2;" + STYLE);// Fond gris clair
        
        
        TextField textField2 = new TextField();
        textField2.setStyle("-fx-background-color: #f2f2f2;" + STYLE);
        
        
        TextField textField3 = new TextField();
        textField3.setStyle("-fx-background-color: #f2f2f2;" + STYLE);
        
        
        TextField textField4 = new TextField();
        textField4.setStyle("-fx-background-color: #f2f2f2;" + STYLE);
       
        
        
        //textField2.getStyleClass().add("text-field");
        //TextField textField3 = new TextField();
        //textField3.getStyleClass().add("text-field");
        //TextField textField4 = new TextField();
        //textField4.getStyleClass().add("text-field");

        // Ajout des éléments à la GridPane avec le style associé à chaque élément
        Grid.addRow(nbRow, textField1, textField2, textField3, textField4, supprimer);
        nbRow++;
        
    }

    private void SupprimerAction(int supprimerId) {
        Grid.getChildren().remove(supprimerId*5,supprimerId*5+5);    
        but.remove(supprimerId);
        //System.out.println(Grid);
        ArrayList<Node> copieGrid = new ArrayList();
        for(int i = 0; i <Grid.getChildren().size(); i++){
            copieGrid.add(Grid.getChildren().get(i));
            //System.out.println(Grid.getChildren().get(i).getStyle());
        }
         //System.out.println(copieGrid.get(1+1).getStyle());
       // //System.out.println(copieGrid);
        Grid.getChildren().clear();
        nbRow = 1;
        for(int i = 0; i <copieGrid.size(); i+=5){
            //System.out.println(copieGrid.get(i+1).getStyle());
            Grid.addRow(nbRow, copieGrid.get(i),copieGrid.get(i+1),copieGrid.get(i+2),copieGrid.get(i+3),copieGrid.get(i+4));
           // Grid.addRow(nbRow, new TextField(),new TextField(),new TextField(),copieGrid.get(i+3),copieGrid.get(i+4));
            nbRow++;
        }
       
        
    }
    private void comboBoxAction() {
        nbRow = 1;
        Grid.getChildren().clear();
        boolean nonPasse = true;
        but.clear();
        if(stock.ressources.size() != 0){
            for(Ressource r : stock.ressources) {
                if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
                    NomRessource.setText(r.getLibelle());

                    Type.setText(r.getType()); 
                    if(r.getEvaluation().size()!= 0){
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
                            supprimer.setOnAction(events -> SupprimerAction(but.indexOf(supprimer)));
                            nbRow ++;
                        }
                    }
                }
            }
        } else {
            for(Ressource r : stock.ressources) {
                if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
                    NomRessource.setText(r.getLibelle());
                    Type.setText(r.getType()); 
                    
                    for(Node n : restaurationEvaluation(r)){
                        nonPasse = false;
                        
                        //Grid.addRow(nbRow,textField1,textField2,textField3,textField4,supprimer);
                    }
                }
            }
        }
    } 
    public void sauvegarde(Ressource cle, ArrayList<Node> listeBackup) {
        HashMap<Ressource, ArrayList<Node>> map = new HashMap<>();
        map.put(cle, listeBackup);
    }
    public ArrayList<Node> restaurationEvaluation(Ressource cle){
        return map.get(cle);       
    }
}
