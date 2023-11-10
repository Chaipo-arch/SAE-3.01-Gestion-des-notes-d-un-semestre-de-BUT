/*
 * Classe principale de l'application permettant d'afficher des fenêtres au contenu
 * différent : addition, soustraction ... (ce sont les vues qui changent)
 */
package gestionnoteapp;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * Cette classe est la classe principale de l'application permettant à l'utilisateur
 * d'effectuer plusieurs calculs via des vues différentes.
 * Le but est d'illustrer comment changer la scène associée associée à la fenêtre
 * principale.
 *
 * Au lancement de l'application, la vue principale contient 2 boutons : addition
 * et soustraction.
 * Selon le bouton cliqué, une scène différente prend la place de la vue principale :
 * soit celle de l'addition, soit celle de la soustraction.
 * Sur chacune de ces 2 vues, il y a un bouton retour permettant d'afficher à
 * nouveau la vue principale
 * @author R. Britelle
 * @version 1.0
 *
 */
public class GEstionNoteApp extends Application {
    
    /** Scène principale de l'application, celle qui contient les 2 boutons */
    private static Scene sceneAccueil;
 
    /** Scène permettant de gérer l'addition */
    private static Scene sceneParametres;
    
    private static Scene sceneAjouterEvaluation;
    
    private static Scene sceneMesEvaluations;
    
    private static Scene sceneMesMoyennes;
    
    private static Scene sceneImportation;
    
    private static Scene sceneCommuniquer;
    

    /** Fenêtre principale de l'application
    * La scène qui lui est associée sera modifiée en fonction
    * des clics de l'utilisateur
    */
    private static Stage fenetrePrincipale;
    
    /**
    * Permet de modifier la scène de la fenêtre principale
    * pour qu'elle devienne celle de l'addition
    */
    public static void activerAcceuil() {
        fenetrePrincipale.setScene(sceneAccueil);
    }
 
    /**
    * Permet de modifier la scène de la fenêtre principale
    * pour qu'elle devienne celle de la soustraction
    */
    public static void activerParametres() {
        fenetrePrincipale.setScene(sceneParametres);
    }


    @Override
    public void start(Stage primaryStage) {
    try {
    /*
    * chargement de la vue de la scène principale dans le conteneur
    * de type Parent
    */
    FXMLLoader chargeurFXML = new FXMLLoader();
    chargeurFXML.setLocation(getClass().getResource("accueil.fxml"));
    Parent conteneur = chargeurFXML.load();

    /*
    * Création de la scène principale
    */
    sceneAccueil = new Scene(conteneur, 500, 300);
 
    /*
    * Chargement de la vue de l'addition et
    * création de la scène associée à cette vue
    */
    FXMLLoader chargeurFXMLAddition = new FXMLLoader();
    chargeurFXMLAddition.setLocation(getClass().getResource("page2.fxml"));
    conteneur = chargeurFXMLAddition.load();
    sceneParametres = new Scene(conteneur, 500, 300);
 
 
 // on définit le titre, la hauteur et la largeur de la fenêtre principale
 primaryStage.setTitle("Application avec plusieurs fenêtres");
 primaryStage.setHeight(600);
 primaryStage.setWidth(1000);
 //primaryStage.minHeightProperty(100);
 //primaryStage.minWidthProperty(100);
 
 primaryStage.setMinHeight(600);
 primaryStage.setMinWidth(1000);
 
 
 primaryStage.setMaxHeight(600);
 primaryStage.setMaxWidth(1000);
 /*
 * on associe la scène principale à la fenêtre principale
 * Cette dernière est stockée en tant qu'attribut afin d'être accessible
 * dans les méthodes activer... Celles qui permettent de rendre active
 * l'une des 3 scènes
 */

 primaryStage.setScene(sceneAccueil);
 fenetrePrincipale = primaryStage;
 primaryStage.show();

 } catch (IOException e) {

 // TODO Auto-generated catch block
 e.printStackTrace();
 }
 }

 /**
 * Programme principal
 * @param args non utilisé
 */
 public static void main(String[] args) {
 launch(args);
 }
}
