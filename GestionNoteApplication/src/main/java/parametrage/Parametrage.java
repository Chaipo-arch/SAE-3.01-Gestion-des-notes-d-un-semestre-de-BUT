/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;

import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui permet de faire toutes les fonctionnalités des parametrages :
 * Importation des données dans l'application
 * Créer le csv avec les données de l'application
 * @author enzo.cluzel
 */
public abstract class Parametrage {
   
    /** file à utilisée */
    protected File file;
    
    /**
     * Instancie un parametrage
     * @param chemin du fichier
     * @throws IllegalArgumentException Si le fichier n'existe pas
     */
    public Parametrage(File chemin) throws IllegalArgumentException {;
        if (chemin.exists()) {
            file = chemin; 
        } else {
            throw new IllegalArgumentException("Fichier non existant");
        }
    }
    
    /** 
     * Lis le fichier, et créer des données dans l'application selon les données du fichiers
     * Ces données créées sont des instances de Evaluation, Ressource et compétence
     * @throws MauvaisFormatFichierException si des données dans le ficher sont incorrectes
     */
    public abstract void parse() throws IOException, MauvaisFormatFichierException, EvaluationException, NoteException;

    
}