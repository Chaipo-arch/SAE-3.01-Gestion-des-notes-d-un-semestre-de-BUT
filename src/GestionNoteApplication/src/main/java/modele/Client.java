package GestionNoteApplication.src.main.java.modele;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    
    public Client(){
        
    }
    private Socket socket ;
    
    public void connection(String serverIP, int port) {
        try {
            this.socket = new Socket(serverIP, port);
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
            OutputStream out = socket.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();

            System.out.println("Fichier CSV envoyé au serveur.");
            
            //TODO on utilise ça ????
            /*InputStream responseIn = socket.getInputStream();
            byte[] responseBuffer = new byte[1024]; // Taille du buffer pour la réponse
            int bytesRead2;
            bytesRead2 = responseIn.read(responseBuffer);
            serverResponse = new String(responseBuffer, 0, bytesRead2);
            responseIn.close();
            System.out.println(serverResponse);*/
            out.close();
           System.out.println("serveur close : " + socket.isClosed());
            System.out.println("Fermeture de l'envoi du fichier");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return serverResponse;
    }
    
    public void recevoirReponse() throws IOException {
        System.out.println("Réponse reçue :");
        try {
            Thread.sleep(500); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
         // Gérer une éventuelle exception si l'interruption se produit pendant la pause
            e.printStackTrace();
        }
        
        try ( InputStream is = socket.getInputStream()){
            InputStreamReader ir = new InputStreamReader(is); // transformation stream octet
            // en stream de caract
            BufferedReader rd = new BufferedReader(ir);
            String messageSrv = rd.readLine();
            System.out.println(messageSrv);
            ir.close();
            rd.close();
        }
        
        
       
        socket.close();
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
