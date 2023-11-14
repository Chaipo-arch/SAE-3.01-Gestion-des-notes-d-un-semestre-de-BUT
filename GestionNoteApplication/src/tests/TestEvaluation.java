/*
 * Ce commentaire en haut du fichier indique des informations sur le fichier, telles que la licence et le modèle.
 * Vous pouvez ajouter ici des informations sur la licence et la description du fichier.
 */
package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import java.util.ArrayList;

/**
 * Cette classe TestEvaluation est destinée à tester la classe Evaluation.
 */
public class TestEvaluation  {
    /**
     * fixture de test = "image fixe de test" partagée pour les différents tests de la classe Evaluation.
     * Nous initialisons des listes d'objets pour effectuer nos tests.
     */
    public static ArrayList<Evaluation> listeEvaluationValide = new ArrayList<>();
    public static ArrayList<Evaluation> listeEvaluationSansNote = new ArrayList<>();
    public static ArrayList<String> listeChaineNonValide = new ArrayList<>();
    public static ArrayList<Note> listeNoteValide = new ArrayList<>();
    public static ArrayList<Double> listeCoefficientValide = new ArrayList<>();
    public static ArrayList<Double> listeCoefficientNonValide = new ArrayList<>();

    /**
     * Cette méthode effectue la préparation de notre environnement de test en créant des objets Evaluation, Note, et des listes.
     * Elle lève des exceptions de NoteException et EvaluationException.
     */
    public static void batterieDeTest() throws NoteException, EvaluationException{

        // Initialisation de listes de notes valides et de coefficients valides
        listeNoteValide.add(new Note(0.5));
        listeNoteValide.add(new Note(10.524));
        listeNoteValide.add(new Note(20));
        listeNoteValide.add(new Note(12.256));

        listeCoefficientValide.add(1.0);
        listeCoefficientValide.add(100.0);
        listeCoefficientValide.add(20.0);
        listeCoefficientValide.add(50.5);

        // Initialisation de listes de coefficients non valides
        
        listeCoefficientNonValide.add(-1.0);
        listeCoefficientNonValide.add(-9456.4898);
        listeCoefficientNonValide.add(100.0001);

        // Initialisation de listes d'évaluations valides avec des notes, des coefficients et des modalités
        listeEvaluationValide.add(new Evaluation(listeNoteValide.get(0)
                ,"QCM",listeCoefficientValide.get(0),"25/01/2023"));
        listeEvaluationValide.add(new Evaluation(listeNoteValide.get(1)
                ,"Exam sur machine",listeCoefficientValide.get(1),"Mi septembre"));
        listeEvaluationValide.add(new Evaluation(listeNoteValide.get(2)
                ,"Ecrit",listeCoefficientValide.get(2),""));
        listeEvaluationValide.add(new Evaluation(listeNoteValide.get(3)
                ,"Relevés TPs",listeCoefficientValide.get(3),"25/12/2024"));

        // Initialisation de listes d'évaluations sans note avec des modalités
        listeEvaluationSansNote.add(new Evaluation(null
                ,"QCM",1,"25/01/2023"));
        listeEvaluationSansNote.add(new Evaluation(null
                ,"Exam sur machine",10,"Mi septembre"));
        listeEvaluationSansNote.add(new Evaluation(null
                ,"Ecrit",20,""));
        listeEvaluationSansNote.add(new Evaluation(null
                ,"Relevés TPs",50.5,"25/12/2024"));

        // Initialisation de listes de chaînes non valides
        listeChaineNonValide.add("");
        listeChaineNonValide.add(null);
    }

    /**
     * tests sur la méthode ajouterNote de la classe Evaluation.
     */
    public static void testAjouterNote(){
        int nbTestNOk = 0;
        int index = 0;
        
        for(Evaluation evaluationTeste : listeEvaluationSansNote){
            
            if (!evaluationTeste.ajouterNote(listeNoteValide.get(index))){
                nbTestNOk++;
            }
            index++;
        }
        index =0;
        
        
        for(Evaluation evaluationTeste : listeEvaluationValide){
            
            if (evaluationTeste.ajouterNote(listeNoteValide.get(index))){
                nbTestNOk++;
            }
            index++;
        }
        if(nbTestNOk != 0){
            System.out.println("Il y a " + nbTestNOk + " erreur(s)");
        }else{
            System.out.println("TEST : ajouterNote VALIDE");
        }
        
        
    }

