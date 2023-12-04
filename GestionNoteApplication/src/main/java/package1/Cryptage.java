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
 * classe outils de cryptage
 * permet de crypter Diffie Helman et Vigenére
 * @author ahmed.bribach
 */
public class Cryptage {
   
   public static HashMap<Integer,Character> dico = new HashMap<>();
   public static HashMap<Character,Integer> dicoReverse = new HashMap<>();
   public static int b;
   public static int a;
   public static String cle = "";
   static int p = 509;
   public static int g;
   public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô()-'" + " ";
    
    /**
     * génére le code de Alice
     * @param g
     * @return 
     */
    public static String codeAlice() {
        genereG();
        String chaineA;    
        a = (int)(Math.random()*p-1);
        int A = expModulaire(g, a, p);
        chaineA= ""+A;
        return chaineA;
    }

    /**
     * 
     * @param g
     * @return 
     */
    public static String codeBob() {
        genereG();
        String chaineB = "";
        b = (int)(Math.random()*p-1); 
        int B = expModulaire(g, b, p);
        chaineB=""+B;
        return chaineB;
    }

    /**
     * 
     * @param B
     * @param a
     * @return
     */
    public static int decodeAlice(int B, int a) {
        int cleSecrete = expModulaire(B, a, p);
        return cleSecrete;
    }
    
    /**
     * 
     * @param A
     * @param b
     * @return
     */
    public static int decodeBob(int A, int b) {
        int cleSecrete = expModulaire(A, b, p);
        return cleSecrete;
    }

    /**
     * 
     * @param base
     * @param exposant
     * @param modulo
     * @return
     */
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
    
   
    
   /**
    * 
    */
    public static void  remplissageduDico(){      
        for (int i = 0; i < ENSEMBLE_CARACTERES.length(); i++) {
            char character = ENSEMBLE_CARACTERES.charAt(i);
            dico.put(i, character);
            dicoReverse.put(character,i);
           
        }
    }
    
    /**
     * 
     * @param ss
     */
    public static void creationClefBob(int ss){
        
        remplissageduDico();
        
        int nouvelIndex;
        String CleRemplacement="";
        for(int i=0; i< cle.length();i++){
        	nouvelIndex = (dicoReverse.get(cle.charAt(i))+decodeBob(ss,b))%ENSEMBLE_CARACTERES.length();
            CleRemplacement += dico.get(nouvelIndex);
            
        }
        cle = CleRemplacement;
           
    }
    
       
        
    
    /**
     * 
     * @param ss
     * @return
     */
    public static void creationClefAlice(int ss){
    	remplissageduDico();
        
        int nouvelIndex;
        String CleRemplacement="";
        for(int i=0; i< cle.length();i++){
        	nouvelIndex = (dicoReverse.get(cle.charAt(i))+decodeAlice(ss,a))%ENSEMBLE_CARACTERES.length();
            CleRemplacement += dico.get(nouvelIndex);
            
        }
        cle = CleRemplacement;
    }
   
    /**
     * 
     * @param cle
     * @param messageACrypter
     * @return
     */
    public static String cryptage(String cle, String messageACrypter){      
       remplissageduDico();
       String chaine = "";
        for (int i = 0; i < messageACrypter.length(); i++) {
            int nombre = (dicoReverse.get(messageACrypter.charAt(i)) + dicoReverse.get(cle.charAt(i % cle.length()))) % ENSEMBLE_CARACTERES.length();
            chaine += dico.get(nombre);
        }
        return chaine;
    }
    
    /**
     * Gros Commentairre
     * @param cle
     * @param messageCrypter
     * @return
     */
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
    
    /**
     * 
     * @return
     */
    public static String creationCleEtape1(){
        remplissageduDico();
        genereG();
        String chaineCle="";
        for(int i= 0 ; i<(int)(Math.random()*1000);i++){
            chaineCle+=dico.get((int)(Math.random()*ENSEMBLE_CARACTERES.length()));
        }
        cle = chaineCle;
        return cle;
    }
    public static boolean testG(int g) {
    	ArrayList<Integer> G = new ArrayList<>();
    	for(int i=0;i<p-1;i++) {
    		if(G.contains(expModulaire(g, i, p))) {
    			return false;
    		}
    		G.add(expModulaire(g, i, p));
    	}
    	
    	
        
    	return true;
    }
    
    public static boolean genereG() {
    	for(int i=0;i<p-1;i++) {
    		if(testG(i)) {
    			g=i;
    			return true;
    		}
    	}
    	return false;
    }
    public static void main(String[] args){
        remplissageduDico();
        creationCleEtape1();
        System.out.println(cle);
        
        System.out.println("TEST de G: "+ genereG() + " et G est : " +g);
        creationClefBob(decodeBob(Integer.parseInt(codeAlice()), b));
        System.out.println(cle);
        System.out.println(cle.length());
    }

}