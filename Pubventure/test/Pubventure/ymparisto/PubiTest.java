
package Pubventure.ymparisto;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;
import Pubventure.ymparisto.Pubiobjekti;
import Pubventure.ymparisto.Pubi;
import Pubventure.ihmiset.Inehmo;
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
        pubi.luoKentta();
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
        pubi.luoHahmot();
        ArrayList<Inehmo> lista = pubi.getInehmot();
        assertTrue(!lista.isEmpty());
    }
    
    @Test
    public void toimiikoLuoInehmoMetodi() {
        Inehmo testiInehmo = pubi.luoInehmo(InehmoEnum.ASIAKAS, null);
        assertTrue(testiInehmo != null);
    }
    
    @Test
    public void toimiikoTormaaMetodiOikein() {
        assertTrue(pubi.tormaako(new Sijainti(0, 0)));
        assertEquals(pubi.tormaako(new Sijainti(2, 3)), false);
    }
    
    @Test
    public void loytyvatkoPubinMittasuhteetOikein() {
        assertEquals(pubi.getKorkeus(), 19);
        assertEquals(pubi.getLeveys(), 38);
    }
}
