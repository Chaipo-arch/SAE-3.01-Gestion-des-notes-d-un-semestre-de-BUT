/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;

/**
 *
 * @author enzo.cluzel
 */
public class MauvaisFormatFichierException extends Exception {

    String message ;
    String titreException ;
    public MauvaisFormatFichierException() {
    }

    public MauvaisFormatFichierException(String message) {
       this.message = message  ; //To change body of generated methods, choose Tools | Templates.
    }
     public MauvaisFormatFichierException(String message,String titre) {
       this.message = message  ; //To change body of generated methods, choose Tools | Templates.
       this.titreException = titre  ;
    }
    
    public String getMessage() {
        return message;
    }
    public String getTitre() {
        return titreException;
    }
    
    
}
