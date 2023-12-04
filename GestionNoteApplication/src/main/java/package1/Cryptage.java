/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * classe outils de cryptage
 * permet de crypter Diffie Helman et Vigenére
 * @author ahmed.bribach
 */
public class Cryptage {
   
   public static HashMap<Integer,Character> dico = new HashMap<>();
   public static HashMap<Character,Integer> dicoReverse = new HashMap<>();
   public static int bOUa;
   public static String cle = "";
   public static int p = 509;
   public static int g;
   public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô()-'" + " ";
    
  
  public static void genereP(){
      Random random = new Random();
      int nb = random.nextInt(10001) + 10000;;
      System.out.println(nb);
      while(!estNombrePremier(nb)){
          nb++;
      }
      
          System.out.println(nb + " est le nouveau nombre premier");
        p = nb;
  }
  
  public static boolean estNombrePremier(int nombre) {
        if (nombre <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }
    /**
     * 
     * @param g
     * @return 
     */
    public static int codeAliceEtBob() {
        genereG();
        String chaineB = "";
        bOUa = (int)(Math.random()*p-1); 
        int B = expModulaire(g, bOUa, p);
        return B;
    }

    /**
     * 
     * @param B
     * @param a
     * @return
     */
    public static int decodeAliceOuBOB(int BouA, int aOUb) {
        int cleSecrete = expModulaire(BouA, aOUb, p);
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
        genereP();
    }
    
    /**
     * 
     * @param ss
     */
    public static void creationClefBobOuAlice(int BouA){
        
        
        
        int nouvelIndex;
        String CleRemplacement="";
        for(int i=0; i< cle.length();i++){
            nouvelIndex = (dicoReverse.get(cle.charAt(i))+decodeAliceOuBOB(BouA,bOUa))%ENSEMBLE_CARACTERES.length();
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
        System.out.println("hoe");
        genereG();
        System.out.println("hoe");
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
        List<Integer> toutLesGPossible = new ArrayList<>();
    	for(int i=2;i<p-1 && toutLesGPossible.size()<20;i++) {
            if(testG(i)) {
                toutLesGPossible.add(i);   			
            }
    	}
        System.out.println("enfin");
        if (!toutLesGPossible.isEmpty()) {
            Random random = new Random();
            g = toutLesGPossible.get(random.nextInt(toutLesGPossible.size()));
            return true;
        }
    	return false;
    }
    public static void main(String[] args){
        
        creationCleEtape1();
        System.out.println(cle);
        
        System.out.println("TEST de G: "+ genereG() + " et G est : " +g);
        creationClefBobOuAlice(decodeAliceOuBOB(codeAliceEtBob(), bOUa));
        System.out.println(cle);
        System.out.println(cle.length());
    }

}