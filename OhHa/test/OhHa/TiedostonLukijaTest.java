/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OhHa;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TiedostonLukijaTest {
    
    private TiedostonLukija tlukija = new TiedostonLukija();
    
    public TiedostonLukijaTest() {
        
    }
    
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testaaEttaEnnenTiedostonLukemistaMerkkijonoOnTyhja() {
        String testiMerkkijono = tlukija.getMerkkijono();
        assertTrue(testiMerkkijono.isEmpty());
    }

    @Test
    public void testaaLueTiedosto() {
        String testiMerkkijono = tlukija.lueTiedosto();
        assertTrue(!testiMerkkijono.isEmpty());
    }

    @Test
    public void testaaGetPubinLeveysEnnenTiedostonLukemista() {
        assertEquals(tlukija.getPubinLeveys(), 0);
    }
    
    @Test
    public void testaaGetPubinLeveysTiedostonLukemisenJalkeen() {
        tlukija.lueTiedosto();
        assertTrue(tlukija.getPubinLeveys() > 0);
    }

    @Test
    public void testaaGetPubinKorkeusEnnenTiedostonLukemista() {
        assertEquals(tlukija.getPubinKorkeus(), 0);
    }
    
    @Test
    public void testaaGetPubinKorkeusTiedostonLukemisenJalkeen() {
        tlukija.lueTiedosto();
        assertTrue(tlukija.getPubinKorkeus() > 0);
    }
}
