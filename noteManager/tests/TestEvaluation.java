/*
 * Ce commentaire en haut du fichier indique des informations sur le fichier, telles que la licence et le modèle.
 * Vous pouvez ajouter ici des informations sur la licence et la description du fichier.
 */
package noteManager.tests;

import noteManager.main.Evaluation;
import noteManager.main.Note;
import noteManager.main.NoteException;
import noteManager.main.EvaluationException;
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
        listeCoefficientNonValide.add(null);
        listeCoefficientNonValide.add(-1.0);
        listeCoefficientNonValide.add(-9456.4898);
        listeCoefficientNonValide.add(100.0001);

        // Initialisation de listes d'évaluations valides avec des notes, des coefficients et des modalités
        listeEvaluationValide.add(new Evaluation("Maths",listeNoteValide.get(0)
                ,"QCM",listeCoefficientValide.get(0),"25/01/2023"));
        listeEvaluationValide.add(new Evaluation("Développement Web",listeNoteValide.get(1)
                ,"Exam sur machine",listeCoefficientValide.get(1),"Mi septembre"));
        listeEvaluationValide.add(new Evaluation("Sql",listeNoteValide.get(2)
                ,"Ecrit",listeCoefficientValide.get(2),""));
        listeEvaluationValide.add(new Evaluation("Anglais",listeNoteValide.get(3)
                ,"Relevés TPs",listeCoefficientValide.get(3),"25/12/2024"));

        // Initialisation de listes d'évaluations sans note avec des modalités
        listeEvaluationSansNote.add(new Evaluation("Cryptographie",null
                ,"QCM",1,"25/01/2023"));
        listeEvaluationSansNote.add(new Evaluation("Economie",null
                ,"Exam sur machine",100,"Mi septembre"));
        listeEvaluationSansNote.add(new Evaluation("Réseaux",null
                ,"Ecrit",20,""));
        listeEvaluationSansNote.add(new Evaluation("Programmation",null
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
        for(Evaluation evaluationTeste : listeEvaluationValide){
            if (evaluationTeste.ajouterNote(listeNoteValide.get(index))){
                nbTestNOk++;
            }
            index++;
        }
        if(nbTestNOk != 0){
            System.out.println("Il y a " + nbTestNOk + " erreur(s)");
        }
    }

    /**
     * tests sur la méthode toString de la classe Evaluation.
     */
    public static void testToSring(){

        String affichageATester = listeEvaluationValide.get(0).toSring();
        String chaineAttendue = "Maths QCM 25/01/2023 1.0 0.5";
        String affichageATester2 = listeEvaluationValide.get(1).toSring();
        String chaineAttendue2 = "Développement Web Exam sur machine Mi septembre 100.0 10.524";
        String affichageATester3 = listeEvaluationValide.get(3).toSring();
        String chaineAttendue3 = "Anglais Relevés TPs 25/12/2024 50.5 12.256";
        String affichageATester4 = listeEvaluationSansNote.get(0).toSring();
        String chaineAttendue4 = "Cryptographie QCM 25/01/2023 1.0 note non renseignée";

        if(affichageATester.compareTo(chaineAttendue) != 0){
            System.out.println("Erreur dans la première chaîne comparée");
        }
        if(affichageATester2.compareTo(chaineAttendue2) != 0){
            System.out.println("Erreur dans la deuxième chaîne comparée");
        }
        if(affichageATester3.compareTo(chaineAttendue3) != 0){
            System.out.println("Erreur dans la troisième chaîne comparée");
        }
        if(affichageATester4.compareTo(chaineAttendue4) != 0){
            System.out.println("Erreur dans la quatrième chaîne comparée");
        }

    }

    /**
     * tests sur la méthode modifierModalite de la classe Evaluation.
     */
    public static void testModifierModalite(){
        if(!listeEvaluationValide.get(0).modifierModalite("Economie",null,
                "Exam sur machine",100,"Mi septembre")){
            System.out.println("Une erreur s'est produite lors de la modification");
        }
        if (listeEvaluationValide.get(0).compareTo(listeEvaluationValide.get(1))){
            System.out.println("Erreur de la classe modification");
        }
    }

    /**
     * tests sur la méthode getNote de la classe Evaluation.
     */
    public static void testGetNote() {
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            if (listeEvaluationValide.get(i).getNote() != listeNoteValide.get(i).getNote()) {
                System.out.println("Erreur de la classe getNote pour la valeur " 
                                + listeEvaluationValide.get(i).getNote());
            }
        }
    }


    /**
     * tests sur la méthode getCoefficient de la classe Evaluation.
     */
    public static void testGetCoefficient() {
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            if (listeEvaluationValide.get(i).getCoefficient() != listeCoefficientValide.get(i)) {
                System.out.println("Erreur de la classe getCoefficient pour la valeur " + listeEvaluationValide.get(i).getCoefficient());
            }
        }
    }

    /**
     * tests sur la méthode isCoefficient pour vérifier la validité des coefficients.
     */
    public static void testIsCoefficient() {
        for (double coefficientATester : listeCoefficientValide) {
            if (!isCoefficient(coefficientATester)) {
                System.out.println("Erreur de la classe isCoefficient pour la valeur " + coefficientATester);
            }
        }
        for (double coefficientNonValideATester : listeCoefficientNonValide) {
            if (isCoefficient(coefficientNonValideATester)) {
                System.out.println("Erreur de la classe isCoefficient pour la valeur " + coefficientNonValideATester);
            }
        }
    }

    /**
     * Méthode principale (main) où les tests sont exécutés.
     */
    public static void main(String[] args) throws NoteException, EvaluationException {
        batterieDeTest();
        testAjouterNote();
        testToSring();
        testModifierModalite();
        testGetNote();
        testGetCoefficient();
        testIsCoefficient();
    }
}
