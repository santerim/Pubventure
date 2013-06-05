
package pubventure.reittialgot;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pubventure.Sijainti;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Minimikeon jarejesta-metodia ei testata suoraan, vaan se tapahtuu
 * käytännössä annaJaPoistaPienin-metodin kautta.
 * 
 * HUOM! Tässä luokassa on vahvaa kovakoodauksen makua. Teen asialle jotain
 * kunhan löydän tarpeeksi aikaa.
 */
public class MinimikekoTest {
    
    /**
     * Testauksessa käytettävät objektit.
     */
    private Minimikeko mk;
    private Pubiobjekti p1;
    private Pubiobjekti p2;
    private Pubiobjekti p3;
    private Pubiobjekti p4;
    private Pubiobjekti p5;
    private Pubiobjekti p6;
    private Pubiobjekti p7;
    private Pubiobjekti p8;
    
    /**
     * Konstruktorissa objektit instantioidaan(?) ja niille asetetaan
     * f-arvot testejä varten. Koska pubiobjekteja verrataan keskenään näiden
     * sijaintien perusteella, annetaan näille myös Sijainnit.
     * @see Astar
     * @see Pubiobjekti
     * @see Sijainti
     */
    public MinimikekoTest() {
        p1 = new Pubiobjekti();
        p2 = new Pubiobjekti();
        p3 = new Pubiobjekti();
        p4 = new Pubiobjekti();
        p5 = new Pubiobjekti();
        p6 = new Pubiobjekti();
        p7 = new Pubiobjekti();
        p8 = new Pubiobjekti();
        
        p1.setF(8);
        p2.setF(7);
        p3.setF(6);
        p4.setF(5);
        p5.setF(4);
        p6.setF(3);
        p7.setF(2);
        p8.setF(1);
        
        p1.setSijainti(new Sijainti(1, 1));
        p2.setSijainti(new Sijainti(2, 2));
        p3.setSijainti(new Sijainti(3, 3));
        p4.setSijainti(new Sijainti(4, 4));
        p5.setSijainti(new Sijainti(5, 5));
        p6.setSijainti(new Sijainti(6, 6));
        p7.setSijainti(new Sijainti(7, 7));
        p8.setSijainti(new Sijainti(8, 8));
    }
    
