package GestionNoteApplication.src.main.java.modele;

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

            System.out.println("Fichier CSV envoyé au serveur.");
            
            //TODO on utilise ça ????
            /*InputStream responseIn = socket.getInputStream();
            byte[] responseBuffer = new byte[1024]; // Taille du buffer pour la réponse
            int bytesRead2;
            bytesRead2 = responseIn.read(responseBuffer);
            serverResponse = new String(responseBuffer, 0, bytesRead2);
            responseIn.close();
            System.out.println(serverResponse);*/
            
            
            // Fermeture des flux et du socket
            fileInputStream.close();
            out.close();
            

        } catch (IOException e) {
            //e.printStackTrace();
        }
        //return serverResponse;
    }
    
    public static void recevoirReponse() throws IOException {
        try {
            Thread.sleep(1000); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
         // Gérer une éventuelle exception si l'interruption se produit pendant la pause
            //e.printStackTrace();
        }
        String serverResponse = "";
        InputStream responseIn = socket.getInputStream();
        byte[] responseBuffer = new byte[1024]; // Taille du buffer pour la réponse
        int bytesRead2;
        bytesRead2 = responseIn.read(responseBuffer);
        serverResponse = new String(responseBuffer, 0, bytesRead2);
        responseIn.close();
        System.out.println(serverResponse);
        socket.close();
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
        
        String ipAddress = "10.2.1.1";
        if (validateIP(ipAddress)) {
            System.out.println(ipAddress + " est une adresse IP valide.");
        } else {
            System.out.println(ipAddress + " n'est pas une adresse IP valide.");
        }
        
        //connection(serverIP, port);
        //Client.sendCSVFileToServer(filePath);
        }
        
        
        
}
