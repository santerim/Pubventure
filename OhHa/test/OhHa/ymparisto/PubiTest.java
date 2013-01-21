/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OhHa.ymparisto;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Santeri
 */
public class PubiTest {

    private Pubi pubi;
    
    public PubiTest() {
        pubi = new Pubi(20, 20, 5, 100, true);
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

    @Test
    public void asiakkaidenMaaraOikein() {
        assertEquals(pubi.getAsiakkaita(), 5);
    }
    
    @Test
    public void asiakkaidenLiikkuvuusOikein() {
        assertTrue(pubi.getAsiakkaatLiikkuvat());
    }
}
