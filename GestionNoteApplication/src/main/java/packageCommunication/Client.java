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
    public static void main(String[] args) {
        try {
            // Spécification de l'adresse IP du serveur et du numéro de port
            String serverIP = "127.0.0.1"; // Remplacez par l'adresse IP du serveur
            int port = 9999;

            // Création d'un socket client pour se connecter au serveur
            Socket socket = new Socket(serverIP, port);

            // Flux de lecture et d'écriture pour communiquer avec le serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Envoi d'un message au serveur
            String message = "Bonjour, serveur!";
            out.println(message);
            System.out.println("Message envoyé au serveur : " + message);

            // Lecture de la réponse du serveur
            String serverResponse = in.readLine();
            System.out.println("Réponse du serveur : " + serverResponse);

            // Fermeture des flux et du socket
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}