/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;

import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author enzo.cluzel
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
    public Parametrage(File chemin) throws IllegalArgumentException {;
        
            contenue = new ArrayList();
        file = chemin; 
    }
    
   
    
    /** TODO comment method role
     * 
     */
    public abstract void parse() throws IOException, MauvaisFormatFichierException, EvaluationException, NoteException;

    
}