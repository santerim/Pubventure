
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
    

}
