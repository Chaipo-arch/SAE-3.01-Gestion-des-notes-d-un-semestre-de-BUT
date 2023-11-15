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
    public static void receiveSerializedFile() {
        try {
            // Spécification du numéro de port
            int port = 8887;
            ServerSocket serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");

            // Accepte la connexion d'un client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

            // Flux d'entrée d'objets pour recevoir le fichier sérialisé du client
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            // Réception du fichier sérialisé
            Object fileObject = in.readObject();
            
            
            System.out.println("Fichier sérialisé reçu du client.");
            if (fileObject instanceof Stockage) {
                Stockage receivedStorage = (Stockage) fileObject;
                Stockage.getInstance().addCompetences(receivedStorage.competences);
                // Utilisation des informations de stockage reçues
                System.out.println("Informations sur le stockage reçues : "+ "-----" + receivedStorage.competences.get(0).identifiant+"++++");
            } else {
                System.out.println("Type d'objet inattendu reçu du client.");
            }
            
            

            // Fermeture des flux et du socket
            in.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        receiveSerializedFile();
    }
}
