package GestionNoteApplication.src.main.java.package1;

import GestionNoteApplication.src.main.controller.NotificationController;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Cette classe représente un client pour la connexion et l'envoi 
 * de fichiers CSV à un serveur distant.
 * @author Robin Britelle, Enzo Cluzel, Ahmed Bribach
 */
public class Client {

    /**
     * Constructeur par défaut de la classe Client.
     */
    public Client(){
        
    }
    private Socket socket ;
    
    public static boolean checkServer = true;
    
    
    
    /**
     * Établit une connexion avec le serveur spécifié en parametres
     * a partir de Socket
     * @param serverIP Adresse IP du serveur.
     * @param port Port du serveur.
     */
    public void connection(String serverIP, int port) {
        try {
            this.socket = new Socket(serverIP, port);
            this.socket.setKeepAlive(true);
        } catch (IOException ex) {
            checkServer = false;
            NotificationController.popUpMessage("Erreur, aucun server en attente","");
        }
    }
    
    
    
    /**
     * Envoie un fichier CSV crypté au serveur.
     * Verifie l'existance du fichier a envoyer
     * @param filePath Chemin du fichier CSV.
     */
    public void sendCSVFileToServer(String filePath) {
        try {
            String fichierCrypter = "parametrageNationaleCrypte.csv";
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
            
            
            FileInputStream fileInputStream = new FileInputStream(fileCripte);
            byte[] buffer = new byte[1024];
            int bytesRead;
            
           
            bw.close();
            fw.close();
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            socket.shutdownOutput();
            System.out.println("Fichier CSV envoyé au serveur.");
            
            NotificationController NotificationController = new NotificationController();
            NotificationController.showNotification("Votres fichier a bien était envoyé");
            
            System.out.println("serveur close : " + socket.isClosed());
            System.out.println("Fermeture de l'envoi du fichier");
        } catch (IOException e) {
            System.out.println("erreur Client.java");

        }
    }
    
    
     /**
     * Envoie une clé au serveur
     * @param cle Valeur de la clé à envoyer.
     * @return Retourne true si l'envoi a réussi, sinon false.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     */
    public boolean sendA() throws IOException{
        System.out.println();
        
        try{
            OutputStream out = socket.getOutputStream();
            Cryptage.creationCleEtape1();
            String espace="\n";
            System.out.println("g = "+Cryptage.g);
            System.out.println("p = "+Cryptage.p);
            String p = Cryptage.p +"";
            String g = Cryptage.g+"";
            
            out.write(Cryptage.cle.getBytes());
            out.write(espace.getBytes());
            out.write(p.getBytes());
            out.write(espace.getBytes());
            out.write(g.getBytes());
            out.write(espace.getBytes());
            Cryptage.codeAliceEtBob();
            String cleString = Cryptage.bOUa+""; 
            out.write(cleString.getBytes());
            socket.shutdownOutput();
           return true; 
        }catch(Exception e){
            return false;
        }
        
    }         
    
    
    /**
     * Ferme la connexion avec le serveur en fermant le socket
     */
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }
 
    /**
     * Reçoit la réponse du serveur apres un envoie
     * @return La réponse reçue du serveur.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     */
    public String recevoirReponse() throws IOException {

        try {
            Thread.sleep(500); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
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
    
    
    
    /**
     * Vérifie si une chaîne saisie par l'utilisateur correspond à une adresse IP valide.
     * @param ip Chaîne à vérifier.
     * @return true si la chaîne est une adresse IP valide, sinon false.
     */
     public static boolean validateIP(String ip) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
    

}
        
        
        
