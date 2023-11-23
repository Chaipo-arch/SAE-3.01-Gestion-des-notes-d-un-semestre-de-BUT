/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ahmed.bribach
 */
public class Encryptage {
   
    public static HashMap<Integer,Character> dico = new HashMap<>();
   public static HashMap<Character,Integer> dicoReverse = new HashMap<>();

    public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô" + " ";
   
    public static void  remplissageduDico(){      
        for (int i = 0; i < ENSEMBLE_CARACTERES.length(); i++) {
            char character = ENSEMBLE_CARACTERES.charAt(i);
            dico.put(i, character);
            dicoReverse.put(character,i);
           
        }
    }
    public static String creationClef(int longueurCle){
        String cleEncryptage = "";
        for(int i = 0 ;i < longueurCle;i++){
            cleEncryptage += ENSEMBLE_CARACTERES.charAt((int)(Math.random()*ENSEMBLE_CARACTERES.length()));          
        }        
        return cleEncryptage;
    }
   
    public static String cryptage(String cle, String messageACrypter){      
       String chaine = "";
        for (int i = 0; i < messageACrypter.length(); i++) {
            int nombre = (dicoReverse.get(messageACrypter.charAt(i)) + dicoReverse.get(cle.charAt(i % cle.length()))) % ENSEMBLE_CARACTERES.length();
            chaine += dico.get(nombre);
        }
        return chaine;
    }
    public static String decryptage(String cle, String messageCrypter){
        String chaine = "";
        for (int i = 0; i < messageCrypter.length(); i++) {
            int nombre = (dicoReverse.get(messageCrypter.charAt(i)) - dicoReverse.get(cle.charAt(i % cle.length()))) % ENSEMBLE_CARACTERES.length();
            if (nombre < 0) {
                nombre += ENSEMBLE_CARACTERES.length();
            }
            chaine += dico.get(nombre);
        }
        return chaine;
    }
   /* public String static cryptageDiffieHelman(){
        
        return "";   
    }*/
    public static void main(String[] args){
        remplissageduDico();
       
        
       
        String cle = creationClef(10);
        System.out.println(" la clé généré est : " + cle);
        System.out.println("le message encrypté devient " + cryptage(cle,"cc les gens"));
        String messageCrypte = cryptage(cle,"ygfyugtoutgoètttèttitrrur");
        System.out.println(decryptage(cle,messageCrypte));
       
        //System.out.println(dico.keySet().toArray()[dico.get("A")+5]);
       
       
       
       
         
    }
}
