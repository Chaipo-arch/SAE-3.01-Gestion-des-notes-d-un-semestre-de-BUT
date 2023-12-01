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

    /**
     * Constructeur sans message de MauvaisFormatFichierException
     */
    public MauvaisFormatFichierException() {
    }

    /**
     * Constructeur avec message de MauvaisFormatFichierException
     * @param message le message de l'exception
     */
    public MauvaisFormatFichierException(String message) {
       this.message = message  ; //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * @return Le message de l'exception
     */
    public String getMessage() {
        return message;
    }
    
}
