/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.modele;

/**
 *
 * @author robin.britelle
 */


/*
 * Competence.java                                    31 oct. 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

import java.io.Serializable;
import java.util.ArrayList;
import GestionNoteApplication.src.main.java.modele.Ressource;

/** TODO comment class responsibility (SRP)
 * @author Cluzel
 *
 */
public class Competence implements Serializable {

    /** TODO comment field role (attribute, association) */
    public String identifiant;

    /** TODO comment field role (attribute, association) */
    public ArrayList<Ressource> ressources = new ArrayList();
    
    /** TODO comment initial state
     * @param string
     */
    public Competence(String string) {
       identifiant = string;
    }

    /** TODO comment method role
     * @param ressources 
     * @param evals
     * @return a
     */
    public boolean ajouterRessources(ArrayList<Ressource> ressources) {
        return this.ressources.addAll(ressources);
        
        
    }

}

