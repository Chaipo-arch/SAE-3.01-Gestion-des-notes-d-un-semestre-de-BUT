package bordelCSV;

import java.io.*;
import java.net.*;

public class Server {
    public static void receiveCSVFile() {
        try {
            int port = 8887;
            ServerSocket serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

            // Flux d'entrée pour recevoir le fichier CSV du client
            InputStream in = clientSocket.getInputStream();
            
            // Chemin de stockage du fichier CSV reçu
            String filePath = "stock.csv";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Fichier CSV reçu et stocké : " + filePath);

            // Fermeture des flux et du socket
            fileOutputStream.close();
            in.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        Server.receiveCSVFile();
    }
}
