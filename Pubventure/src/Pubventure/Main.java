
package Pubventure;

/**
 * 
 * Luokan tehtävänä on vain toimia koodin suorituksen aloituspisteenä,
 * ja antaa Logiikka-luokalle perusparametrit:
 * Asiakkaiden määrä, siirtojen määrä ja asiakkaiden liikkuvuus.
 */

public class Main {

    public static void main(String[] args) {
        new Logiikka(5, 999, true).aloita();
    }
}


// avoimet kysymykset:
//      - miten txt-tiedostoa tulee käsitellä niin että koodi löytää sen
//      jar-paketin sisältä?

//      - miksi JLabelien värin muuttaminen ei onnistu?

//      - miksi enumien vertailu ei toimi?
