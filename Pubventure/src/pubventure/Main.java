
package pubventure;

/**
 * 
 * Luokan tehtävänä on vain toimia koodin suorituksen aloituspisteenä,
 * ja antaa Logiikka-luokalle perusparametrit:
 * Asiakkaiden määrä, siirtojen määrä ja asiakkaiden liikkuvuus.
 */

public class Main {

    // siirtomäärällä ei nykyisellään merkitystä
    public static void main(String[] args) {
//        System.out.println(System.getProperty("java.classpath"));
        Logiikka log = new Logiikka(1, 999, true, true);
        log.aloita();
    }
}
