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
    
    private boolean flagErreur1 = false;
    private boolean flagErreur2 = false;
    private boolean flagErreur3 = false;
    
    private String messageErreur1 = "Erreur : Le type d'une évaluation est manquant";
    //messageErreur1 = "";
    private String messageErreur2 = "Erreur : Le total des coeficients d'une ressource sont obligatoirement compris entre 0 et 100";
    //messageErreur2 = "\nErreur : Le total des coeficients d'une ressource sont obligatoirement compris entre 0 et 100";
    private String messageErreur3 = "WARNING : Attention certaines évaluation ne possédes pas de note, elles ne seront pas pris en comptes dans le calcul des moyennes";
    //messageErreur3="\nWARNING : Attention certaines évaluation ne possédes pas de note, elles ne seront pas pris en comptes dans le calcul des moyennes";
    
    
    Stockage stock = Stockage.getInstance();
    ArrayList<Node> sauvegardeGrid = new ArrayList();
    /**
     * Initializes the controller class.
     * @param url
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //Stockage.getInstance().supprimerDonnees();
        Grid.getChildren().clear();
        ArrayList<String> ids = Stockage.getInstance().getRessourcesId();
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
            NotificationController.popUpMessage("Il est nécessaire d'impoter votre "
                    + "Programme Nationnal pour utiliser cette fonctionnalité."
                    + "Rendez vous dans :  Parametres > Importer ",
                    "Erreur Parametres d'application manquants");
        }
    }
    @FXML
    void ValiderAction(ActionEvent event) throws NoteException, EvaluationException {
       
       
            for(Ressource r : stock.ressources) {
                if(r.getIdentifiant().equals(comboBox.getValue())) {
                    r.getEvaluation().clear();
                }
            }
       
       
        if(stock.ressources.size() != 0) {
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
                            if(!flagErreur3){
                                
                                //NotificationController.popUpMessage("Erreur : Une Evaluation ne peut pas etre insérer car elle ne posséde pas de type", "");
                                flagErreur3=true;
                            }
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
                            coefficientDouble = Double.parseDouble(textFieldAnalyser.getText().replace(',','.'));
                            
                            if(!Evaluation.isCoefficient(coefficientDouble)){
                                textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                            } else {
                                coefficientCorrect = true;
                                totalCoefficient += coefficientDouble;
                                textFieldAnalyser.setStyle(STYLE);
                                
                            }
                        } catch (Exception e ){
                            if (!flagErreur1){
                                
                                //NotificationController.popUpMessage("Erreur : Un ou plusieurs coeficient ne correspondent pas au format attendu (le nombre doit etre compris entre 0 et 100)", "");
                                //LabelNotificationID.setText("Un ou plusieur coefficient ne correcponde pas au champs attendu");
                                
                                flagErreur1=true;
                            }
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
                                noteATester = new Note(Double.parseDouble(textFieldAnalyser.getText().replace(',','.')));
                                if(Note.isNote(noteATester.getNote())){
                                    noteCorrect = 2;
                                    noteDouble = Double.parseDouble(textFieldAnalyser.getText());
                                    textFieldAnalyser.setStyle(STYLE);
                                } else {
                                    textFieldAnalyser.setStyle(STYLE + "-fx-border-color: red;" + "-fx-border-width: 2px;");
                                }
                            }
                        } catch (Exception e ){
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
                        if(!flagErreur2){
                           
                           //NotificationController.popUpMessage("Attention : certaines évaluation ne seront pas pris en compte lors du calcul de la moyenne car elles ne possédent pas de note", "");
                            //LabelNotificationID.setText("Attention certaines évaluation ne seront pas pris en compte lors du calcul de la moyenne"); 
                           flagErreur2=true;
                        }
                            
                         
                    }
                    if(totalCoefficient <= 100 && totalCoefficient > 0){
                        for(Ressource r : stock.ressources) {
                            if(r.getIdentifiant().equals(comboBox.getValue())) {                              
                                if(!r.ajouterEvaluation(evaluationAAjouter)){
                                    String messageErreurSimi = "Erreur des evaluation sont similaire" + STYLE;
                                    NotificationController.popUpMessage(messageErreurSimi, "");
                                }                                                 
                            }                            
                        }
                        sauvegardeGrid();
                        AccueilController.mesNoteCourant = false;
                    }
                    noteCorrect = 0;
                    typeCorrect = false;
                    coefficientCorrect = false;    
                }
                
            }
            if (flagErreur1 && flagErreur2 && flagErreur3){
                NotificationController.popUpMessage(
                          messageErreur1
                        + "\n"+messageErreur2
                        + "\n"+messageErreur3, "Des données sont absentes ou incorrect");
            }else if (flagErreur1 && flagErreur3){
                NotificationController.popUpMessage(""
                        + messageErreur1
                        + "\n"+ messageErreur2,"Des données sont absentes ou incorrect");
            }else if(flagErreur1){
                NotificationController.popUpMessage(""
                        + messageErreur2,"Des données sont absentes ou incorrect");
            }else if(flagErreur3){
                NotificationController.popUpMessage(""
                        + messageErreur1,"Des données sont absentes ou incorrect");
            }else if (flagErreur2){
                NotificationController.popUpMessage(""
                        + messageErreur3,"Attention");
            }
            if(totalCoefficient > 100 || totalCoefficient < 0){
                for(int j = 2; j < Grid.getChildren().size(); j+= 5){
                    Grid.getChildren().get(j).setStyle(STYLE + "-fx-border-color: red;");
                }
            }
            
        } else {
            NotificationController.popUpMessage("L'ajout d'evaluation est impossible tant que vos parametres n'ont pas était importer :"
                    + "\nRendez vous dans Parametres > Importer","");
            //LabelNotificationID.setText("Impossible d'ajout� les évaluations, veuillez importé un paramétrage");  
        }
        flagErreur1=false;
        flagErreur2=false;
        flagErreur3=false;
        
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
       
        Grid.addRow(nbRow, textField1, textField2, textField3, textField4, supprimer);
        nbRow++;
        modification(sauvegardeGrid);
    }

    private void SupprimerAction(int supprimerId) {
        Ressource r = getRessource();
        for(int i = 0; i < Grid.getChildren().size();i++){
            if(Grid.getChildren().get(i) instanceof Button){
                if(Grid.getChildren().get(i).getId().equals(supprimerId)){
                    for(Evaluation e : r.getEvaluation()){
                        if(e.getType() ==((TextField) Grid.getChildren().get(i-4)).getText()){
                            if(e.getDate() ==((TextField) Grid.getChildren().get(i-3)).getText()){
                                if(e.getCoefficient() == Double.parseDouble(((TextField) Grid.getChildren().get(i-2)).getText())){
                                    if(e.getNote() == Double.parseDouble(((TextField) Grid.getChildren().get(i-1)).getText())){
                                        r.getEvaluation().remove(e);                                      
                                    }
                                }
                            }
                        }
                    }        
                }
            }
        }
       
        Grid.getChildren().remove(supprimerId*5,supprimerId*5+5);    
        but.remove(supprimerId);
        //System.out.println(Grid);
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
        modification(sauvegardeGrid);
       
    }
    public void comboBoxAction() { 
        modification(sauvegardeGrid);
        if(AccueilController.mesNoteCourant){
            Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
            if (result.isPresent() && result.get() == ButtonType.OK) {          
                if(stock.ressources.size() != 0){
                    nbRow = 1;
                    Grid.getChildren().clear();
                    but.clear();
                    restauration();
                }
            }
        } else{
            if(stock.ressources.size() != 0){
                    nbRow = 1;
                    Grid.getChildren().clear();
                    but.clear();
                    restauration();
            }
        } 
        AccueilController.mesNoteCourant = false;
    }
       
    public void restauration(){
        boolean nonPasse = true;
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
        sauvegardeGrid();
    }
    public void modification(ArrayList<Node> sauvegardeGrid){
        boolean resultat = false;
        if(Grid.getChildren().size() != sauvegardeGrid.size()){
            resultat = true;
        } else {
            for (int i = 0; i < Grid.getChildren().size(); i++) {
                if(sauvegardeGrid.get(i) instanceof TextField){
                    if((!((TextField)Grid.getChildren().get(i)).getText().equals(((TextField) sauvegardeGrid.get(i)).getText()))){
                        resultat = true;
                        //.out.println(((TextField)Grid.getChildren().get(i)).getText()+" different de : "+ ((TextField)sauvegardeGrid.get(i)).getText());
                        
                    }
                } else {
                    if(Grid.getChildren().get(i).getId() != sauvegardeGrid.get(i).getId()){
                        resultat = true;
                    }
                }              
            }
        }
        AccueilController.mesNoteCourant = resultat;
        //.out.println(resultat);
    }
    public void sauvegardeGrid(){
        ArrayList<Node> sauvegarde = new ArrayList();
        int nbRowSauvegarde = 1;
        for(int i = 0; i <Grid.getChildren().size(); i++){
            if(Grid.getChildren().get(i) instanceof TextField){
                TextField tx = new TextField(((TextField)Grid.getChildren().get(i)).getText());
                sauvegarde.add(tx);
            } else {
                sauvegarde.add(Grid.getChildren().get(i));
            }
            
        }   
        sauvegardeGrid = sauvegarde;
    }
    public Ressource getRessource(){
        boolean nonPasse = true;  
        for(Ressource r : stock.ressources) {
            if(r.getIdentifiant().equals(comboBox.getValue()) && nonPasse) {
                return r;
            }
        }
        return null;
    }
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
    
    @FXML
    void NoticeClick() {
        try {
            NoticeController.y = 6.3;
            modification(sauvegardeGrid);
            if(AccueilController.mesNoteCourant){
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    changerPage("Notice.fxml");
                }
            } else {
                changerPage("Notice.fxml");
            }
            
             
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