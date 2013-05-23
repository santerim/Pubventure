/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubventure.reittialgot;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pubventure.Sijainti;
import pubventure.enumit.PubiobjektiEnum;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 */
public class EtaisyysComparatorTest {
    
        EtaisyysComparator komparaattori = new EtaisyysComparator();
        
        Sijainti sijainti = new Sijainti(1, 2);
        
        Pubiobjekti ekaPO = new Pubiobjekti("Ä", true, PubiobjektiEnum.JUKEBOKSI, "Jukeboksi", sijainti, 1000);
        Pubiobjekti tokaPO = new Pubiobjekti("Ö", true, PubiobjektiEnum.LAVUAARI, "Lavuaari", sijainti, 1000);
        
    public EtaisyysComparatorTest() {
    }
    
    @Before
    public void setUp() {
        ekaPO.setF(1);
        tokaPO.setF(2);
    }

    /**
     * Test of compare method, of class EtaisyysComparator.
     */
    @Test
    public void antaakoCompareMetodiOikeanTuloksen() {
        assertTrue("Virhe kun ensimmäisen verrattavan f-arvo on pienempi", komparaattori.compare(ekaPO, tokaPO) == -1);
        // asetetaan f-arvot toisinpäin
        ekaPO.setF(2);
        tokaPO.setF(1);
        assertTrue("Virhe kun toisen verrattavan f-arvo on pienempi", komparaattori.compare(ekaPO, tokaPO) == 1);
        // f-arvot samat
        tokaPO.setF(2);
        assertTrue("Virhe kun verrattavilla on sama f-arvo", komparaattori.compare(ekaPO, tokaPO) == 0);
    }
}
