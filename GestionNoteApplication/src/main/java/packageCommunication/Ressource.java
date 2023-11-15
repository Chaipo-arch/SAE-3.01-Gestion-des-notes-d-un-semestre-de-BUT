/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

/**
 *
 * @author robin.britelle
 */


/*
 * Ressource.java                                    31 oct. 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */

import java.io.Serializable;
import java.util.ArrayList;

/** TODO comment class responsibility (SRP)
 * @author Cluzel
 *
 */
public class Ressource implements Serializable {

    /** TODO comment field role (attribute, association) */
    public String libelle;

    /** TODO comment field role (attribute, association) */
    public ArrayList<Evaluation> evaluations = new ArrayList();
    
    public String type;
    public String identifiant;
    public String poid;
    
    /** TODO comment initial state
     * @param string
     * @param string2
     * @param string3
     * @param string4 
     */
    public Ressource(String string, String string2, String string3, String string4) {
        type = string;
        identifiant= string2;
        poid = string4;
        libelle = string3;
    }
    /** TODO comment initial state
     * @param string
     */
    public Ressource(String string) {
        libelle = string;
    }
    /** TODO comment method role
     * @param evals
     */
    public void ajouterEvaluations(ArrayList<Evaluation> evals) {
        evaluations.addAll(evals);
        
    }

}
