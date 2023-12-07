package GestionNoteApplication.src.main.java.package1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * Cette classe représente le server pour la reception et l'envoi 
 * de fichiers CSV à un serveur distant.
 * Le port Definie est 51263
 * comprends des methodes pour le cryptage/decryptage et la reception de fichier csv avec 
 * Verification et notification utilisateur en focntion des cas d'erreur
 * @author Robin Britelle, Enzo Cluzel, Ahmed Bribach
 */
public class Server {
    
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectInputStream in;
    
    /**
    * Crée un serveur sur un port spécifié et attend les connexions entrantes.
    */
    public static void createServer() {
        try {
            int port = 51263;
            serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");
        } catch (IOException ex) {
           
        }
    }

    /**
    * Accepte une connexion entrante du côté du serveur.
    * 
    * @return true si la connexion est établie avec succès, false sinon.
    */
    public static boolean connexion()  {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());
            
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


    /**
    * Reçoit un fichier CSV envoyé par le client, 
    * le déchiffre et le stocke localement.
    * 
    * @param filePath Chemin de stockage du fichier CSV reçu.
    * @return true si le fichier CSV est reçu, déchiffré et stocké avec succès, false sinon.
    */
    public static boolean receiveCSVFile(String filePath) {
        try {
            System.out.println("receive en cours");
            
            // Flux d'entrée pour recevoir le fichier CSV du client
            InputStream in = clientSocket.getInputStream();
            
            // Flux de sortie pour stocker le fichier CSV
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            // Lecture et écriture du fichier CSV
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            
            // Lecture du fichier CSV stocké localement
            File fichier = new File(filePath);
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> toutLeFichier = new ArrayList<>();
            String ligne;
            
            // Lecture de chaque ligne du fichier CSV
            while ((ligne = br.readLine()) != null) {
                toutLeFichier.add(ligne);
            }
            
            // Fermeture des flux de lecture
            br.close();
            fr.close();
            
            // Écriture déchiffrée du fichier CSV
            FileWriter fw = new FileWriter(fichier);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < toutLeFichier.size(); i++) {
                // Déchiffrement et écriture dans le fichier
                bw.write(Cryptage.decryptage(Cryptage.cle, toutLeFichier.get(i)) + "\n");
            }
            
            // Fermeture des flux d'écriture
            bw.close();
            fw.close();
            
            // Fermeture de la connexion côté client
            clientSocket.shutdownInput();
            
            System.out.println("Fichier CSV reçu et stocké : " + filePath);
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;   
        }
    }

    /**
    * Reçoit et traite la clé de cryptage envoyée par le client pour initialiser le cryptage.
    * 
    * @return true si la clé est reçue et traitée avec succès, false sinon.
    */
    public static boolean cle(){
        try {
            System.out.println("receive en cours");
            // Flux d'entrée pour recevoir le fichier CSV du client
            InputStream in = clientSocket.getInputStream();
            
            // Chemin de stockage du fichier CSV reçu
            
           
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            
           String mess = "";
           StringBuilder message = new StringBuilder();
           while ((bytesRead = in.read(buffer)) != -1) {
                message.append(new String(buffer, 0, bytesRead));
            }
            
            String[] cle = message.toString().split("\n");
            
            Cryptage.cle = cle[0];
            System.out.println(cle[0]);
            System.out.println(cle[1]);
            System.out.println(cle[2]);
            System.out.println(cle[3]);
            
            Cryptage.p = Integer.parseInt(cle[1]);
            
            Cryptage.g = Integer.parseInt(cle[2]);
            Cryptage.codeAliceEtBob();
            Cryptage.creationClefBobOuAlice(Integer.parseInt(cle[3]));
            
            System.out.println("la clé de cryptage est : "+Cryptage.cle);

            
            clientSocket.shutdownInput();
            System.out.println("client close : "+ clientSocket.isClosed());
            System.out.println("server close : " + serverSocket.isClosed());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;   
        }
    }


    /**
    * Envoie une réponse au client a partir du socket
    * 
    * @param message Le message à envoyer en réponse.
    * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'envoi de la réponse.
    */
    public static void reponse(String message) throws IOException {
        // Vérifie si la connexion avec le client est active
        if(clientSocket != null) {
            System.out.println("Envoie Reponse");
            
            // Flux de sortie pour envoyer la réponse au client
            OutputStream os = clientSocket.getOutputStream();
            
            // Envoi du message au client
            os.write(message.getBytes());
            
            // Fermeture de la sortie côté client
            clientSocket.shutdownOutput();

            try {
                // Mettre en pause pendant 500 millisecondes (0,5 seconde)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Gérer une éventuelle exception si l'interruption se produit pendant la pause
                System.out.println("Une erreur est survenue");
            }
        }
    }
    
    
    /**
    * Ferme la connexion avec le client en genrant les cas cas d'erreurs
    */
    public static void closeClient() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Server Close2");
            }
           
        } catch (SocketException e) {
            System.out.println("Une erreur sur le socket est survenue");
           
        } catch (IOException ex) {
        }
    }


    /**
    * Ferme le serveur et les flux associés.
    */
    public static void closeServer() {
        try {
            if (in != null) {
                in.close();
                System.out.println("Server Close1");
            }
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server Close3");
            }
        } catch (SocketException e) {
            System.out.println("Une erreur sur le socket est survenue");
           
        } catch (IOException ex) {
        }
    }
}
