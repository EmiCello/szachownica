/*
    Napisz program: podajemy mu plik tekstowy, w którym jest lista ruchów w grze w szachy, 
    a on wyświetla, jak wygląda plansza po wykonaniu tych ruchów. Sam zdecyduj, w jakim formacie 
    będzie ten plik z listą ruchów - przyjmij taki format, żeby było ci wygodnie. 
    Wybrany format ruchów np.: 2a-4b
*/
package szachownica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class Szachownica {  
    public static void main(String[] args) throws IOException { 
        graj();   
    }
    
    private static String[][] dajSzachownicę(){
        String[][] szachownicaSzablon = new String[8][8];
        for (int i = 0; i < szachownicaSzablon.length; i++) {
            for (int j = 0; j < szachownicaSzablon.length; j++) {
                szachownicaSzablon[i][j] = " ";
            }
        }
        return szachownicaSzablon;
    }
    
    private static String[][] tablicaFigurCzarnych(){
        String[][] figuryCzarne = new String[2][8];
        String figuryCzarnePierwszyRząd = "♜♞♝♛♚♝♞♜";
        for (int i = 0; i < 8; i++) {
            figuryCzarne[0][i] = figuryCzarnePierwszyRząd.substring(i, i + 1);
        }
        String figuryCzarneDrugiRząd = "♟♟♟♟♟♟♟♟";
        for (int i = 0; i < 8; i++) {
            figuryCzarne[1][i] = figuryCzarneDrugiRząd.substring(i, i + 1);
        }
        return figuryCzarne;
    }
    
    private static String[][] tablicaFigurBiałych(){
        String[][] figuryBiałe = new String[2][8];
        String figuryBiałePierwszyRząd = "♙♙♙♙♙♙♙♙";
        for (int i = 0; i < 8; i++) {
            figuryBiałe[0][i] = figuryBiałePierwszyRząd.substring(i, i + 1);
        }
        String figuryBiałeDrugiRząd = "♖♘♗♕♔♗♘♖";
        for (int i = 0; i < 8; i++) {
            figuryBiałe[1][i] = figuryBiałeDrugiRząd.substring(i, i + 1);
        }
        return figuryBiałe;
    }
    
    private static String[][] wypełnijSzachownicęFigurami(String[][] szachownica, String[][] tablicaFiguryCzarne, String[][] tablicaFiguryBiałe){
        String[][] tablicaZFigurami = dajSzachownicę();    
        
        for (int i = 0; i < tablicaZFigurami.length; i++) {
            
                for (int j = 0; j < tablicaZFigurami.length; j++) {
                    if(i < 2){
                        tablicaZFigurami[i][j] = tablicaFiguryCzarne[i][j];
                    }
                    if(i > 5){
                        tablicaZFigurami[i][j] = tablicaFiguryBiałe[i - 6][j];
                    }
                }                        
        }        
        return tablicaZFigurami;
    }
    
    private static void drukujSzachownicę(String[][] szachownicaZFigurami){
        System.out.println("   A   B   C  D   E  F   G   H ");
        System.out.println(" - - - - - - - - - - - - - - ");
        for (int i = 0; i < szachownicaZFigurami.length; i++) {
            for (int j = 0; j < szachownicaZFigurami.length; j++) { 
                    if(j == 0){
                        System.out.print(i + 1 + " ");
                    }
                    System.out.print(" " + szachownicaZFigurami[i][j] + " "); 
            }
            System.out.println('\n' + " - - - - - - - - - - - - - - - ");
        }
        System.out.println("   A   B   C  D   E  F   G   H ");          
    }
        
    private static String[] rodzajeRuchów() throws FileNotFoundException, IOException{
        List<String> lista = new ArrayList<>();         
        File plik = new File("C:\\Users\\Emilia\\Desktop\\ruchySzachownica.txt");
        FileInputStream strumień = new FileInputStream(plik);
        InputStreamReader wczytaj = new InputStreamReader(strumień);
        BufferedReader czytaj = new BufferedReader(wczytaj);
        String linijka;       
        String[] tablica1 = {"1", "2", "3","4","5","6","7","8"};
        String[] tablica2 = {"a", "b", "c","d","e","f","g","h"};
        while ((linijka = czytaj.readLine()) != null){
            StringBuilder line = new StringBuilder(linijka);             
            for (String a : tablica1) {               
               if(a.equalsIgnoreCase(line.substring(0, 1))){                 
                line.delete(0, 1).replace(0, 0, String.valueOf(Integer.parseInt(a) - 1));
               }               
            }
            int x = 0;
            for (String a : tablica2) {                
               if(a.equalsIgnoreCase(line.substring(1,2))){                 
                line.delete(1,2).replace(1, 1, String.valueOf(x));
               }
               x++;
            }            
            for (String a : tablica1) {               
               if(a.equalsIgnoreCase(line.substring(3,4))){                 
                line.delete(3,4).replace(3,3, String.valueOf(Integer.parseInt(a) - 1));
               }              
            }            
            int y = 0;
            for (String a : tablica2) {                
               if(a.equalsIgnoreCase(line.substring(4,5))){                 
                line.delete(4,5).replace(4,4, String.valueOf(y));
               }
               y++;
            }             
            lista.add(line.toString());            
        }
        int długość = lista.size();
        String[] rodzajeRuchów = new String[długość];
        for (int i = 0; i <rodzajeRuchów.length; i++) {
            rodzajeRuchów[i] = lista.get(i);
        }
        return rodzajeRuchów;
    }
    private static String[][] wykonajRuch(String[] ruchyUżytkownika,String[][] szachownicaZFigurami, String[] ruchyPierwszyGracz, String[] ruchyDrugiGracz){
        int a = 0;
        int b = 0;
        for (int i = 0; i < ruchyUżytkownika.length; i++) {
            
            if(i % 2 == 0){
                int numerWierszaSkąd = Integer.parseInt(ruchyPierwszyGracz[a].substring(0, 1));
                int numerKolumnySkąd = Integer.parseInt(ruchyPierwszyGracz[a].substring(1, 2));
                int numerWierszaDokąd = Integer.parseInt(ruchyPierwszyGracz[a].substring(3, 4));
                int numerKolumnyDokąd = Integer.parseInt(ruchyPierwszyGracz[a].substring(4, 5));
                
                if(szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd] != null && szachownicaZFigurami[numerWierszaDokąd][numerKolumnyDokąd] == " "){
                    szachownicaZFigurami[numerWierszaDokąd][numerKolumnyDokąd] = szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd];
                    szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd] = " ";
                    a++;
                }
                else{
                    a++;
                    System.out.println("Gracz 1 nie można wykonać ruchu nr " + a);  
                    System.out.println("");
                }
            }
            else
            {
                int numerWierszaSkąd = Integer.parseInt(ruchyDrugiGracz[b].substring(0, 1));
                int numerKolumnySkąd = Integer.parseInt(ruchyDrugiGracz[b].substring(1, 2));
                int numerWierszaDokąd = Integer.parseInt(ruchyDrugiGracz[b].substring(3, 4));
                int numerKolumnyDokąd = Integer.parseInt(ruchyDrugiGracz[b].substring(4, 5));
                
                if(szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd] != null && szachownicaZFigurami[numerWierszaDokąd][numerKolumnyDokąd] == " "){
                    szachownicaZFigurami[numerWierszaDokąd][numerKolumnyDokąd] = szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd];
                    szachownicaZFigurami[numerWierszaSkąd][numerKolumnySkąd] = " ";
                    b++;
                }
                else{
                    b++;
                    System.out.println("Gracz 2 nie można wykonać ruchu nr " + b);   
                    System.out.println("");
                }
            }            
        }
        return szachownicaZFigurami;
    }   

    
    private static void drukujSzachownicęPoRuchu(String[][] szachownicaPoRuchu){
        System.out.println("   A   B   C  D   E  F   G   H ");
        System.out.println(" - - - - - - - - - - - - - - ");
        for (int i = 0; i < szachownicaPoRuchu.length; i++) {
            for (int j = 0; j < szachownicaPoRuchu.length; j++) { 
                    if(j == 0){
                        System.out.print(i + 1 + " ");
                    }
                    System.out.print(" " + szachownicaPoRuchu[i][j] + " "); 
            }
            System.out.println('\n' + " - - - - - - - - - - - - - - - ");
        }
        System.out.println("   A   B   C  D   E  F   G   H ");          
    }
    
    private static int ileRuchówPierwszyGracz(String[] ruchyUżytkownika){
        int długośćRuchyUżytkownika = ruchyUżytkownika.length;
        int ileRuchówPierwszyGracz = 0;
        if(ruchyUżytkownika.length % 2 == 0){
            ileRuchówPierwszyGracz = długośćRuchyUżytkownika / 2;
        }
        if(ruchyUżytkownika.length % 2 != 0){
            ileRuchówPierwszyGracz = długośćRuchyUżytkownika / 2 + 1;
        }
        return ileRuchówPierwszyGracz;
    }
    
    private static String[] ruchyPierwszyGracz(int ilośćRuchówPierwszyGracz, String[] ruchyUżytkownika){        
        
        String[] ruchyPierwszyGracz = new String[ilośćRuchówPierwszyGracz];  
        int a = 0;
        int b = 0;
        while(b < ruchyUżytkownika.length){
            if(b % 2 == 0){
                ruchyPierwszyGracz[a] = ruchyUżytkownika[b];
                a++;
            }
            b++;
        }     
        return ruchyPierwszyGracz;
    }
    
    private static int ileRuchówDrugiGracz(String[] ruchyUżytkownika){        
        int długośćRuchyUżytkownika = ruchyUżytkownika.length;
        int ileRuchówDrugiGracz = długośćRuchyUżytkownika / 2;        
                    
        return ileRuchówDrugiGracz;
    }
    
    private static String[] ruchyDrugiGracz(int ilośćRuchówDrugiGracz, String[] ruchyUżytkownika){        
        
        String[] ruchyDrugiGracz = new String[ilośćRuchówDrugiGracz];  
        int a = 0;
        int b = 0;
        while(b < ruchyUżytkownika.length){
            if(b % 2 != 0){
                ruchyDrugiGracz[a] = ruchyUżytkownika[b];
                a++;
            }
            b++;
        }     
        return ruchyDrugiGracz;
    }
    private static void graj() throws IOException{                
        String[] ruchyUżytkownika = rodzajeRuchów();   
        int ilośćRuchówPierwszyGracz = ileRuchówPierwszyGracz(ruchyUżytkownika);
        String[] ruchyPierwszyGracz = ruchyPierwszyGracz(ilośćRuchówPierwszyGracz, ruchyUżytkownika);        
        int ilośćRuchówDrugiGracz = ileRuchówDrugiGracz(ruchyUżytkownika);
        String[] ruchyDrugiGracz = ruchyDrugiGracz(ilośćRuchówDrugiGracz, ruchyUżytkownika);        
        String[][] szachownica = dajSzachownicę();
        String[][] tablicaFiguryCzarne = tablicaFigurCzarnych();
        String[][] tablicaFiguryBiałe = tablicaFigurBiałych();   
        String[][] szachownicaZFigurami = wypełnijSzachownicęFigurami(szachownica, tablicaFiguryCzarne, tablicaFiguryBiałe);        
        String[][] szachownicaPoRuchu = wykonajRuch(ruchyUżytkownika, szachownicaZFigurami, ruchyPierwszyGracz, ruchyDrugiGracz);
        drukujSzachownicęPoRuchu(szachownicaPoRuchu);
    }
}
