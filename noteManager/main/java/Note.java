
/*
 * Note.java                                            //2023
 * IUT de Rodez, BUT2, aucun droit d'auteur
 */

 package noteManager.main.java;

 /**
  * Note associé à une eval, ressource ou compétence
  * La note peut être changé et créé
  * @author Enzo Cluzel
  */
public class Note {

    /** la valeur de la note */
    private double valeur ;

    /**
     * crée une instance de note
     * @param valeur , valeur de la note
     * @throws NoteException si la valeur n'est pas une valeur d'une note correcte
     */
    public Note(double valeur) throws NoteException {
        if(isNote(valeur)) {
            this.valeur = valeur;
        } else {
            throw new NoteException("La valeur de la note n'est pas bonne");
        }
    }

    /**
     * Modification de la valeur de la note
     * @param nouvelleValeur , nouvelle valeur de la note 
     * @throws NoteException si la valeur argument n'est pas une bonne valeur de note
    */
    public void setNote(double nouvelleValeur) throws NoteException {
        if(isNote(nouvelleValeur)) {
            this.valeur = nouvelleValeur;
        } else {
            throw new NoteException("La valeur de la note n'est pas bonne");
        }
        
    }
    
    public double getNote(){
        return valeur;
    }
    /**
     * @param valeur , la valeur à vérifié
     * @return true si la valeur est une note correcte sinon false
     */
    public static boolean isNote(double valeur) {
        return (valeur >= 0.0 && valeur <=20.0) || valeur ==-1;
    }
    
    public boolean equals(Note note){
        if(valeur == note.getNote()){
            return true;
        }else{
            return false;
        }
    }
}

