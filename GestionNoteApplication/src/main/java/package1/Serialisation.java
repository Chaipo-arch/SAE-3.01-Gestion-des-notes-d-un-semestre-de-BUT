
package GestionNoteApplication.src.main.java.package1;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Ressource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Classe pour la sérialisation et la désérialisation d'objets.
 * Cette classe implémente l'interface Serializable.
 * @author robin.britelle
 */
public class Serialisation implements Serializable{
    
    
    /**
     * Méthode pour sérialiser un objet.
     * 
     * @param objet Objet à sérialiser.
     */
     public static void serialiserObjet(Object objet) {
         try {
             String fichier = "stock.bin";
             FileOutputStream fichierSortie = new FileOutputStream(fichier);
             ObjectOutputStream sortieObjet = new ObjectOutputStream(fichierSortie);
             
             sortieObjet.writeObject(objet);
             sortieObjet.close();
             
             fichierSortie.close();
             System.out.println("L'objet a été sérialisé avec succès dans " + fichier);
             
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     /**
     * Méthode pour désérialiser un objet.
     * 
     * @return Objet désérialisé.
     */
     public static Object deserialiserObjet() {
         
         
    	 String fichier = "stock.bin";
         Object objetDeserialise = null;
    	 
         try {
             FileInputStream fichierEntree = new FileInputStream(fichier);
             ObjectInputStream entreeObjet = new ObjectInputStream(fichierEntree);
             
             objetDeserialise = entreeObjet.readObject();
             entreeObjet.close();
             fichierEntree.close();
             
         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         }
         return objetDeserialise;
     }
     
     private static String nomFichierDonnees1 = "stock.bin";
     

     /**
     * Méthode pour sérialiser une instance de Stockage.
     */
     public static void serialiserStockage(){        
        
        try {
             if(!new File(nomFichierDonnees1).exists()) {
                  new File(nomFichierDonnees1).createNewFile();
             } else {
                 new File(nomFichierDonnees1).delete();
                 new File(nomFichierDonnees1).createNewFile();
             }
             
             
            // Création d'une instance de Stockage
            Stockage monStockage = Stockage.getInstance();


            Serialisation.serialiserObjet(monStockage);
        }
        catch(IOException e) {
          System.out.println("Erreur sauvegarde ");
        }
         
     }
     

     
     
     
}
   
