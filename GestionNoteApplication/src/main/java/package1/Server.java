package GestionNoteApplication.src.main.java.package1;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static ObjectInputStream in;
    

    public static void createServer() {
        try {
            int port = 10008;
            serverSocket = new ServerSocket(port);
            
            System.out.println("Serveur en attente de connexion...");
        } catch (IOException ex) {
           
        }
    }
    public static boolean connexion()  {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connexion établie avec " + clientSocket.getInetAddress());
            
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
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
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            File fichier = new File(filePath);
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> toutLeFichier = new ArrayList<>();
            while (br.readLine() != null) {
                toutLeFichier.add(br.readLine());
            }
            
            br.close();
            fr.close();
            
            FileWriter fw = new FileWriter(fichier);
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i < toutLeFichier.size()-1;i++){
                System.out.println(Cryptage.decryptage(Cryptage.cle, toutLeFichier.get(i)));
                bw.write(Cryptage.decryptage(Cryptage.cle, toutLeFichier.get(i))+"\n");
            }
            bw.close();
            fw.close();
            
            
            //Cryptage.decryptage(Cryptage.cle, toutLeFichier);
           
            
            System.out.println("Fichier CSV reçu et stocké : " + filePath);
            //in.close();
            //fileOutputStream.close();
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
    public static void reponse(String message) throws IOException {
        
        System.out.println("Envoie Reponse");
        OutputStream os = clientSocket.getOutputStream();
        os.write(message.getBytes());
        
        /*OutputStream os = clientSocket.getOutputStream();
        OutputStreamWriter ow = new OutputStreamWriter(os);
        BufferedWriter wr = new BufferedWriter(ow);         
        wr.write("Test\n".);*/
        clientSocket.shutdownOutput();
        
        try {
            Thread.sleep(500); // Mettre en pause pendant 1 seconde
        } catch (InterruptedException e) {
         // Gérer une éventuelle exception si l'interruption se produit pendant la pause
            e.printStackTrace();
        }
    }
    
    
    
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
