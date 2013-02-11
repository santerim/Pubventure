
package Pubventure.ihmiset;

import Pubventure.ihmiset.Inehmo;
import Pubventure.Sijainti;
import org.junit.*;
import static org.junit.Assert.*;


public class InehmoTest {
    
    private Inehmo inehmo;
    
    public InehmoTest() {
        this.inehmo = new Inehmo(new Sijainti(2, 3), "@", "S", true, 75);
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void onkoLuodullaHahmollaOikeaSijainti() {
        assertEquals(inehmo.getSijainti().getX(), 2);
        assertEquals(inehmo.getSijainti().getY(), 3);
    }
    
    @Test
    public void onkoLuodullaHahmollaOikeaUlkonako() {
        assertEquals(inehmo.getUlkomuoto(), "@");
    }
    
    @Test
    public void onkoLuodunHahmonTyyppiOikea() {
        assertEquals(inehmo.getTyyppi(), "S");
    }
    
    @Test
    public void onkoLuodunHahmonLiikkuvuusparametriOikein() {
        assertEquals(inehmo.getLiikkuvuus(), true);
    }
    
    @Test
    public void onkoAsenneAsetettuOikein() {
        assertEquals(inehmo.getAsenne(), 75);
    }
}
