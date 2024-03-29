/*
 * GestionNote.java                                           26/10/2023
 * IUT De Rodez, info2, aucun droit d'auteur
 */
package GestionNoteApplication.src.main.java.package1;

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


/**
 * Gestionnaire des Notes de l'application
 * @author enzo.cluzel
 */
public class GestionNote {
    
    private static String nomFichierDonnees = "donnees.txt";
    
    
    
    /**
     * Sauvegarde les données de l'application dans un 
     * fichier externe, les données sauvegardé sont celle qui sont 
     * stockées dans stockage
     */
    public static void enregistrerDonnees() {
        
         try {
             if(!new File(nomFichierDonnees).exists()) {
                  new File(nomFichierDonnees).createNewFile();
             } else {
                 new File(nomFichierDonnees).delete();
                 new File(nomFichierDonnees).createNewFile();
             }
             
            ObjectOutputStream donnees = new ObjectOutputStream(new FileOutputStream(nomFichierDonnees));
            donnees.writeObject(Stockage.getInstance()); 
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
    public static void recupererDonnees() throws IOException, ClassNotFoundException {
        if(new File(nomFichierDonnees).isFile()){
            System.out.println("ok");
            ObjectInputStream donnees;
            donnees = new ObjectInputStream(new FileInputStream(nomFichierDonnees));
            Stockage stockageDonnees;
            
            stockageDonnees = (Stockage)donnees.readObject();
            System.out.println(stockageDonnees.ressources);
            Stockage.getInstance().addCompetences(stockageDonnees.competences);
            for(Competence c: stockageDonnees.competences) {
               
                Competence c2 = null;
                try {
                    c2 = new Competence(c.identifiant,c.libelle);
                } catch (NoteException ex) {
                    System.out.println(c.getRessources());
                }
                c2.getRessources().clear();
                Stockage.getInstance().addRessources(c.getRessources(), c2);
            }
           
            Stockage.getInstance().setUserName(stockageDonnees.getUserName());
            Stockage.getInstance().addEvaluations(stockageDonnees.evaluations);
            Stockage.getInstance().setSemestre(stockageDonnees.getSemestre());
            Stockage.getInstance().setParcour(stockageDonnees.getParcour());
           donnees.close();
        }
    }
    
    /**
     * Reinitialie toutes les données de l'application en supprimant 
     * les données de stockage
     */
    public void reinitialiser() {
        Stockage.getInstance().supprimerDonnees();
    }
    
    
}