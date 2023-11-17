package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.modele.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/*import GestionNoteApplication.src.main.java.modele.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;*/

public class CommuniquerController {

   @FXML
    private TextField adresseIPText;

    @FXML
    private RadioButton toggleNational;

    @FXML
    private Button btnComm;

    @FXML
    private RadioButton toggleRessource;

    @FXML
    private TextField portID;

    @FXML
    private ToggleGroup choix;

    private Client client = new Client(); // Instanciation de la classe Client
   

    @FXML
    public void CommuniquerAction() {
        //String adresseIP = adresseIPText.getText();
        //int port = Integer.parseInt(portText.getText()); // Convertir le port en entier
        //portID.setText("1234");
        String serverIP = adresseIPText.getText(); // Adresse IP du serveur
        String filePath = "stock.bin"; // Chemin du fichier sérialisé
        try {
            System.out.println(portID.getText());
            int port = Integer.parseInt(portID.getText());
            System.out.println(port);
            System.out.println("salut debug1");
            System.out.println(serverIP);
            client.sendSerializedFileToServer(serverIP, filePath, port);
        } catch (NumberFormatException e) {
            System.out.println("Le port doit être un nombre valide.");
            //e.printStackTrace();
        }
        System.out.println("salut controller");

        //client.sendSerializedFileToServer(serverIP, filePath, port); // Appeler la méthode du client
    }
}
