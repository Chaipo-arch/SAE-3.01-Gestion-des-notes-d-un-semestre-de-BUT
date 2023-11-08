/*
 * GestionNote.java                                           26/10/2023
 * IUT De Rodez, info2, aucun droit d'auteur
 */
package noteManager.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import noteManager.main.*;

/**
 * Gestionnaire des Notes de l'application
 * @author enzo.cluzel
 */
public class GestionNote {
    
    private static String nomFichierDonnees = "donnees.txt";
    private static Stockage stockageDonnees ;
    
    
    /**
     * Sauvegarde les données de l'application dans un 
     * fichier externe, les données sauvegardé sont celle qui sont 
     * stockées dans stockage
     */
    public void enregistrerDonnees() {
         try {
             if(!new File(nomFichierDonnees).exists()) {
                  new File(nomFichierDonnees).createNewFile();
             } else {
                 new File(nomFichierDonnees).delete();
                 new File(nomFichierDonnees).createNewFile();
             }
             
            ObjectOutputStream donnees = new ObjectOutputStream(new FileOutputStream(nomFichierDonnees));
            donnees.writeObject(stockageDonnees);
            //donnees.writeObject(stockageDonnees.ressources);
             //donnees.writeObject(stockageDonnees.evaluations);
        
            donnees.close();
        }
        catch(IOException e) {
          System.out.println("Erreur sauvegarde ");
        }
        
    }
    /**
     * Recupere les données sauvegarder dans un fichier externe crée à partir 
     * de enregistrer données
     * @return le stockage ou sont stockées les données
     */
    public Stockage recupererDonnees() throws IOException, ClassNotFoundException {
        if(new File(nomFichierDonnees).isFile()){
            ObjectInputStream donnees;
            donnees = new ObjectInputStream(new FileInputStream(nomFichierDonnees));
            stockageDonnees = (Stockage)donnees.readObject();
           donnees.close();

            
        }else{
            // Parametrage
        }
        

        return null; // bouchou
    
        
    }
    
    /**
     * Reinitialie toutes les données de l'application en supprimant 
     * les données de stockage
     */
    public void reinitialiser() {
        stockageDonnees.supprimerDonnees();
    }
    
    /**
     * Permet de consulter toutes les notes par compétences et Ressource pour 
     * chaque éval
     */
    public void consulterNote() {
        //stockageDonnees.competences // TODO 
        
    }
    
    public void communiquer() {
        
    }
    
    /**
     * Permet de données une note à une évaluation selon une compétence et 
     * une resource donné

     */
    public void saisirNote() {
        //stockageDonnees.competences // TODO 
    }
    
    /**
     * permet d'importer un paramètre depuis le poste en local ou depuis un 
     * poste sur le réseau
     */
    public void importerParametre() {
        // new Parametrage() // TODO
    }
    
    /**
     * calcule les moyennes par compétence, ressources et la moyennes générale 
     * avec les notes qui sont disponibles
     */
    public void lancerCalculs() {
        //stockageDonnees.competences // TODO 
    }
    
}