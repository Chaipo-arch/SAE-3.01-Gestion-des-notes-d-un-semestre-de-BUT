/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametrage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author enzo.cluzel robin.britelle
 */
public abstract class Parametrage {
   
    /**
     * 
     */
    protected File file;
    /** TODO comment field role (attribute, association) */
    protected ArrayList<String> contenue ;
    
    
    
    
    
    /**
     * 
     * @param chemin 
     * @throws IllegalArgumentException 
     */
    public Parametrage(String chemin) throws IllegalArgumentException {;
        if(isCSV(chemin)) {
            contenue = new ArrayList();
            file = new File(chemin);   
            
        } else {
            throw new IllegalArgumentException("pas csv");
        }
        
    }
    
    /**
     * verifie si le fichier est un csv
     * @return return un boolean qui est égal a true si le fichier est au format .csv
     *          et false sinon
     */
    private static boolean isCSV(String chemin) {
        File file = new File(chemin);
        return file.exists() && file.getName().toLowerCase().endsWith(".csv");    
    }
    
    /** TODO comment method role
     * 
     */
    public abstract void parse();
    
    /** TODO comment method role
     * @return b
     * 
     */
    public abstract boolean isCorrect();
}
