package GestionNoteApplication.src.main.controller;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * La classe NotificationController gère l'affichage des notifications dans l'application.
 * @author Robin Britelle
 */
public class NotificationController {

    private Stage notificationStage; // La fenêtre de notification
    private Label notificationLabel; // Le label contenant le message de la notification

    /**
     * Constructeur de la classe NotificationController permettant de crée une
     * scene remporaires lors de l'affichage d'une notification
     */
    public NotificationController() {
        
        // Initialisation de la fenêtre de notification
        notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UNDECORATED); 
        notificationStage.setAlwaysOnTop(true); 


        VBox rootNotif = new VBox();
        rootNotif.setAlignment(Pos.CENTER); 
        notificationLabel = new Label(); 
        rootNotif.getChildren().add(notificationLabel); 

        
        rootNotif.setStyle(
            "-fx-background-color: #7EEC93;" + // Couleur de fond blanche
            "-fx-background-radius: 20;"+ // Coins arrondis de 20 pixels
            "-fx-font-size: 15px;"
        );
        
        // Création de la scène pour la fenêtre de notification
        Scene scene = new Scene(rootNotif, 270, 55); 
        scene.setFill(Color.TRANSPARENT); // Rend la scène transparente

        
        // Attribution de la scène à la fenêtre de notification
        notificationStage.setScene(scene);
        notificationStage.initStyle(StageStyle.TRANSPARENT);
    }

    /**
     * Affiche une notification avec un message spécifié.
     * @param message Le message à afficher dans la notification.
     */
    public void showNotification(String message) {
        notificationLabel.setText(message); // Définition du message de la notification

        
        // Positionnement de la fenêtre de notification
        notificationStage.setX(GEstionNoteApp.getPrimaryStage().getX() + 830); 
        notificationStage.setY(GEstionNoteApp.getPrimaryStage().getY() + 35); 
        
        // Affichage de la fenêtre de notification
        notificationStage.show();
        
        // Définition de la durée d'affichage de la notification avant de la fermer
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(11), 
                new KeyValue(notificationStage.opacityProperty(), 0)) // Réduction de l'opacité jusqu'à 0
        );

        // Action à effectuer lorsque la durée d'affichage est terminée
        timeline.setOnFinished(event -> notificationStage.close()); // Fermeture de la fenêtre de notification
        timeline.play(); // Lancement de l'animation
    }

    /**
     * Affiche une boîte de dialogue a l'utilisateur de type confirmation avec des options "OK" et "Annuler".
     * @param text Le texte à afficher dans la boîte de dialogue.
     * @param titre Le titre de la boîte de dialogue.
     * @return La réponse de l'utilisateur (option choisie).
     */
    public static Optional<ButtonType> popUpChoix(String text, String titre){
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle(titre);
         alert.setHeaderText(titre);
         alert.setContentText(text);

         return alert.showAndWait(); // Retourne l'option choisie par l'utilisateur
     }

    
    /**
     * Affiche une boîte de dialogue d'avertissement pour informer l'utilisateur.
     * @param text Le texte à afficher dans la boîte de dialogue.
     * @param titre Le titre de la boîte de dialogue.
     */
    public static void popUpMessage(String text, String titre){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(titre);
        alert.setContentText(text);
        alert.showAndWait(); 
    }
}