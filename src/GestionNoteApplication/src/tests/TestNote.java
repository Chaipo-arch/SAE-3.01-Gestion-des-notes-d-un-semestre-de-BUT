
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.util.ArrayList;

/**
 *
 * @author enzo.cluzel
 */
public class TestNote{
    
    public static ArrayList<Double> noteValide= new ArrayList<>();
    public static  ArrayList<Double> noteInvalide = new ArrayList<>();
    public static ArrayList<Note> note = new ArrayList<>();
    
    public static void jeuxDeDonnees() throws NoteException{
        noteInvalide.add(Double.NaN);
        noteInvalide.add(-1.0);
        noteInvalide.add(20.00000000000001);
        noteValide.add(0.0);
        noteValide.add(20.0000000);
        noteValide.add(5.0);
        
        note.add(new Note(4));
        note.add(new Note(0));
        note.add(new Note(8));
         note.add(new Note(13));
    }
    public static void testNoteIsValide(){
        int nombreErreur = 0;
        System.out.println("## TestNoteIsValide ## \n ###########################\n tests des cas Valide");
        for (Double double1 : noteValide) {
            System.out.print(double1);
            if(Note.isNote(double1)){
                System.out.println(": cas valide");
            }else{
                System.out.println(": cas invalide");
                nombreErreur++;
            }
        }
        for (Double double1 : noteInvalide) {
            System.out.print(double1);
            if(!Note.isNote(double1)){
                System.out.println(": cas valide");
            }else{
                System.out.println(": cas invalide");
                nombreErreur++;
            }
        }
        System.out.println("le nombre d'erreur est de : "+nombreErreur);
    }
    
    public static void NoteIsValide(){
        int nombreErreur = 0;
        for (Double double1 : noteValide) {
            try{
                new Note(double1);
                System.out.println(double1+" : cas valide");
            }catch(NoteException e){
             System.out.println(double1+" : cas invalide");
                nombreErreur++;
            }
        }
        for (Double double1 : noteInvalide) {
            try{
                new Note(double1);
                System.out.println(double1+" : cas invalide");
                nombreErreur++;
            }catch(NoteException e){
                System.out.println(double1+" : cas valide");
            }
        }
        System.out.println("le nombre d'erreur est de : "+nombreErreur);
    }
    
    public static void setNoteIsValide() throws NoteException{
        int nombreErreur = 0;
        for(int i = 0 ; i<noteValide.size();i++){
            note.get(i).setNote(noteValide.get(i));
            System.out.print(note.get(i).getNote() + " : doit être modifié par  " + noteValide.get(i)+ " résultat : ");
            if(note.get(i).getNote() == noteValide.get(i)){
                System.out.println("  cas valide");
            }else{
                System.out.println("  cas invalide");
                nombreErreur++;
            }
        }
        
    }
    public static void main(String[] args)throws NoteException{
        jeuxDeDonnees();
        setNoteIsValide();
    }
    
}