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

}
