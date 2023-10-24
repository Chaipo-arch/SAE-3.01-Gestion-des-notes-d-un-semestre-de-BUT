/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stock.test;

import java.util.ArrayList;
import stock.Stockage;
import stock.Competence;

/**
 *
 * @author enzo.cluzel
 */
public class testStockage {
    public static void main(String[] args) {
       Stockage stockage = Stockage.getInstance();
       Competence competence = new Competence("U2.1");
       ArrayList<Competence> listeCompe = new ArrayList<>();
       listeCompe.add(competence);
       stockage.addCompetences(listeCompe);
       System.out.println(stockage.recherche("U2."));
       System.out.println(stockage.recherche("U2.1"));
        
    }
}
