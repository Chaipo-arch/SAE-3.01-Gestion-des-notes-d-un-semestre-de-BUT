package GestionNoteApplication.src.main.java.modele;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    public Client(){
        
    }
    private Socket socket ;
    private OutputStream out;
    
    public void connection(String serverIP, int port) {
        try {
            this.socket = new Socket(serverIP, port);
            this.socket.setKeepAlive(true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendCSVFileToServer(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Le fichier spécifié n'existe pas.");
                return;
            }

            // Flux de sortie pour envoyer le fichier CSV au serveur
            out = socket.getOutputStream();
            
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            socket.shutdownOutput();
            System.out.println("Fichier CSV envoyé au serveur.");
            System.out.println("serveur close : " + socket.isClosed());
            System.out.println("Fermeture de l'envoi du fichier");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String recevoirReponse() throws IOException {
        try {
            Thread.sleep(500); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String serverReponse ="";
        InputStream responseIn = socket.getInputStream();
        byte[] responseBuffer = new byte[1024]; // Taille du buffer pour la réponse
        int bytesRead2;
        bytesRead2 = responseIn.read(responseBuffer);
        serverReponse = new String(responseBuffer, 0, bytesRead2);
        responseIn.close();
        //System.out.println(serverReponse);
        
       
        socket.close();
        return(serverReponse);
    }
    
    public static void main(String[] args) {
        String serverIP = "10.2.14.25"; // Adresse IP du serveur
        //String filePath = "Z:\\communication\\src\\Ressource\\test.csv"; // Chemin du fichier CSV à envoyer
        String filePath = "Z:\\IHM\\src\\GestionNoteApplication\\src\\ressources\\csv\\Paramétrage semestre2.xlsx"; // Chemin du fichier CSV à envoyer
        int port = 8881; // Port du serveur
        //connection(serverIP, port);
        //Client.sendCSVFileToServer(filePath);
        }
        
        
        
}
