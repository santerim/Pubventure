
package OhHa.ymparisto;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Sankari;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;

/**
 *
 * @author Santeri
 */
public class PubiTest {

    private Pubi pubi;
    
    public PubiTest() {
        pubi = new Pubi(20, 20);
        
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void saakoPubiOikeanKoon() {
        assertEquals(pubi.getKorkeus(), 20);
        assertEquals(pubi.getLeveys(), 20);
    }
}
