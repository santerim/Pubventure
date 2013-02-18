
package Pubventure;

/**
 * 
 * Luokan tehtävänä on vain toimia koodin suorituksen aloituspisteenä,
 * ja antaa Logiikka-luokalle perusparametrit:
 * Asiakkaiden määrä, siirtojen määrä ja asiakkaiden liikkuvuus.
 */

public class Main {

    // siirtomäärällä ei nykyisellään merkitystä
    public static void main(String[] args) {
        new Logiikka(20, 999, true).aloita();
    }
}
