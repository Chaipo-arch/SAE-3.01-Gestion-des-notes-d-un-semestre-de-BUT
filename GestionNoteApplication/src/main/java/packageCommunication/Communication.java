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
public class Communication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
            Object deserialiserObjet = Serialisation.deserialiserObjet();
            // Vous pouvez ici travailler avec l'objet sérialisé reçu (ex: casting, traitement, etc.)
            for(Competence c : ((Stockage) fileObject).competences) {
                System.out.println(c.identifiant);
            }
    }
    
}




import java.io.*;
import java.net.*;

// Supposons que la classe Stockage représente les données de stockage
class Stockage implements Serializable {
    private String nom;
    private int quantite;

    public Stockage(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return "Stockage [nom=" + nom + ", quantite=" + quantite + "]";
    }
}

public class Server {
    public static void receiveSerializedFile() {
        try {
            int port = 8887;
            ServerSocket serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            Object fileObject = in.readObject();

            if (fileObject instanceof Stockage) {
                Stockage receivedStorage = (Stockage) fileObject;
                
                // Utilisation des informations de stockage reçues
                System.out.println("Informations sur le stockage reçues : " + receivedStorage.toString());
                System.out.println("Nom du stockage : " + receivedStorage.getNom());
                System.out.println("Quantité en stock : " + receivedStorage.getQuantite());
            } else {
                System.out.println("Type d'objet inattendu reçu du client.");
            }

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
