
package OhHa;

import OhHa.ymparisto.Pubi;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static File tiedosto = new File("pub.txt");
    private static FileReader tLukija;

    
    public static void main(String[] args) {
        try {
            tLukija = new FileReader(tiedosto);
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedostoa ei löydy.");;
        }
        
        Pubi pubi = new Pubi(15, 15);
        new Logiikka(pubi, 5, 999, true).run();
    }
}


//avoimia kysymyksiä:
//
// -kannattaako paikkatietoja seurata pubi-luokassa, vai niin että jokainen
// inehmo-olio pitää kirjaa omasta sijainnistaan?
//
