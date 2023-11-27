package GestionNoteApplication.src.main.controller;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
package GestionNoteApplication.src.main.controller;

import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.geometry.Bounds;
import javafx.stage.Stage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
*/
/**
 *
 * @author robin.britelle
 */


/*
public class NotificationController{
    
    
    
    private Stage notificationStage;
    private Label notificationLabel;
    private Stage primaryStage;



    public NotificationController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UTILITY);
        notificationStage.setAlwaysOnTop(true);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        notificationLabel = new Label();
        root.getChildren().add(notificationLabel);

        Scene scene = new Scene(root, 300, 50);
        notificationStage.setScene(scene);
    }

    public void showNotification(String message) {
        Timeline timeline;
        //timeline.
        notificationLabel.setText(message);
       
 
        
        
        //Set the position of the notification window
        
        //double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        //double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        //double notificationWidth = notificationStage.getWidth();
        //double notificationHeight = notificationStage.getHeight();

        /*double windowWidth2;
        windowWidth2 = GEstionNoteApp.getwindowWidth();
        double windowHeight2 = GEstionNoteApp.windowHeight;
        */
        
        
        
        //notificationStage.setX(windowWidth2 - notificationWidth - 20); // Set X position
        //notificationStage.setY(windowHeight2 - notificationHeight - 600); // Set Y position
        //Bounds bounds = primaryStage.getScene().getRoot().getLayoutBounds();
        

        /*
        Scene bounds = primaryStage.getScene();
       double windowWidth = primaryStage.getX();
        double windowHeight = primaryStage.getY();
        //System.out.println(primaryStage.getWidth() + "   " +primaryStage.getHeight() );

        notificationStage.setX(1264+windowWidth);
        notificationStage.setY(675+windowHeight);
        
        
        
        notificationStage.show();
        
        /*
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(10),
                //new KeyValue(notificationStage.opacityProperty(), 0))
                new KeyValue(notificationStage.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> notificationStage.hide());

*/
        //timeline.playFromStart();
       

    //}
    
    
   
    
    

//}


//*/


import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/*
public class NotificationController {

    private static final double NOTIFICATION_HEIGHT = 40.0;
    private static final double SCREEN_PADDING = 10.0;
    */
    


public class NotificationController {

    private Stage notificationStage;
    private Label notificationLabel;

    public NotificationController() {
        notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.UTILITY);
        notificationStage.setAlwaysOnTop(true);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        notificationLabel = new Label();
        root.getChildren().add(notificationLabel);

        Scene scene = new Scene(root, 300, 100);
        notificationStage.setScene(scene);
    }

    public void showNotification(String message) {
        notificationLabel.setText(message);
        
         // Récupérer la taille de l'écran principal   //TODO ajuster la Notifications par rapport a la fenetres et pas a l'ecran
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        
        
        // Positionner la notification en bas à droite
        notificationStage.setX(screenWidth-305); // L'espace de 20 pixels à partir du bord droit
        notificationStage.setY(screenHeight-130); // L'espace de 20 pixels à partir du bord bas
        
        
        notificationStage.show();

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(10),
                new KeyValue(notificationStage.opacityProperty(), 0))
        );

        timeline.setOnFinished(event -> notificationStage.hide());
        timeline.play();
    }



    public static Optional<ButtonType> popUp(String text, String titre){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(titre);
            alert.setHeaderText(titre);
            alert.setContentText(text);

            // Affichage de la boîte de dialogue et attente de fermeture
            Optional<ButtonType> result = alert.showAndWait();
            return result;

    }
}

    /*
    public static void showNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initStyle(StageStyle.TRANSPARENT);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();

        Label label = new Label(message);
        label.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 10px;");
        label.setPrefHeight(NOTIFICATION_HEIGHT);
        label.setPrefWidth(screenWidth - (2 * SCREEN_PADDING));
        label.setWrapText(true);

        HBox notificationBox = new HBox(label);
        notificationBox.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(new Pane(notificationBox), screenWidth, NOTIFICATION_HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        notificationStage.setScene(scene);

        notificationStage.setX(SCREEN_PADDING);
        notificationStage.setY(screenHeight);

        notificationStage.show();

        FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(1), notificationStage.getScene().getRoot());
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), notificationStage.getScene().getRoot());
        translateTransition.setToY(screenHeight - NOTIFICATION_HEIGHT - SCREEN_PADDING);
        translateTransition.play();

        fadeInTransition.setOnFinished(event -> {
            try {
                Thread.sleep(3000); // Attendre 3 secondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), notificationStage.getScene().getRoot());
            fadeOutTransition.setFromValue(1);
            fadeOutTransition.setToValue(0);
            fadeOutTransition.play();

            fadeOutTransition.setOnFinished(fadeEvent -> notificationStage.close());
        });
    }
*/
