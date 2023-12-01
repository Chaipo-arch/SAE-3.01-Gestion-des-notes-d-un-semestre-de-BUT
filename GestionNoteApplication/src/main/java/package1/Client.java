/*
 * Client.java
 */
package GestionNoteApplication.src.main.java.package1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la communication 
 * Le client envoie un ou des fichiers aux serveurs
 */
public class Client {
    
    public Client(){
        
    }
    private Socket socket ;
    
    /**
     * Connexion au serveur 
     * @param serverIP , L'adresse ip du serveur dont on veut se connecter
     * @param port , le port du serveur
     */
    public void connection(String serverIP, int port) {
        try {
            this.socket = new Socket(serverIP, port);
            this.socket.setKeepAlive(true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Envoyer au serveur le fichier avec le chemin du fichier donné en argument
     * @param filePath , chemin du fichier
     */
    public void sendCSVFileToServer(String filePath) {
        try {
            File fileC = new File("crypte.csv");
            if (fileC.exists()) {
                fileC.delete();
                fileC.createNewFile();
            } else {
                fileC.createNewFile();
            }
            String fichierCrypter = "crypte.csv";
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Le fichier spécifié n'existe pas.");
                return;
            }

            // Flux de sortie pour envoyer le fichier CSV au serveur
            OutputStream out = socket.getOutputStream();
            System.out.println("hiorg uiosbh fgui fhdjkfdbfgjh fvb vf;n vbvc,b vcb ,nvc hv ,nbfdv  vb ");
           
            FileReader fr = new FileReader(file);
            BufferedReader br =  new BufferedReader(fr);
            String ligne;
            ArrayList<String> toutLeFichier = new ArrayList<>() ;
            while ((ligne = br.readLine()) != null) {
                toutLeFichier.add(ligne);
            }           
            br.close();
            fr.close();
            File fileCripte = new File(fichierCrypter);
            FileWriter fw = new FileWriter(fileCripte);
            BufferedWriter bw = new BufferedWriter(fw);
            for(int i=0; i < toutLeFichier.size();i++){
                System.out.println(toutLeFichier.get(i));
                bw.write(Cryptage.cryptage(Cryptage.cle, toutLeFichier.get(i))+"\n");
            }
            
            bw.close();
            fw.close();
            FileInputStream fileInputStream = new FileInputStream(fileCripte);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            socket.shutdownOutput();
            System.out.println("Fichier CSV envoyé au serveur.");
            System.out.println("serveur close : " + socket.isClosed());
            System.out.println("Fermeture de l'envoi du fichier");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    public boolean sendA(String cle) throws IOException{
        System.out.println(cle);
        try{
            OutputStream out = socket.getOutputStream();
            
            

            out.write(cle.getBytes());
            socket.shutdownOutput();
           return true; 
        }catch(Exception e){
            return false;
        }
        
    }      
        
    /**
     * Fermeture de la connexion avec le serveur
     */    
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }

    /**
     * Recevoir la réponse envoyé par le serveur 
     */
    public String recevoirReponse() throws IOException {
        //socket.shutdownOutput();// a garder si marche pas
        try {
            Thread.sleep(500); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String serverReponse ="";
        InputStream responseIn = socket.getInputStream();
        byte[] responseBuffer = new byte[1024]; // Taille du buffer pour la réponse
        int bytesRead2;
        bytesRead2 = responseIn.read(responseBuffer);
        serverReponse = new String(responseBuffer, 0, bytesRead2);
        responseIn.close();
        
         
        socket.close();
        return serverReponse;
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
        
        
        
