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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


public class NotesController implements Initializable{

    
    @FXML
    private AnchorPane contenuPage;
    
    @FXML
    private Button ValiderChangementButton;

    @FXML
    private Label NomRessource;

    @FXML
    private Label Type;
    
     Parent fxml;

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

    private GridPane GridSave;
    int nbRow = 1;
    
    TextField type = new TextField();
    TextField date = new TextField();
    TextField poids = new TextField();
    TextField note = new TextField();
    
    private ArrayList<Button> but = new ArrayList();
    Evaluation evaluationAAjouter;
    
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
        GridSave = Grid;
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
                        textFieldAnalyser.setStyle(STYLE + "-fx-background-color: white;");
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
                        evaluationAAjouter = new Evaluation(NomRessource.getText(),noteAAjouter 
                                               ,typeString,coefficientDouble,dateString);
                        //LabelNotificationID ajouter notif eval succeful
                        NotificationController NotificationController = new NotificationController();
                        NotificationController.showNotification("Vos données ont était insérer");
                    } else {
                        evaluationAAjouter = new Evaluation(NomRessource.getText(),new Note(-1) 
                                               ,typeString,coefficientDouble,dateString);
                        LabelNotificationID.setText("Attention certaines évaluation ne seront pas pris en compte lors du calcul de la moyenne");
                         
                    } 
                    if(totalCoefficient <= 100 && totalCoefficient > 0){
                        for(Ressource r : stock.ressources) {
                            if(r.getIdentifiant().equals(comboBox.getValue())) {
                                
                                if(!r.ajouterEvaluation(evaluationAAjouter)){
                                    LabelNotificationID.setText("Erreur des evaluation sont similaire" + STYLE);
                                }                                                     
                            }
                        }
                    } else {            	
                        for(int j = 2; j < Grid.getChildren().size(); j+= 5){
                            Grid.getChildren().get(j).setStyle("-fx-border-color: red;" + STYLE);
                        }
                        LabelNotificationID.setText("total des coefficient != de 100");  
                    }
                    noteCorrect = 0;
                    typeCorrect = false;
                    coefficientCorrect = false;     
                }
            }            
        } else {
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
        ArrayList<Node> copieGrid = new ArrayList();
        
        for(int i = 0; i <Grid.getChildren().size(); i++){
            copieGrid.add(Grid.getChildren().get(i));
        }
        Grid.getChildren().clear();
        nbRow = 1;
        for(int i = 0; i <copieGrid.size(); i+=5){
            Grid.addRow(nbRow, copieGrid.get(i),copieGrid.get(i+1),copieGrid.get(i+2),copieGrid.get(i+3),copieGrid.get(i+4));
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
        }
    } 
    public boolean modification(GridPane GridSave){
        boolean resultat = true;
        if(Grid.getChildren().size() != GridSave.getChildren().size()){
            resultat = false;
        }else {
            for (int i = 0; i < Grid.getChildren().size(); i++) {
                if(Grid.getChildren().get(i) != GridSave.getChildren().get(i)){//TODO != a changer
                    resultat = false;
                }
            }
        }
        return resultat;
    }
    
    @FXML
    void NoticeClick() {
         try {
             NoticeController.y = 6.3;
             changerPage("Notice.fxml");
            //changerPage("Notice.fxml");
        } catch (IOException ex) {
            
        }
    }
    
    public void changerPage(String page) throws IOException {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        System.out.println(file.exists());
        if(file.exists()) {
            String changementPage = "../../ressources/fxml/"+page;
            System.out.println(file.getAbsolutePath());
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }
    
}