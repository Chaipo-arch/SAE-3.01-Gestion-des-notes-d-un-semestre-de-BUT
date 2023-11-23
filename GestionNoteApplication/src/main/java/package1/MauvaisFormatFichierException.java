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
    public MauvaisFormatFichierException() {
    }

    public MauvaisFormatFichierException(String message) {
       this.message = message  ; //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getMessage() {
        return message;
    }
    
}
