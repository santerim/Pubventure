
package pubventure.reittialgot;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
    
    Pubi pubi;
    Random arpoja;
    int leveys;
    int korkeus;
    Pubiobjekti lahto;
    Pubiobjekti maali;
    
    public AstarTest() {
        // luodaan pubi, jossa ei ole asiakkaita
        pubi = new Pubi(0, false);
        
        leveys = pubi.getLeveys();
        korkeus = pubi.getKorkeus();
        arpoja = new Random();
        
        // etsitään satunnaiset pubiobjektit lähdöksi ja maaliksi sillä
        // edellytyksellä, että ne eivät ole esteitä (seiniä tms) ja että
        // ei käsitellä kahta samaa objektia
        int sopivia = 0;
        
        while (true) {
            if (sopivia == 2) {
                break;
            }
            
            int lev = arpoja.nextInt(leveys);
            int kor = arpoja.nextInt(korkeus);
            Pubiobjekti kokeiltava = pubi.getObjekti(kor, lev);
            
            if (kokeiltava.getEste() == false) {
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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testEtsiReitti() {
        
    }
    
    @Test
    public void lasketaanko_F_Oikein() {
        Pubiobjekti po = new Pubiobjekti();
    }
    
    @Test
    public void lasketaanko_G_Oikein() {
        
    }
    
    @Test
    public void lasketaanko_H_Oikein() {
        
    }
}
