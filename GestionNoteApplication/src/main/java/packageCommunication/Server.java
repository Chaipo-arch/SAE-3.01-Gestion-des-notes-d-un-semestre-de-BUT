/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

/**
 *
 * @author robin.britelle
 */


import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Création d'un socket serveur et spécification du numéro de port
            int port = 9999;
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Serveur en attente de connexion...");
                
                try ( // Accepte la connexion d'un client
                        Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Connexion établie avec " + clientSocket.getInetAddress());
                    
                    PrintWriter out;
                    try ( // Flux de lecture et d'écriture pour communiquer avec le client
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        // Lecture du message envoyé par le client
                        String messageFromClient = in.readLine();
                        System.out.println("Message reçu du client : " + messageFromClient);
                        // Réponse au client
                        out.println("Message reçu par le serveur : " + messageFromClient);
                        // Fermeture des flux et des sockets
                    }
                    out.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

