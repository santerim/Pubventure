
package OhHa;

import OhHa.ymparisto.Pubi;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LogiikkaTest {
    
    Logiikka log = new Logiikka(5, 999, true);
    
    public LogiikkaTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testRun() {
    }

    @Test
    public void testKasitteleSyote() {
        
    }

    @Test
    public void testLuoOlennot() {
    }

    @Test
    public void testSiirraSankaria() {
    }

    @Test
    public void testTulostaOlennot() {
    }

    @Test
    public void testTulostaLuola() {
    }

    @Test
    public void testPiirraRivi() {
    }

    @Test
    public void testArvoX() {
    }

    @Test
    public void testArvoY() {
    }

    @Test
    public void testOsuiko() {
    }

    @Test
    public void testTormaako() {
    }

    @Test
    public void testLiikutaAsiakkaita() {
        int inehmonX = log.getInehmo(1).x;
        int inehmonY = log.getInehmo(1).y;
//        System.out.println("Inehmon X: " + inehmonX);
//        System.out.println("Inehmon Y: " + inehmonY);
        log.liikutaAsiakkaita();
        assertTrue(inehmonX != log.getInehmo(1).x || inehmonY != log.getInehmo(1).y);
    }

    @Test
    public void testArvoLiikesuunta() {
        String suunta = "";
        assertTrue(!log.arvoLiikesuunta().isEmpty());
    }

    @Test
    public void testGetPubi() {
        Pubi pupi;
        pupi = log.getPubi();
        assertTrue(pupi != null);
    }

    @Test
    public void testGetAsiakkaita() {
        assertEquals(log.getAsiakkaita(), 5);
    }

    @Test
    public void testGetSiirtoja() {
        assertTrue(log.getSiirtoja() == 999);
    }

    @Test
    public void testGetAsiakkaatLiikkuvat() {
    }

    @Test
    public void testSetAsiakkaita() {
    }

    @Test
    public void testSetSiirtoja() {
    }

    @Test
    public void testSetAsiakkaatLiikkuvat() {
    }
}
