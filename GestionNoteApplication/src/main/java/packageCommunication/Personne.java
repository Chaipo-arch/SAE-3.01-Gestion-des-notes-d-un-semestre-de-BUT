/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

/**
 *
 * @author robin.britelle
 */
import java.io.Serializable;

// Une classe représentant une personne
public class Personne implements Serializable {
    private String nom;
    private int age;
    private String adresse;

    // Constructeur
    public Personne(String nom, int age, String adresse) {
        this.nom = nom;
        this.age = age;
        this.adresse = adresse;
    }

    // Getters et setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", age=" + age +
                ", adresse='" + adresse + '\'' +
                '}';
    }

    public static void main(String[] args) {
        // Création d'une instance de Personne
        Personne personne = new Personne("Alice", 30, "123 Rue de la Java");

        // Affichage des informations de la personne
        System.out.println(personne);
    }
}