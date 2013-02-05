
package OhHa.ymparisto;

import OhHa.ihmiset.Inehmo;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.*;

/**
 *
 * @author Santeri
 */
public class PubiTest {

    private Pubi pubi;
//    private TiedostonLukija tl;
    
    public PubiTest() {
        pubi = new Pubi(5);
//        tl = new TiedostonLukija();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void saakoPubiOikeanKoon() {
        assertEquals(pubi.getKorkeus(), 13);
        assertEquals(pubi.getLeveys(), 26);
    }
    
    @Test
    public void onnistuukoKentanLuominen() {
        Pubiobjekti[][] kentta = pubi.luoKentta();
        Pubiobjekti po = kentta[0][0];
        assertTrue(po != null);
    }
    
    @Test
    public void onnistuukoOlentojenLuominen() {
        pubi.luoKentta();
        pubi.luoOlennot();
        ArrayList<Inehmo> lista = pubi.getInehmot();
        assertTrue(!lista.isEmpty());
    }
}
