package GestionNoteApplication.src.main.java.modele;

import java.io.*;
import java.net.*;

public class Server {
    
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectInputStream in;
    

    public static void createServer() {
        try {
            int port = 8881;
            serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");
        } catch (IOException ex) {
           
        }
    }
    public static boolean receiveCSVFile() {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

            // Flux d'entrée pour recevoir le fichier CSV du client
            InputStream in = clientSocket.getInputStream();
            
            // Chemin de stockage du fichier CSV reçu
            String filePath = "NationalExporte.csv";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Fichier CSV reçu et stocké : " + filePath);
            return true;
            // Fermeture des flux et du socket
            //fileOutputStream.close();
            //in.close();
            //clientSocket.close();
            //serverSocket.close();

        } catch (IOException e) {
            return false;
        }
    }
    
    public static void closeServer() {
        //SocketException e;
        try {
            if (in != null) {
                in.close();
                System.out.println("Server Close1");
            }
            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Server Close2");
            }
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server Close3");
            }
        } catch (SocketException e) {
            System.out.println("je suis le message d'erreur !!");
           
        } catch (IOException ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        Server.createServer();
        Server.receiveCSVFile();
        Server.closeServer();
    }
}
