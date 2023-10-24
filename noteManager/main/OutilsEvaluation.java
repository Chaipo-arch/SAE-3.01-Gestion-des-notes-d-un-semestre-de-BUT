/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;

/**
 * Interface OutilEvaluation qui permet un contrat de création
 * @author alexandre.brouzes
 */
public interface OutilsEvaluation {
    
    String toString();
    double calculMoyenne(Evaluation[] listeEvaluation);
}
