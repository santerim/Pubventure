
package Pubventure.ymparisto;

import Pubventure.enumit.PubiobjektiEnum;
import Pubventure.ymparisto.Pubiobjekti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PubiobjektiTest {
    
    private Pubiobjekti poyta = new Pubiobjekti("#", true, PubiobjektiEnum.POYTA, "pöytä");
    private Pubiobjekti lattia = new Pubiobjekti(".", false, PubiobjektiEnum.LATTIA, "lattia");
    
    public PubiobjektiTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testaaGetUlkonako() {
        assertEquals(poyta.getUlkonako(), "#");
    }

    @Test
    public void testaaGetEste() {
        assertTrue(poyta.getEste());
    }

    @Test
    public void testaaSetUlkonako() {
        poyta.setUlkonako("Y");
        assertEquals(poyta.getUlkonako(), "Y");
    }

    @Test
    public void testaaSetEste() {
        assertEquals(poyta.getEste(), true);
        assertEquals(lattia.getEste(), false);
    }
}
