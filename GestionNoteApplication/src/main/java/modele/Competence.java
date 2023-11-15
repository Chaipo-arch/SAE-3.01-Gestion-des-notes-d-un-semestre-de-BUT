/*
 * Competence.java                                    31 oct. 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package GestionNoteApplication.src.main.java.modele;

import java.io.Serializable;
import java.util.ArrayList;

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
