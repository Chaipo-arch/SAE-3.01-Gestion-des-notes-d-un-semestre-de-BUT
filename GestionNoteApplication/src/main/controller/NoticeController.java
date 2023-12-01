/*
 * NoticeController.java
 */
package GestionNoteApplication.src.main.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author enzo.cluzel
 */
public class NoticeController implements Initializable{
    
    /**
     * Initializes the controller class.
     * @param url
     */
    public void initialize(URL url, ResourceBundle rb) {
        setScene();
    }
    Stage noticeStage ;
    public static double y ;
    @FXML
    private AnchorPane Contenu;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private AnchorPane contenu2;
    
    public void setScene(){
        //Contenu.setLayoutY(y);
        scroll.setVvalue(y);
        //contenu2.setLayoutY(y);
       // scroll.setLayoutY(y);
    }
}
