/*
 * Evaluation.java                                    31 oct. 2023
 * IUT Rodez, info1 2022-2023, pas de copyright ni "copyleft"
 */
package GestionNoteApplication.src.main.java.modele;

import java.io.Serializable;

/** TODO comment class responsibility (SRP)
 * @author Cluzel
 *
 */
public class Evaluation implements Serializable {

    String type;
    String date;
    String poid;
    /** TODO comment initial state
     * @param string
     * @param string2
     * @param string3
     */
    public Evaluation(String string, String string2, String string3) {
        type = string;
        date = string2;
        poid = string3;
    }

}
