
package pubventure.reittialgot;

import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pubventure.enumit.PubiobjektiEnum;
import pubventure.ihmiset.Inehmo;
import pubventure.ymparisto.Pubi;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Koska nykyisellään tarkoitus on rakentaa tämä peli vain yhden staattisen
 * kentän varaan, nämä testit nojaavat pubventure.resurssit-paketista
 * löytyvään pubi.txt-tiedostoon.
 * 
 * Tässä on kovakoodauksen makua, mutta en oikein näe miten ilman turhan
 * suurta vaivaa voisin testata joitain metodeja muulla tavoin.
 */
public class AstarTest {
    
    private Pubi pubi;
    private Random arpoja;
    private int leveys;
    private int korkeus;
    private Pubiobjekti lahto;
    private Pubiobjekti maali;
    private Astar astar;
    private Pubiobjekti[] reitti;
    
    public AstarTest() {
        // luodaan pubi, jossa ei ole asiakkaita
        pubi = new Pubi(0, new ArrayList<Inehmo>());
        
        // luodaan olio testattavasta luokasta
        this.astar = new Astar(pubi);
        
        leveys = pubi.getLeveys();
        korkeus = pubi.getKorkeus();
        arpoja = new Random();
        
        
    }
    
    /**
     * etsitään satunnaiset pubiobjektit lähdöksi ja maaliksi sillä
     * edellytyksellä, että ne eivät ole esteitä (seiniä tms) ja että
     * ei käsitellä kahta samaa objektia
     */
    private void arvoLahtoJaMaali() {
        int sopivia = 0;
        
        while (true) {
            if (sopivia == 2) {
                break;
            }
            
            int lev = arpoja.nextInt(leveys);
            int kor = arpoja.nextInt(korkeus);
            Pubiobjekti kokeiltava = pubi.getObjekti(kor, lev);
            
            if (kokeiltava.getEste() == false 
                    && kokeiltava.getTyyppi() != PubiobjektiEnum.TALUE) {
                if (sopivia == 0) {
                    lahto = kokeiltava;
                    sopivia++;
                } else if (sopivia == 1 && !lahto.equals(kokeiltava)) {
                    maali = kokeiltava;
                    sopivia++;
                }
            }
        }
    }
    
    /**
     * debuggausmetodi
     */
    private void tulostaReitinSolmujenSijainnit() {
        for (int i = 0; i < reitti.length; i++) {
            if (reitti[i] != null) {
                System.out.println(i + ": (" + reitti[i].getSijainti().getX() + ", " + reitti[i].getSijainti().getY() + ")");
            } else {
                break;
            }
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        arvoLahtoJaMaali();
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void toimiikoReitinetsinta() {
        astar.etsiReitti(lahto, maali);
        reitti = astar.getReitti();
        
        System.out.println("Lähtö: (" + lahto.getSijainti().getX() + ", " + lahto.getSijainti().getY() + ")");
        System.out.println("Maali: (" + maali.getSijainti().getX() + ", " + maali.getSijainti().getY() + ")");
        tulostaReitinSolmujenSijainnit();
        
        assertTrue("Reitistä löytyi testatessa null-arvo", reitti[0] != null);
        if (reitti.length > 1) {
            assertTrue("Reitistä löytyi testatessa null-arvo", reitti[1] != null);
        }
    }
    
    @Test
    public void suorituskyky() {
        
    }
    
    @Test
    public void lasketaanko_F_Oikein() {
        
    }
    
    @Test
    public void lasketaanko_G_Oikein() {
        
    }
    
    @Test
    public void lasketaanko_H_Oikein() {
        
    }
}
