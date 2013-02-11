
package Pubventure.ymparisto;

import Pubventure.ymparisto.Pubiobjekti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PubiobjektiTest {
    
    private Pubiobjekti po = new Pubiobjekti("X", true);
    
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
        assertEquals(po.getUlkonako(), "X");
    }

    @Test
    public void testaaGetEste() {
        assertTrue(po.getEste());
    }

    @Test
    public void testaaSetUlkonako() {
        po.setUlkonako("Y");
        assertEquals(po.getUlkonako(), "Y");
    }

    @Test
    public void testaaSetEste() {
        po.setEste(false);
        assertEquals(po.getEste(), false);
    }
}
