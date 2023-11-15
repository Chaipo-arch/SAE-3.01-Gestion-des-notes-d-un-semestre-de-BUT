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

public class Client {
    public static void sendSerializedFileToServer() {
        String serverIP = "127.0.0.1"; // Adresse IP du serveur
        String filePath = "stock.bin"; // Chemin du fichier sérialisé
        
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Le fichier spécifié n'existe pas.");
                return;
            }

            // Spécification du numéro de port
            int port = 8887;

            // Création d'un socket client pour se connecter au serveur
            Socket socket = new Socket(serverIP, port);

            // Flux d'entrée d'objets pour lire le fichier sérialisé
            ObjectInputStream fileInputStream = new ObjectInputStream(new FileInputStream(file));

            // Flux de sortie d'objets pour envoyer le fichier sérialisé au serveur
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Envoi du fichier sérialisé au serveur
            Object fileObject = fileInputStream.readObject();
            out.writeObject(fileObject);
            out.flush();

            System.out.println("Fichier sérialisé envoyé au serveur.");

            // Fermeture des flux et du socket
            fileInputStream.close();
            out.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendSerializedFileToServer();
    }
}