    /**
     * tests sur la méthode toString de la classe Evaluation.
     */
    public static void testToString(){

        int nbErreur = 0;
        String affichageATester = listeEvaluationValide.get(0).toString();
        String chaineAttendue = "Maths QCM 25/01/2023 1.0 0.5";
        String affichageATester2 = listeEvaluationValide.get(1).toString();
        String chaineAttendue2 = "Développement Web Exam sur machine Mi septembre 100.0 10.524";
        String affichageATester3 = listeEvaluationValide.get(3).toString();
        String chaineAttendue3 = "Anglais Relevés TPs 25/12/2024 50.5 12.256";
        String affichageATester4 = listeEvaluationSansNote.get(0).toString();
        String chaineAttendue4 = "Cryptographie QCM 25/01/2023 1.0 note non renseignée";

        if(affichageATester.equals(chaineAttendue)){
            System.out.println("Erreur dans la première chaîne comparée");
            nbErreur++;
        }
        if(affichageATester2.equals(chaineAttendue2) ){
            System.out.println("Erreur dans la deuxième chaîne comparée");
            nbErreur++;
        }
        if(affichageATester3.equals(chaineAttendue3)){
            System.out.println("Erreur dans la troisième chaîne comparée");
            nbErreur++;
        }
        if(affichageATester4.equals(chaineAttendue4)){
            System.out.println("Erreur dans la quatrième chaîne comparée");
            nbErreur++;
        }
        
        if(nbErreur == 0){
            System.out.println("TEST : toString VALIDE");
        }

    }

    /**
     * tests sur la méthode modifierModalite de la classe Evaluation.
     */
    public static void testModifierModalite(){
        if(!listeEvaluationValide.get(0).modifierModalite(null,
                "Exam sur machine",100,"Mi septembre")){
            System.out.println("Une erreur s'est produite lors de la modification");
        }
        else if (listeEvaluationValide.get(0) ==listeEvaluationValide.get(1)){
            System.out.println("Erreur de la classe modification");
        }else{
            System.out.println("TEST : modification VALIDE");
        }
    }

    /**
     * tests sur la méthode getNote de la classe Evaluation.
     */
    public static void testGetNote() {
        int nbErreur =0;
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            if (listeEvaluationValide.get(i).getNote() != listeNoteValide.get(i).getNote()) {
                System.out.println("Erreur de la classe getNote pour la valeur " 
                                   + listeEvaluationValide.get(i).getNote());
                nbErreur++;
            }
        }
        if(nbErreur==0){
            System.out.println("Test : GetNote VALIDE");
        }
    }


    /**
     * tests sur la méthode getCoefficient de la classe Evaluation.
     */
    public static void testGetCoefficient() {
        int nbError = 0;
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            if (listeEvaluationValide.get(i).getCoefficient() != listeCoefficientValide.get(i)) {
                System.out.println("Erreur de la classe getCoefficient pour la valeur " 
                                   + listeEvaluationValide.get(i).getCoefficient());
                nbError++;
            }
        }
        if(nbError==0){
            System.out.println("TEST : testGetCoefficient VALIDE");
        }else{
            System.out.println("test invalide nombre d'erreur : " +nbError);
        }
    }

    /**
     * tests sur la méthode isCoefficient pour vérifier la validité des coefficients.
     */
    public static void testIsCoefficient() {
        int nbError =0;
        for (double coefficientATester : listeCoefficientValide) {
            if (!Evaluation.isCoefficient(coefficientATester)) {
                System.out.println("Erreur de la classe isCoefficient pour la valeur " 
                                   + coefficientATester);
                nbError++;
            }
        }
        for (double coefficientNonValideATester : listeCoefficientNonValide) {
            if (Evaluation.isCoefficient(coefficientNonValideATester)) {
                System.out.println("Erreur de la classe isCoefficient pour la valeur " + coefficientNonValideATester);
                nbError++;
            }
        }
        if(nbError==0){
            System.out.println("TEST : isCoefficient VALIDE");
        }else{
            System.out.println("test invalide nombre d'erreur : " +nbError);
        }
    }

    /**
     * Méthode principale (main) où les tests sont exécutés.
     */
    public static void main(String[] args) throws NoteException, EvaluationException {
        batterieDeTest();
        testAjouterNote();
        testToString();
        testGetNote();
        testIsCoefficient();
        testGetCoefficient();
        testModifierModalite();
    }
    
}

