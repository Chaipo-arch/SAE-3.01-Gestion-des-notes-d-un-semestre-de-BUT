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
public class Cryptage {
   
   public static HashMap<Integer,Character> dico = new HashMap<>();
   public static HashMap<Character,Integer> dicoReverse = new HashMap<>();
   
   private static String cle = "";
   static int p = 73;

    public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô" + " ";
   
    public static int codeAlice(int g, int a){
        //a = 6;
        int A = (int) (Math.pow(g, a) % p);
        return A;
    }
    public static int codeBob(int g, int b){
       // b = 15;
        int B = (int) (Math.pow(g, b) % p);
        return B;
    }
   
    public static int decodeAlice(int B, int a){
     
        int cleSecrete = (int) (Math.pow(B, a) % p);
        return cleSecrete;
    }
   
    public static int decodeBob(int A, int b ){
       
        int cleSecrete = (int) (Math.pow(A, b) % p);
        return cleSecrete;
    }
   
   
   
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
    public static void main(String[] args){
        remplissageduDico();
        String message = "lemassageaenvoyer";
        for(char c : message.toCharArray()){
            int a = (int) (Math.random() * 20);
            int b = (int) (Math.random() * 20);
            System.out.println(a +"  espace  "+ b);
            int A = codeAlice(5,a);
            int B = codeBob(5,b);
            int cleA = decodeAlice(B,a);
            int cleB = decodeBob(A,b);
            
            if(cleA == cleB){
                cle += dico.get(cleA);
            } else {
                System.out.println("erreur");
            }
            
           
        }
        System.out.println("la cle finale est égale à : "+ cle);
       
     
       
       
         
    }
}