/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.* ;



/**
 *
 * @author robin.britelle
 */
public class Serialisation implements Serializable{
    
    
    // Méthode pour sérialiser un objet
     public static void serialiserObjet(Object objet) {
         try {
             String fichier = "Z://stockage.txt";
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
    	 String fichier = "Z:\\stockage.txt";
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
    
}
