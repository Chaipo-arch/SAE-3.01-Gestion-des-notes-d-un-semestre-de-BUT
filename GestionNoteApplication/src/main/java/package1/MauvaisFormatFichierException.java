/*
 * MauvaisFormatFichierException.java
 */
package GestionNoteApplication.src.main.java.package1;

/**
 * Exception pour les fichiers lues non correstes lors du parse 
 * @author enzo.cluzel
 */
public class MauvaisFormatFichierException extends Exception {

    String message ;
    public MauvaisFormatFichierException() {
    }

    public MauvaisFormatFichierException(String message) {
       this.message = message  ; //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getMessage() {
        return message;
    }
    
}
