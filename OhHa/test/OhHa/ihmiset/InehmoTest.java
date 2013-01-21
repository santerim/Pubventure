
package OhHa.ihmiset;

import org.junit.*;
import static org.junit.Assert.*;


public class InehmoTest {
    
    private Inehmo inehmo;
    
    public InehmoTest() {
        this.inehmo = new Inehmo(2, 3, "@", "S", true);
    }

    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void onkoLuodullaHahmollaOikeaSijainti() {
        assertEquals(inehmo.x, 2);
        assertEquals(inehmo.y, 3);
    }
    
    @Test
    public void onkoLuodullaHahmollaOikeaUlkonako() {
        assertEquals(inehmo.ulkomuoto, "@");
    }
    
    @Test
    public void onkoLuodunHahmonTyyppiOikea() {
        assertEquals(inehmo.tyyppi, "S");
    }
    
    @Test
    public void onkoLuodunHahmonLiikkuvuusparametriOikein() {
        assertEquals(inehmo.liikkuvuus, true);
    }
    
    //Tässä tarkoituksella kaikki hyvän tavan vastaisesti. Koska olen turtana.
    @Test
    public void toimivatkoSetterit() {
        inehmo.setSijainti(5, 5);
        inehmo.setLiikkuva(false);
        inehmo.setUlkomuoto("8");
        inehmo.setTyyppi("A");
        
        assertEquals(inehmo.x, 5);
        assertEquals(inehmo.y, 5);
        assertEquals(inehmo.liikkuvuus, false);
        assertEquals(inehmo.ulkomuoto, "8");
        assertEquals(inehmo.tyyppi, "A");
    }
}
