/**
 * Server.java
 */
package GestionNoteApplication.src.main.java.package1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la communication 
 * Le Serveur reçoie un ou 2 fichiers envoyé par le client 
 */
public class Server {
    
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectInputStream in;
    
    /**
     * Creation du serveur
     */
    public static void createServer() {
        try {
            int port = 10008;
            serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");
        } catch (IOException ex) {
           
        }
    }

    /**
     * Attendre la connexion du client
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
    * recevoir le fichier envoyé par le client, le nouveau fichier a pour nom le nom donné en argument
     * @param filePath , chemin du fichier
     */
    public static boolean receiveCSVFile(String filePath) {
        try {
            System.out.println("receive en cours");
            // Flux d'entrée pour recevoir le fichier CSV du client
            InputStream in = clientSocket.getInputStream();
            
            // Chemin de stockage du fichier CSV reçu
            String f;
            f = filePath;
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            // Lecture de ce qu'est envoyé par le client 
            // Le fichier est écrit ligne par ligne
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            File fichier = new File(filePath);
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> toutLeFichier = new ArrayList<>();
            String ligne ;
            // lecture du fichier créé
            while ((ligne = br.readLine()) != null) {
                toutLeFichier.add(ligne);
            }
            
            br.close();
            fr.close();
            
            FileWriter fw = new FileWriter(fichier);
            BufferedWriter bw = new BufferedWriter(fw);
            // Décryptage puis réécriture du fichier
            for(int i = 0; i < toutLeFichier.size();i++){
               
                bw.write(Cryptage.decryptage(Cryptage.cle, toutLeFichier.get(i))+"\n");
            }
            bw.close();
            fw.close();
            System.out.println("Fichier CSV reçu et stocké : " + filePath);
            clientSocket.shutdownInput();
            System.out.println("client close : "+ clientSocket.isClosed());
            System.out.println("server close : " + serverSocket.isClosed());
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //OutputStream os = clientSocket.getOutputStream();
            //os.write("Le fichier n'a pas était recu ".getBytes());
            return false;   
        }
    }

    /**
     * 
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
            
            
            Cryptage.creationClefBob(message.toString());
            System.out.println("la clé de cryptage est : "+Cryptage.cle);

            
            //in.close();
            clientSocket.shutdownInput();
            System.out.println("client close : "+ clientSocket.isClosed());
            System.out.println("server close : " + serverSocket.isClosed());
            return true;
        } catch (Exception e) {
            //OutputStream os = clientSocket.getOutputStream();
            //os.write("Le fichier n'a pas était recu ".getBytes());
            return false;   
        }
    }

    /**
     * Envoie de la réponse aux client 
     * @param message , la réponse à envoyer
     */
    public static void reponse(String message) throws IOException {
        
        System.out.println("Envoie Reponse");
        OutputStream os = clientSocket.getOutputStream();
        os.write(message.getBytes());
        clientSocket.shutdownOutput();
    }
    
    
    /**
     * Arret de la connexion avec le client
     */
    public static void closeClient() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
                System.out.println("Server Close2");
            }
           
        } catch (SocketException e) {
            System.out.println("je suis le message d'erreur !!");
           
        } catch (IOException ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * arret du serveur 
     */
    public static void closeServer() {
        //SocketException e;
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
            System.out.println("je suis le message d'erreur !!");
           
        } catch (IOException ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        //Server.createServer();
        //Server.receiveCSVFile();
        //Server.closeServer();
    }
}
