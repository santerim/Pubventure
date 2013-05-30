
package pubventure.reittialgot;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Minimikeon jarejesta-metodia ei testata suoraan, vaan se tapahtuu
 * käytännössä annaJaPoistaPienin-metodin kautta.
 */
public class MinimikekoTest {
    
    private Minimikeko mk;
    private Pubiobjekti p1;
    private Pubiobjekti p2;
    private Pubiobjekti p3;
    private Pubiobjekti p4;
    private Pubiobjekti p5;
    private Pubiobjekti p6;
    private Pubiobjekti p7;
    private Pubiobjekti p8;
    
    public MinimikekoTest() {
        p1 = new Pubiobjekti();
        p2 = new Pubiobjekti();
        p3 = new Pubiobjekti();
        p4 = new Pubiobjekti();
        p5 = new Pubiobjekti();
        p6 = new Pubiobjekti();
        p7 = new Pubiobjekti();
        p8 = new Pubiobjekti();
        
        p1.setF(8);
        p2.setF(7);
        p3.setF(6);
        p4.setF(5);
        p5.setF(4);
        p6.setF(3);
        p7.setF(2);
        p8.setF(1);
    }
    
    @Before
    public void setUp() {
        mk = new Minimikeko(10);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void lisaaKekoonYksiObjekti() {
        mk.lisaaKekoon(p1);
        assertEquals(8, mk.getKeko()[0].getF());
    }
    
    @Test
    public void lisaaKekoonNeljaObjektia_OnkoJarjestysOikein() {
        mk.lisaaKekoon(p1);
        mk.lisaaKekoon(p2);
        mk.lisaaKekoon(p3);
        mk.lisaaKekoon(p4);
//        System.out.println("1. " + mk.getKeko()[0].getF() + "\n" 
//                + "2. " + mk.getKeko()[1].getF() + "\n"
//                + "3. " + mk.getKeko()[2].getF() + "\n"
//                + "4. " + mk.getKeko()[3].getF());
        assertEquals("Keon ensimmäisen objektin arvon on väärin",    5, mk.getKeko()[0].getF());
        assertEquals("Keon toisen objektin arvon on väärin",         6, mk.getKeko()[1].getF());
        assertEquals("Keon kolmannen objektin arvon on väärin",      7, mk.getKeko()[2].getF());
        assertEquals("Keon neljännen objektin arvon on väärin",      8, mk.getKeko()[3].getF());
    }

    // vielä rukattava
    @Test
    public void testAnnaPienin() {
        mk.lisaaKekoon(p1);
        mk.lisaaKekoon(p2);
        mk.lisaaKekoon(p3);
        mk.lisaaKekoon(p4);
        Pubiobjekti pienin = mk.annaPienin();
        assertEquals("Metodin palauttama pubiobjekti ei ollut pienin f-arvoltaan!", 5, pienin.getF());
    }

    @Test
    public void testAnnaJaPoistaPienin() {
        mk.lisaaKekoon(p7);
        mk.lisaaKekoon(p6);
        mk.lisaaKekoon(p3);
        mk.lisaaKekoon(p4);
        mk.lisaaKekoon(p5);
        mk.lisaaKekoon(p2);
        mk.lisaaKekoon(p1);
        mk.lisaaKekoon(p8);
        
//        int i = 0;
//        while (true) {
//            if (mk.getKeko()[i] != null) {
//                System.out.println(mk.getKeko()[i].getF());
//                i++;
//            } else {
//                break;
//            }
//        }
//        System.out.println("--------------------");
        
        Pubiobjekti pienin = mk.annaJaPoistaPienin();
        
//        i = 0;
//        while (true) {
//            if (mk.getKeko()[i] != null) {
//                System.out.println(mk.getKeko()[i].getF());
//                i++;
//            } else {
//                break;
//            }
//        }
//        System.out.println("----------------------");
        
        assertEquals("Keko antoi väärän pubiobjektin!", 1, pienin.getF());
        assertEquals("Poiston jälkeen keossa on väärä pubiobjekti päällimmäisenä!", 2, mk.getKeko()[0].getF());
        
//        mk.lisaaKekoon(p1);
//        mk.lisaaKekoon(p2);
//        mk.lisaaKekoon(p3);
//        mk.lisaaKekoon(p4);
//        
        
        
//        System.out.println("Luvut keossa");
//        for (int i = 0; i < mk.getSolmujenLKM(); i++) {
//            System.out.println(mk.getKeko()[i].getF());
//        }
        
    }
}
