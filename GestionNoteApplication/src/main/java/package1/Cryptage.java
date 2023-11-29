/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ahmed.bribach
 */
public class Cryptage {
   
   public static HashMap<Integer,Character> dico = new HashMap<>();
   public static HashMap<Character,Integer> dicoReverse = new HashMap<>();
   public static ArrayList<Integer> tableauB = new ArrayList<>();
   public static ArrayList<Integer> tableauA = new ArrayList<>();
   public static String cle = "";
   static int p = 71;

    public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô()-" + " ";
    
    public static String codeAlice(int g) {
        String chaineA = "";
        for(int i=0 ; i<30 ;i++){
            int a = (int)(Math.random()*70);
            tableauA.add(a);  
            int A = expModulaire(g, a, p);
            chaineA+= " "+A;
        }
        
        return chaineA;
    }

    public static String codeBob(int g) {
        String chaineB = "";
        for(int i=0 ; i<30 ;i++){
            int b = (int)(Math.random()*70);
            tableauB.add(b);  
            int B = expModulaire(g, b, p);
            chaineB+= " "+B;
        }
        
        return chaineB;
    }

    public static int decodeAlice(int B, int a) {
        int cleSecrete = expModulaire(B, a, p);
        return cleSecrete;
    }

    public static int decodeBob(int A, int b) {
        int cleSecrete = expModulaire(A, b, p);
        return cleSecrete;
    }

    // Implémentation de l'exponentiation modulaire
    private static int expModulaire(int base, int exposant, int modulo) {
        int resultat = 1;
        base = base % modulo;

        while (exposant > 0) {
            if (exposant % 2 == 1) {
                resultat = (resultat * base) % modulo;
            }

            exposant = exposant >> 1;
            base = (base * base) % modulo;
        }

        return resultat;
    }
    
   
    
   
    public static void  remplissageduDico(){      
        for (int i = 0; i < ENSEMBLE_CARACTERES.length(); i++) {
            char character = ENSEMBLE_CARACTERES.charAt(i);
            dico.put(i, character);
            dicoReverse.put(character,i);
           
        }
    }
    public static void creationClefBob(String ss){
        cle = "";
        remplissageduDico();
        String tableau[]= ss.split(" ");
        int a = 0;
        
        for(int i=0; i< tableau.length;i++){
             tableau[i] = tableau[i].trim();
            if(!tableau[i].equals("")){
                 
                cle+=dico.get(decodeBob(Integer.parseInt(tableau[i]),tableauB.get(a)));
                a++;
                
            }
           
        }
       
        
    }
    
    public static String creationClefAlice(String ss){
        cle = ""; 
        remplissageduDico();
        String tableau[]= ss.split(" "); 
        int a = 0;
        for(int i=0; i< tableau.length;i++){
            tableau[i] = tableau[i].trim();
            if(!tableau[i].equals("")){
                
                
                char c = dico.get(decodeAlice(Integer.parseInt(tableau[i]),tableauA.get(a)));
                a++;
                cle+=c; 
                
            }
           
        }
        
        return cle;
    }
   
    public static String cryptage(String cle, String messageACrypter){      
       remplissageduDico();
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
        
        System.out.println(cle);
        System.out.println(cle.length());  
    }
}