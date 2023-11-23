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
    private Label IdentifiantR;

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
    
    /**
     * Initializes the controller class.
     * @param url
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//Stockage.getInstance().supprimerDonnees();
        Grid.getChildren().clear();
        System.out.println(" 1");
        ArrayList<String> ids = Stockage.getInstance().getRessourcesId();
        if(Stockage.getInstance().ressources.size() != 0) {
            System.out.println(" 2");
            
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
                            textFieldAnalyser.setStyle("-fx-background-color: orange;");
                        } else { 
                            typeCorrect = true;
                            textFieldAnalyser.setStyle("-fx-background-color: white;");    
                        }
                    }
                    /*if (colIndex == 1){
                        dateString = textFieldAnalyser.getText();
                        if(dateString.equals("")||dateString == null){
                            textFieldAnalyser.setStyle("-fx-background-color: orange;");
                        } else { 
                            dateCorrect = true;
                            textFieldAnalyser.setStyle("-fx-background-color: white;");
                        }
                    }*/
                    if (colIndex == 2){
                        try {
                            coefficientDouble = Double.parseDouble(textFieldAnalyser.getText());
                            if(!Evaluation.isCoefficient(coefficientDouble)){
                                textFieldAnalyser.setStyle("-fx-background-color: orange;");
                            } else {
                                coefficientCorrect = true;
                                totalCoefficient += coefficientDouble;
                                textFieldAnalyser.setStyle("-fx-background-color: white;");
                            }
                        } catch (Exception e ){
                            System.out.println(e);
                            textFieldAnalyser.setStyle("-fx-background-color: orange;");
                        }  
                    }
                    if (colIndex == 3){                   
                        try {
                            if(textFieldAnalyser.getText() == null 
                                    || textFieldAnalyser.getText().equals("")){
                                noteCorrect = 1;
                                textFieldAnalyser.setStyle("-fx-background-color: yellow;");
                            } else {
                                noteATester = new Note(Double.parseDouble(textFieldAnalyser.getText()));
                                if(Note.isNote(noteATester.getNote())){
                                    noteCorrect = 2;
                                    noteDouble = Double.parseDouble(textFieldAnalyser.getText());
                                    textFieldAnalyser.setStyle("-fx-background-color: white;");
                                } else {
                                    textFieldAnalyser.setStyle("-fx-background-color: orange;");
                                }
                            }
                        } catch (Exception e ){
                            System.out.println(e);
                            textFieldAnalyser.setStyle("-fx-background-color: orange;");   
                        }  
                    }   
                } else if(typeCorrect && coefficientCorrect && noteCorrect != 0) {
                    if(noteCorrect == 2){
                        Note noteAAjouter = new Note(noteDouble);
                        evaluationList.add(new Evaluation(NomRessource.getText(),noteAAjouter 
                                               ,typeString,coefficientDouble,dateString));
                    } else {
                        evaluationList.add(new Evaluation(NomRessource.getText(),new Note(-1) 
                                               ,typeString,coefficientDouble,dateString));
                        LabelNotificationID.setText("Attention certaines évaluation ne seront pas pris en compte lors du calcul de la moyenne");
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
                if(totalCoefficient == 100){
                    for(Ressource r : stock.ressources) {
                        if(r.getIdentifiant().equals(comboBox.getValue())) {
                            r.getEvaluation().clear();
                            for(Evaluation e : evaluationList) {
                                if(!r.ajouterEvaluation(e)){
                                    LabelNotificationID.setText("Attention des evaluation sont similaire");
                    		}
                            }
                        }
                    }
                }else{            	
                    for(int i = 2; i < Grid.getChildren().size(); i+= 5){
                        Grid.getChildren().get(i).setStyle("-fx-background-color: yellow;");
                    }
                    LabelNotificationID.setText("total des coefficient != de 100");  
                }
            }
        } else {
            LabelNotificationID.setText("Impossible d'ajout� les évaluations, veuillez importé un paramétrage");  
        }   
    } 
   
    @FXML
    void AjouterEvaluationAction(ActionEvent event) {
        Button supprimer = new Button("supprimer");
        supprimer.setId(nbRow+"");
        but.add(supprimer);
        supprimer.setOnAction(events -> SupprimerAction(but.indexOf(supprimer)));
        
        Grid.addRow(nbRow, new TextField(),new TextField(),new TextField(),new TextField(),supprimer);
        nbRow ++;
    }

    private void SupprimerAction(int supprimerId) {
        Grid.getChildren().remove(supprimerId*5,supprimerId*5+5);    
        but.remove(supprimerId);
        
    }
    private void comboBoxAction() {
        Grid.getChildren().clear();
        boolean nonPasse = true;
        but.clear();
        for(Ressource r : stock.ressources) {
            if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
                NomRessource.setText(r.getLibelle());
                IdentifiantR.setText(r.getIdentifiant());
                Type.setText(r.getType()); 
                if(r.getEvaluation().size()!= 0){
                    for(Evaluation e : r.getEvaluation()){
                        nonPasse = false;
                        Button supprimer = new Button("supprimer");
                        supprimer.setId(nbRow+"");
                        Grid.addRow(nbRow, new TextField(e.getType()),
                                           new TextField(e.getDate()),
                                           new TextField(e.getCoefficient()+""),
                                           new TextField(e.getNote()+""),supprimer);
                        but.add(supprimer);
                        supprimer.setOnAction(events -> SupprimerAction(but.indexOf(supprimer)));
                        nbRow ++;
                    }
                }
            }
        }     
    }    
}
