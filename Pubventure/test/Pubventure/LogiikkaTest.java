
package Pubventure;

import Pubventure.Logiikka;
import Pubventure.ymparisto.Pubi;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogiikkaTest {
    
    Logiikka log = new Logiikka(5, 999, true);
    Pubi pubi = log.getPubi();
    
    public LogiikkaTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // ei vielä toiminnassa - vaatii muutoksia logiikka-luokassa
//    @Test
//    public void testSiirraSankaria() {
//        boolean siirtoOnnistui = false;
//        int i = 1;
//        while (i < 10) {
//            Sijainti vanhaSijainti = pubi.getInehmot().get(0).getSijainti();
//            log.kasitteleLiikekomento("s");
//            Sijainti uusiSijainti = pubi.getInehmot().get(0).getSijainti();
//            if (uusiSijainti.getY()-vanhaSijainti.getY() == 1) {
//                siirtoOnnistui = true;
//                break;
//            } else {
//                i++;
//            }
//        }
//        assertTrue(siirtoOnnistui);
//    }

    // nämä neljä testiä vanhentuneita - ainakin tällä hetkellä
//    @Test
//    public void testTulostaOlennot() {
//    }
//
//    @Test
//    public void testTulostaLuola() {
//    }
//
//    @Test
//    public void testPiirraRivi() {
//    }
//
//
//    @Test
//    public void testOsuiko() {
//    }

    @Test
    public void testTormaako() {
        Logiikka log2 = new Logiikka(5, 999, false);
        log2.getInehmo(2).getSijainti().setX(log2.getInehmo(0).getSijainti().getX());
    }

    @Test
    public void testLiikutaAsiakkaita() {
        int inehmonX = log.getInehmo(2).getSijainti().getX();
        int inehmonY = log.getInehmo(2).getSijainti().getY();
//        System.out.println("Inehmon X: " + inehmonX);
//        System.out.println("Inehmon Y: " + inehmonY);
        log.liikutaAsiakkaita();
        assertTrue(inehmonX != log.getInehmo(2).getSijainti().getX() || inehmonY != log.getInehmo(2).getSijainti().getY());
    }

    @Test
    public void testArvoLiikesuunta() {
        String suunta = log.arvoLiikesuunta();
        assertTrue(!suunta.isEmpty());
    }




}
