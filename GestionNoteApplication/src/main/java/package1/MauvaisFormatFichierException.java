
package GestionNoteApplication.src.main.java.package1;

/**
 * Exception levée en cas de mauvais format de fichier.
 * Cette exception est utilisée pour signaler un problème de format de fichier.
 * Elle peut contenir un message et un titre pour identifier plus précisément l'erreur.
 *
 * @author enzo.cluzel
 */
public class MauvaisFormatFichierException extends Exception {

    String message ;
    String titreException ;

    /**
     * Constructeur par Default
     *
     */
    public MauvaisFormatFichierException() {
    }


    /**
     * Constructeur prenant un message et un titre pour l'exception.
     * @param message Le message d'erreur.
     * @param titre Le titre associé à l'exception.
     */
    public MauvaisFormatFichierException(String message, String titre) {
        this.message = message;
        this.titreException = titre;
    }

    /**
     * Renvoie le message d'erreur associé à cette exception.
     * @return Le message d'erreur.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Renvoie le titre de l'exception.
     * @return Le titre associé à cette exception.
     */
    public String getTitre() {
        return titreException;
    }
    
    
}
