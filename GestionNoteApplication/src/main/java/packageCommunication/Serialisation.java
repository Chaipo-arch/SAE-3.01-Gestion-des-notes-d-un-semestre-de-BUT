/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;




/**
 *
 * @author robin.britelle
 */
public class Serialisation implements Serializable{
    
    
    // Méthode pour sérialiser un objet
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

     // Méthode pour désérialiser un objet
     public static Object deserialiserObjet() {
         
         //zone de stockage a mettre en Fichier Binaire TODO
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
     
     
     private static String nomFichierDonnees = "stock.bin";
     
     public static void serialiserStockage(){        
        
        try {
             if(!new File(nomFichierDonnees).exists()) {
                  new File(nomFichierDonnees).createNewFile();
             } else {
                 new File(nomFichierDonnees).delete();
                 new File(nomFichierDonnees).createNewFile();
             }
             
             
            // Création d'une instance de Stockage
            Stockage monStockage = Stockage.getInstance();

            // Ajout d'une compétence
            //Competence nouvelleCompetence = new Competence(/* ... */); // Créez une nouvelle compétence
            //monStockage.ajouterCompetence(nouvelleCompetence);

            // Suppression d'une compétence
            //Competence competenceASupprimer = /* ... */; // Obtenez la compétence à supprimer
            //monStockage.supprimerCompetence(competenceASupprimer);

            // Appel de la méthode pour sérialiser l'objet Stockage
            Serialisation.serialiserObjet(monStockage);
            
            
            //System.out.println("Objet Serialisé Normalement ?");
        }
        catch(IOException e) {
          System.out.println("Erreur sauvegarde ");
        }
         
     }
     
     
     
    public static void main(String[] args) {
            // Création d'une instance de Personne
        //Personne personne = new Personne("Alice", 30, "123 Rue de la Java");

            // Appel de la méthode pour sérialiser l'objet
        Serialisation.serialiserStockage();
    }
}
   