    /**
     * Turhaa toistoa välttääksemme määrittelemme tässä, että ennen jokaista
     * testiä alustetaan uusi keko, johon lisätään konstruktorissa
     * valmistellut pubiobjektit.
     */
    @Before
    public void setUp() {
        mk = new Minimikeko(10);
        
        /* keossa pitäisi allaolevien lisäyksien jälkeen olla:
         * indeksi      0 1 2 3 4 5 6 7
         * luvut        1 2 6 3 4 7 8 5
         *
         *           1
         *         /   \
         *        2     6
         *       / \   /  \
         *      3  4  7    8
         *     /
         *    5
         * 
         */
        
        mk.lisaaKekoon(p7);
        mk.lisaaKekoon(p6);
        mk.lisaaKekoon(p3);
        mk.lisaaKekoon(p4);
        mk.lisaaKekoon(p5);
        mk.lisaaKekoon(p2);
        mk.lisaaKekoon(p1);
        mk.lisaaKekoon(p8);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Kokeillaan lisätä kekoon yksi objekti sarjan suurimmalla arvolla, ja
     * testataan josko solmujen lukumäärä on muuttunut oikein, sekä keon nyt
     * viimeisen solmun f-arvon oikeellisuus. Kyllä, tiedän että jokaisessa
     * testimetodissa pitäisi testata VAIN YHTÄ asiaa. Minulla on liikaa töitä
     * ja liian vähän kaljaa.
     */
    @Test
    public void lisaaKekoonYksiObjekti() {
        int kokoAlussa = mk.getSolmujenLKM();
        mk.lisaaKekoon(p1);
        assertEquals(8, mk.getKeko()[mk.getSolmujenLKM() - 1].getF());
        assertEquals(kokoAlussa + 1, mk.getSolmujenLKM());
    }
    
    /**
     * Testataan josko objektit ovat siinä järjestyksessä, kuin niiden pitäisi
     * olla.
     */
    @Test
    public void onkoJarjestysOikein() {
        assertEquals(1, mk.getKeko()[0].getF());
        assertEquals(2, mk.getKeko()[1].getF());
        assertEquals(6, mk.getKeko()[2].getF());
        assertEquals(3, mk.getKeko()[3].getF());
        assertEquals(4, mk.getKeko()[4].getF());
        assertEquals(7, mk.getKeko()[5].getF());
        assertEquals(8, mk.getKeko()[6].getF());
        assertEquals(5, mk.getKeko()[7].getF());
    }

    /**
     * Kokeillaan saadaanko keosta tosiaan pienimmän arvon omaava objekti.
     */
    @Test
    public void testAnnaPienin() {
        Pubiobjekti pienin = mk.annaPienin();
        assertEquals("Metodin palauttama pubiobjekti ei ollut pienin f-arvoltaan!", 1, pienin.getF());
    }

    /**
     * Poistetaan keosta päällimmäinen ja kokeillaan onko uusi päällimmäinen
     * objekti oikealla paikallaan.
     */
    @Test
    public void testAnnaJaPoistaPienin() {
        Pubiobjekti pienin = mk.annaJaPoistaPienin();
        
        assertEquals("Keko antoi väärän pubiobjektin!", 1, pienin.getF());
        assertEquals("Poiston jälkeen keossa on väärä pubiobjekti päällimmäisenä!", 2, mk.getKeko()[0].getF());
    }
    
    /**
     * Käydään läpi kaikki keossa olevat objektit, joilla on vanhempi, ja
     * tarkistetaan että ne ovat oikein.
     */
    @Test
    public void loytyykoVanhempiOikein() {
//        tulostaKeko();
        
        // keossa pitäisi siis olla:
        // indeksi      0 1 2 3 4 5 6 7
        // luvut        1 2 6 3 4 7 8 5

        assertEquals(1, mk.getKeko()[mk.vanhempi(1)].getF());
        assertEquals(1, mk.getKeko()[mk.vanhempi(2)].getF());
        assertEquals(2, mk.getKeko()[mk.vanhempi(3)].getF());
        assertEquals(2, mk.getKeko()[mk.vanhempi(4)].getF());
        assertEquals(6, mk.getKeko()[mk.vanhempi(5)].getF());
        assertEquals(6, mk.getKeko()[mk.vanhempi(6)].getF());
        assertEquals(3, mk.getKeko()[mk.vanhempi(7)].getF());
    }
    
    /**
     * Vasemman lapsen testaus missä mahdollista.
     */
    @Test
    public void loytyykoVasenLapsiOikein() {
        assertEquals(2, mk.getKeko()[mk.vasenLapsi(0)].getF());
        assertEquals(3, mk.getKeko()[mk.vasenLapsi(1)].getF());
        assertEquals(7, mk.getKeko()[mk.vasenLapsi(2)].getF());
        assertEquals(5, mk.getKeko()[mk.vasenLapsi(3)].getF());
    }
    
    /**
     * Oikean lapsen testaus missä mahdollista.
     */
    @Test
    public void loytyykoOikeaLapsiOikein() {
        assertEquals(6, mk.getKeko()[mk.oikeaLapsi(0)].getF());
        assertEquals(4, mk.getKeko()[mk.oikeaLapsi(1)].getF());
        assertEquals(8, mk.getKeko()[mk.oikeaLapsi(2)].getF());
    }
    
    /**
     * Kokeillaan, että objekti löydetään keosta.
     */
    @Test
    public void loytyykoObjektiKeosta() {
        Pubiobjekti p100 = new Pubiobjekti();
        p100.setSijainti(new Sijainti(15, 15));
        assertTrue(mk.onkoKeossa(p100) == false);
        assertTrue(mk.onkoKeossa(p1));
    }
    
    /**
     * Kahden objektin paikkojen vaihtaminen keskenään keossa.
     */
    @Test
    public void toimiikoObjektienVaihtaminenPaikseen() {
        int ekanF = mk.getKeko()[0].getF();
        int tokanF = mk.getKeko()[1].getF();
        mk.vaihdaPaikseen(0, 1);
        assertEquals(ekanF, mk.getKeko()[1].getF());
        assertEquals(tokanF, mk.getKeko()[0].getF());
    }
    
    /**
     * Tämä metodi on olemassa vain debuggausta varten. Tulostaa keossa olevien
     * pubiobjektien f-arvot riviin pilkuilla erotettuina.
     */
    private void tulostaKeko() {
        System.out.println("\nLuvut keossa");
        for (int i = 0; i < mk.getSolmujenLKM(); i++) {
            System.out.print(mk.getKeko()[i].getF());
            if (i < mk.getSolmujenLKM() - 1) {
                System.out.print(", ");
            }
        }
    }
}
