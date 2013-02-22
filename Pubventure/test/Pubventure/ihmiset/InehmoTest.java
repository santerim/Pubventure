package Pubventure.ihmiset;

import Pubventure.ihmiset.Inehmo;
import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;
import org.junit.*;
import static org.junit.Assert.*;

public class InehmoTest {

    private Inehmo inehmo;

    public InehmoTest() {
        
    }

    @Before
    public void setUp() {
        this.inehmo = new Inehmo(new Sijainti(2, 3), "@", InehmoEnum.SANKARI, true, InehmoEnum.MIES);
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
        assertEquals(inehmo.getTyyppi(), InehmoEnum.SANKARI);
    }

    @Test
    public void onkoLuodunHahmonLiikkuvuusparametriOikein() {
        assertEquals(inehmo.getLiikkuvuus(), true);
    }
    
    @Test
    public void toimiikoAsenteenMuutosOikein() {
        inehmo.setAsenne(-150);
        assertTrue(inehmo.getAsenne() == 0);
        inehmo.setAsenne(50);
        assertTrue(inehmo.getAsenne() == 50);
    }
    
    @Test
    public void toimiikoHumalanMuutosOikein() {
        inehmo.setHumala(-200);
        assertTrue(inehmo.getHumala() == 0);
        inehmo.setHumala(400);
        assertTrue(inehmo.getHumala() == 100);
        inehmo.setHumala(-25);
        assertTrue(inehmo.getHumala() == 75);
    }
}
