package GestionNoteApplication.src.main.java.package1;

import GestionNoteApplication.src.main.controller.NotificationController;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    
    private static Socket socket ;
    
    
    public static void connection(String serverIP, int port) {
        try {
             socket = new Socket(serverIP, port);
        } catch (IOException ex) {
            //Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            NotificationController.popUpMessage("Erreur pas de server","");
        }
    }
    
    public static void sendCSVFileToServer(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Le fichier spécifié n'existe pas.");
                return;
            }

            // Flux de sortie pour envoyer le fichier CSV au serveur
            OutputStream out = socket.getOutputStream();
            
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            //socket.shutdownOutput();
           //fileInputStream.close();
            socket.shutdownOutput();
            System.out.println("Fichier CSV envoyé au serveur.");
            System.out.println("serveur close : " + socket.isClosed());
            System.out.println("Fermeture de l'envoi du fichier");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String recevoirReponse() throws IOException {
        //socket.shutdownOutput();// a garder si marche pas
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
    
    public static boolean validateIP(String ip) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
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