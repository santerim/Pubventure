
package pubventure.reittialgot;

import java.util.Comparator;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Tämä luokka toimii PriorityQueuen comparatorina
 */


public class EtaisyysComparator implements Comparator<Pubiobjekti> {
    
    @Override
    public int compare(Pubiobjekti eka, Pubiobjekti toka) {
        if (eka.getF() < toka.getF()) {
            return -1;
        }
        if (eka.getF() > toka.getF()) {
            return 1;
        }
        return 0;
    }
}
